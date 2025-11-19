package school.sptech;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApachePOI {

    Integer linhasPuladas = 0;
    Integer linhasLidas = 0;

    private static final Logger logger = LoggerFactory.getLogger(ApachePOI.class);
    BancoRepositorio bancoRepositorio = new BancoRepositorio();

    List<Endereco> listaEnderecos = new ArrayList<>();

    private final S3Client s3;
    private final String bucket = "s3-raw-pi";

    public ApachePOI(S3Client s3) {
        this.s3 = s3;
    }

    //    Lendo Info_escolas_municipais
    public List<Escola> extrairEscolas() {

        String diretorioPastasTemporarias = "C:/Users/kauan/Desktop/SchoolMapping/PlanilhasDados";

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        List<Escola> listaEscolas = new ArrayList<>();

        String key = "Info_escolas_municipais.xlsx";

        try (
                InputStream arquivo = s3.getObject(GetObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .build());
                Workbook workbook = new XSSFWorkbook(arquivo)
        ) {

            logger.info(" {} Iniciando leitura [{}]...", timestamp, key);

            LocalDateTime agora = LocalDateTime.now();
            bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data_origem, nivel, descricao, origem) VALUES (?, ?, ?, ?)", agora, "INFO", "Iniciando leitura das Escolas", "ApachePOI");

            Sheet folha = workbook.getSheetAt(0); // Pegando a primeira folha (única) da planilha.

            Integer INDEX_INFO_NOME = 1;
            Integer INDEX_INFO_CODIGO_INEP = 2;
            Integer INDEX_INFO_ENDERECO = 8;

            for (int i = 1; i <= folha.getLastRowNum(); i++) {

                Row linha = folha.getRow(i); // Lendo a primeiro linha da folha

                if (
                        linha.getCell(1) == null ||
                                linha.getCell(2) == null ||
                                linha.getCell(8) == null ||
                                linha.getCell(17) == null ||
                                linha.getCell(18) == null) {
                    linhasPuladas++;
                    continue; // Pula para o próximo indice (i++)
                }

                linhasLidas++;


                String nome = linha.getCell(INDEX_INFO_NOME).getStringCellValue();
//                System.out.println(linha.getCell(2).getCellType());
                String codigoInep = String.valueOf((long) linha.getCell(INDEX_INFO_CODIGO_INEP).getNumericCellValue());
                String endereco = linha.getCell(INDEX_INFO_ENDERECO).getStringCellValue();
                String logradouro = extrairLogradouro(endereco);
                String numero = extrairNumero(endereco);
                String bairro = extrairBairro(endereco);
                String cep = extrairCep(endereco);

                Regiao regiao;
                Character digitoCep = cep.charAt(1);

//                Centro: CEP de 01000 a 01599
//                Zona Norte: CEP de 02000 a 02999
//                Zona Leste: CEP de 03000 a 03999 e 08000 a 08499
//                Zona Sul: CEP de 04000 a 04999
//                Zona Oeste: CEP de 05000 a 05899

                switch (digitoCep) {
                    case '1':
                        regiao = Regiao.CENTRO;
                        break;
                    case '2':
                        regiao = Regiao.NORTE;
                        break;
                    case '3':
                    case '8':
                        regiao = Regiao.LESTE;
                        break;
                    case '4':
                        regiao = Regiao.SUL;
                        break;
                    case '5':
                        regiao = Regiao.OESTE;
                        break;
                    default:
                        regiao = null;
                }

                Endereco enderecoEscola = new Endereco(logradouro, numero, bairro, cep, regiao);
                Escola escola = new Escola(nome, codigoInep, cep, enderecoEscola);
                listaEscolas.add(escola);
                listaEnderecos.add(enderecoEscola);
            }
        } catch (IOException e) {

            logger.error("Erro ao tentar iniciar leitura das Escolas.", e);
            LocalDateTime agora = LocalDateTime.now();
            bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data_origem, nivel, descricao, origem) VALUES (?, ?, ?, ?)", timestamp, "ERROR", "Erro ao tentar iniciar leitura das Escolas.", "ApachePOI");
        }

        return listaEscolas;
    }

    private String extrairLogradouro(String endereco) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        try {
            String[] logradouro = endereco.split(",");
            return logradouro[0].trim();
        } catch (Exception e) {
            logger.error("Erro ao extrair o logradouro do endereço.", e);
            bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data_origem, nivel, descricao, origem) VALUES (?, ?, ?, ?)", timestamp, "ERROR", "Erro ao extrair o logradouro do endereço.", "ApachePOI");
            return "";
        }
    }

    private String extrairNumero(String endereco) {

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        try {
            String[] divisor = endereco.split(",");
            String depoisDaVirgula = divisor[1].trim();

            String[] numero = depoisDaVirgula.split(" ");
            return numero[0].trim();

        } catch (Exception e) {
            logger.error("Erro ao extrair o número do endereço.", e);
            bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data_origem, nivel, descricao, origem) VALUES (?, ?, ?, ?)", timestamp, "ERROR", "Erro ao tentar iniciar leitura das Escolas.", "ApachePOI");
            return "";
        }
    }

    private String extrairBairro(String endereco) {

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        try {
            String bairro = "";

            String[] divisor = endereco.split(",");
            String depoisDaVirgula = divisor[1].trim();

            String[] depoisDoNumero = depoisDaVirgula.split(" ");

            for (int i = 1; i < depoisDoNumero.length; i++) {

                try {
                    String palavra = depoisDoNumero[i];

                    if (palavra.endsWith(".")) {
                        palavra = palavra.replace(".", "");
                        bairro += palavra;
                        break;
                    } else {
                        bairro += palavra + " ";
                    }
                } catch (Exception e) {
                    logger.error(" {} Erro ao extrair o bairro do endereço.", timestamp, e);
                    bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data_origem, nivel, descricao, origem) VALUES (?, ?, ?, ?)", timestamp, "ERROR", "Erro ao extrair o bairro do endereço.", "ApachePOI");
                    return "";
                }
            }

            return bairro.trim();
        } catch (Exception e) {
            logger.error("{} Erro ao extrair o bairro do endereço.", timestamp, e);
            bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data_origem, nivel, descricao, origem) VALUES (?, ?, ?, ?)", timestamp, "ERROR", "Erro ao extrair o bairro do endereço.", "ApachePOI");
            return "";
        }
    }

    //    Metodo com Regex para encontrar o CEP no endereço.
    private String extrairCep(String endereco) {

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        try {
            if (endereco == null) return "";

            Pattern padrao = Pattern.compile("\\d{5}-\\d{3}");
            Matcher procurarCep = padrao.matcher(endereco);

            if (procurarCep.find()) {
                return procurarCep.group();
            } else {
                System.out.println("CEP está undefined!");
                return null;
            }

        } catch (Exception e) {
            logger.error("{} Erro ao extrair o CEP do endereço.", timestamp, e);
            bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data_origem, nivel, descricao, origem) VALUES (?, ?, ?, ?)", timestamp, "ERROR", "Erro ao extrair o CEP do endereço.", "ApachePOI");
            return "";
        }
    }

    //    Lendo ideb_territorios-3550308-2023-EM
    public List<Ideb> extrairIdeb() {

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        List<Ideb> listaDadosIdeb = new ArrayList<>();

        try (
                InputStream arquivo = s3.getObject(GetObjectRequest.builder()
                        .bucket(bucket)
                        .key("ideb_territorios-3550308-2023-EM.xlsx")
                        .build());
                Workbook workbook = new XSSFWorkbook(arquivo)
        ) {

            System.out.println("Iniciando leitura [ideb_territorios-3550308-2023-EM.xlsx]...");

            bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data, nivel, descricao, origem) VALUES (?, ?, ?, ?)", timestamp, "INFO", "Erro ao tentar iniciar leitura do Ideb.", "ApachePOI");

            Sheet folha = workbook.getSheet("escolas");

            for (int i = 1; i <= folha.getLastRowNum(); i++) {

                Row linha = folha.getRow(i);

                if (linha.getCell(0) == null || linha.getCell(4) == null) {
                    linhasPuladas++;
                    continue;
                }

                linhasLidas++;

                Long codigoInep = (long) linha.getCell(0).getNumericCellValue();
                String codigoInepTratado = String.valueOf(codigoInep);
                Double ideb = linha.getCell(4).getNumericCellValue();

                Ideb idebEscolar = new Ideb(codigoInepTratado, ideb, LocalDate.now().getYear(), null);
                listaDadosIdeb.add(idebEscolar);
            }
        } catch (IOException e) {

            logger.error("{} Erro ao tentar iniciar leitura do Ideb.", timestamp, e);
            LocalDateTime agora = LocalDateTime.now();
            bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data, nivel, descricao, origem) VALUES (?, ?, ?, ?)", agora, "ERROR", "Erro ao tentar iniciar leitura do Ideb.", "ApachePOI");
        }

        return listaDadosIdeb;
    }

    public Integer getLinhasPuladas() {
        return linhasPuladas;
    }

    public Integer getLinhasLidas() {
        return linhasLidas;
    }

    public List<Endereco> getListaEnderecos() {
        return listaEnderecos;
    }
}
