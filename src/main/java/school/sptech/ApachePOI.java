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
    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    private static final Logger logger = LoggerFactory.getLogger(ApachePOI.class);
    BancoRepositorio bancoRepositorio = new BancoRepositorio();

    List<Endereco> listaEnderecos = new ArrayList<>();

    private final S3Client s3;
    private final String bucket = "s3-raw-pi";

    public ApachePOI(S3Client s3) {
        this.s3 = s3;
    }

    String diretorioPastasTemporarias = "C:\\Users\\victo\\OneDrive\\Área de Trabalho\\SchoolMapping\\PlanilhaDados";

    //    Lendo Info_escolas_municipais
    public List<Escola> extrairEscolas() {


        List<Escola> listaEscolas = new ArrayList<>();

//        String key = "Planilhas de Dados/Info_escolas_municipais.xlsx";
        String key = "Info_escolas_municipais.xlsx";

        try (
//                InputStream arquivo = s3.getObject(GetObjectRequest.builder()
//                        .bucket(bucket)
//                        .key(key)
//                        .build());

//                Diretório testes - Kauan Luna
                InputStream arquivo = new FileInputStream(diretorioPastasTemporarias + "/" + key);
                Workbook workbook = new XSSFWorkbook(arquivo)
        ) {

            logger.info(" {} Iniciando leitura [{}]...", timestamp, key);

            LocalDateTime agora = LocalDateTime.now();
            bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data_hora, nivel, descricao, origem) VALUES (?, ?, ?, ?)", agora, "INFO", "Iniciando leitura das Escolas", "ApachePOI");

            Sheet folha = workbook.getSheetAt(0); // Pegando a primeira folha (única) da planilha.

            Integer INDEX_INFO_NOME = 1;
            Integer INDEX_INFO_CODIGO_INEP = 2;
            Integer INDEX_INFO_ENDERECO = 8;

            for (int i = 1; i <= folha.getLastRowNum() / 2; i++) {

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

                Endereco enderecoEscola = new Endereco(logradouro, numero, bairro, cep, regiao, codigoInep);
                Escola escola = new Escola(nome, codigoInep, enderecoEscola);
                listaEscolas.add(escola);
                listaEnderecos.add(enderecoEscola);
            }
        } catch (IOException e) {

            logger.error("Erro ao tentar iniciar leitura das Escolas.", e);
            LocalDateTime agora = LocalDateTime.now();
            bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data_hora, nivel, descricao, origem) VALUES (?, ?, ?, ?)", timestamp, "ERROR", "Erro ao tentar iniciar leitura das Escolas.", "ApachePOI");
        }

        return listaEscolas;
    }

    private String extrairLogradouro(String endereco) {

        try {
            String[] logradouro = endereco.split(",");
            return logradouro[0].trim();
        } catch (Exception e) {
            logger.error("Erro ao extrair o logradouro do endereço.", e);
            bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data_hora, nivel, descricao, origem) VALUES (?, ?, ?, ?)", timestamp, "ERROR", "Erro ao extrair o logradouro do endereço.", "ApachePOI");
            return "";
        }
    }

    private String extrairNumero(String endereco) {

        try {
            String[] divisor = endereco.split(",");
            String depoisDaVirgula = divisor[1].trim();

            String[] numero = depoisDaVirgula.split(" ");
            return numero[0].trim();

        } catch (Exception e) {
            logger.error("Erro ao extrair o número do endereço.", e);
            bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data_hora, nivel, descricao, origem) VALUES (?, ?, ?, ?)", timestamp, "ERROR", "Erro ao tentar iniciar leitura das Escolas.", "ApachePOI");
            return "";
        }
    }

    private String extrairBairro(String endereco) {

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
                    bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data_hora, nivel, descricao, origem) VALUES (?, ?, ?, ?)", timestamp, "ERROR", "Erro ao extrair o bairro do endereço.", "ApachePOI");
                    return "";
                }
            }

            return bairro.trim();
        } catch (Exception e) {
            logger.error("{} Erro ao extrair o bairro do endereço.", timestamp, e);
            bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data_hora, nivel, descricao, origem) VALUES (?, ?, ?, ?)", timestamp, "ERROR", "Erro ao extrair o bairro do endereço.", "ApachePOI");
            return "";
        }
    }

    //    Metodo com Regex para encontrar o CEP no endereço.
    private String extrairCep(String endereco) {

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
            bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data_hora, nivel, descricao, origem) VALUES (?, ?, ?, ?)", timestamp, "ERROR", "Erro ao extrair o CEP do endereço.", "ApachePOI");
            return "";
        }
    }

    //    Lendo ideb_territorios-3550308-2023-EM
    public List<Ideb> extrairIdeb() {

        List<Ideb> listaDadosIdeb = new ArrayList<>();
        List<PlanilhaIdeb> listaPlanilhasIdeb = new ArrayList<>();

        listaPlanilhasIdeb.add(new PlanilhaIdeb("ideb_territorios-3550308-2023-EM.xlsx", 2023, 0, 4, 5,
                6, 7, 8));

        listaPlanilhasIdeb.add(new PlanilhaIdeb("ideb_territorios-3550308-2021-EM.xlsx", 2021, 0, 4, 5,
                6, 7, 8));

        for (PlanilhaIdeb planilhaIdeb : listaPlanilhasIdeb) {

            String key = "Planilhas de Dados/" + planilhaIdeb.getNome();
            try (
//                    InputStream arquivo = s3.getObject(GetObjectRequest.builder()
//                            .bucket(bucket)
//                            .key(key)
//                            .build());
                    InputStream arquivo = new FileInputStream(diretorioPastasTemporarias + "/" + planilhaIdeb.getNome());
                    Workbook workbook = new XSSFWorkbook(arquivo)
            ) {

                logger.info("[{}] === Iniciando leitura [{}] ===", timestamp, planilhaIdeb.getNome());

                bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data_hora, nivel, descricao, origem) VALUES (?, ?, ?, ?)",
                        timestamp, "INFO", "Iniciando leitura do arquivo:" + planilhaIdeb.getNome(), "ApachePOI");

                Sheet folha = workbook.getSheet("escolas");

                for (int i = 1; i <= folha.getLastRowNum() / 2; i++) {

                    Row linha = folha.getRow(i);


                    Long codigoInep = linha.getCell(planilhaIdeb.getINDEX_INEP_ID()) == null ? null : (long) linha.getCell(planilhaIdeb.getINDEX_INEP_ID()).getNumericCellValue();
                    String codigoInepTratado = codigoInep == null ? "" : String.valueOf(codigoInep);
                    Double ideb = linha.getCell(planilhaIdeb.getINDEX_NOTA_IDEB()) == null ? null : linha.getCell(planilhaIdeb.getINDEX_NOTA_IDEB()).getNumericCellValue();
                    Double fluxo = linha.getCell(planilhaIdeb.getINDEX_NOTA_FLUXO()) == null ? null : linha.getCell(planilhaIdeb.getINDEX_NOTA_FLUXO()).getNumericCellValue();
                    Double aprendizado = linha.getCell(planilhaIdeb.getINDEX_NOTA_APRENDIZADO()) == null ? null : linha.getCell(planilhaIdeb.getINDEX_NOTA_APRENDIZADO()).getNumericCellValue();
                    Double mat = linha.getCell(planilhaIdeb.getINDEX_NOTA_MAT()) == null ? null : linha.getCell(planilhaIdeb.getINDEX_NOTA_MAT()).getNumericCellValue();
                    Double lp = linha.getCell(planilhaIdeb.getINDEX_NOTA_LP()) == null ? null : linha.getCell(planilhaIdeb.getINDEX_NOTA_LP()).getNumericCellValue();


                    if (codigoInepTratado.isEmpty()) {
                        linhasPuladas++;
                        continue;
                    }

                    Ideb idebEscolar = new Ideb(codigoInepTratado, ideb, fluxo, planilhaIdeb.getAnoEmissao(), null);
                    listaDadosIdeb.add(idebEscolar);

                    linhasLidas++;
                }
            } catch (IOException e) {

                logger.error("{} Erro ao tentar iniciar leitura do Ideb.", timestamp, e);
                bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data_hora, nivel, descricao, origem) VALUES (?, ?, ?, ?)", timestamp, "ERROR", "Erro ao tentar iniciar leitura do Ideb.", "ApachePOI");
            }
        }

        return listaDadosIdeb;
    }

    public List<Verba> extrairVerbas() {

        List<PlanilhaVerba> listPlanilhas = new ArrayList<>();

        PlanilhaVerba planilhaVerba2023 = new PlanilhaVerba("ptrf-2023.xlsx", 2023, 2, null, 3, 4, 5, 6,
                8, 11, null, List.of(7, 9), List.of(10));
        listPlanilhas.add(planilhaVerba2023);

        PlanilhaVerba planilhaVerba2021 = new PlanilhaVerba("ptrf2021.xlsx", 2021, 2, null, 3, null, null, 4,
                5, 8, null, List.of(6, 9), null);
        listPlanilhas.add(planilhaVerba2021);


        List<Verba> listaVerba = new ArrayList<>();

        for (PlanilhaVerba planilha : listPlanilhas) {

            String key = "Planilhas de Dados/" + planilha.getNome();

            try (InputStream arquivo = new FileInputStream(diretorioPastasTemporarias + "/" + planilha.getNome());
//            try (InputStream arquivo = s3.getObject(GetObjectRequest.builder()
//                    .bucket(bucket)
//                    .key(key)
//                    .build());
                 Workbook workbook = new XSSFWorkbook(arquivo)) {

                Sheet sheet = workbook.getSheetAt(0);


                for (int i = 4; i <= sheet.getLastRowNum() / 2; i++) {
                    Row linhaDados = sheet.getRow(i);

                    String nome = linhaDados.getCell(planilha.getINDEX_INFO_NOME_ESCOLA()) == null ? "" : linhaDados.getCell(planilha.getINDEX_INFO_NOME_ESCOLA()).getStringCellValue().replaceAll("\\s*-", "").trim();
                    String dre = linhaDados.getCell(planilha.getINDEX_INFO_DRE()) == null ? "" : linhaDados.getCell(planilha.getINDEX_INFO_DRE()).getStringCellValue().trim();
                    String distrito = "";
                    String subprefeitura = "";
                    Double primeiroRepasse = 0.0;
                    Double segundoRepasse = 0.0;
                    Double terceiroRepasse = 0.0;
                    Double valorVulnerabilidade = 0.0;
                    Double valorExtraordinario = 0.0;
                    Double valorGremio = 0.0;

                    if (planilha.getINDEX_INFO_DISTRITO() != null) {
                        distrito = linhaDados.getCell(planilha.getINDEX_INFO_DISTRITO()) == null ? "" : linhaDados.getCell(planilha.getINDEX_INFO_DISTRITO()).getStringCellValue().trim();
                    }

                    if (planilha.getINDEX_INFO_SUBPREFEITURA() != null) {
                        subprefeitura = linhaDados.getCell(planilha.getINDEX_INFO_SUBPREFEITURA()) == null ? "" : linhaDados.getCell(planilha.getINDEX_INFO_SUBPREFEITURA()).getStringCellValue().trim();
                    }

                    if (planilha.getINDEX_INFO_PRIMEIRO_REPASSE() != null) {
                        primeiroRepasse = linhaDados.getCell(planilha.getINDEX_INFO_PRIMEIRO_REPASSE()) == null ? 0.0 : linhaDados.getCell(planilha.getINDEX_INFO_PRIMEIRO_REPASSE()).getNumericCellValue();
                    }

                    if (planilha.getINDEX_INFO_SEGUNDO_REPASSE() != null) {
                        segundoRepasse = linhaDados.getCell(planilha.getINDEX_INFO_SEGUNDO_REPASSE()) == null ? 0.0 : linhaDados.getCell(planilha.getINDEX_INFO_SEGUNDO_REPASSE()).getNumericCellValue();
                    }

                    if (planilha.getINDEX_INFO_TERCEIRO_REPASSE() != null) {
                        terceiroRepasse = linhaDados.getCell(planilha.getINDEX_INFO_TERCEIRO_REPASSE()) == null ? 0.0 : linhaDados.getCell(planilha.getINDEX_INFO_TERCEIRO_REPASSE()).getNumericCellValue();
                    }

                    if (planilha.getINDEX_INFO_VALOR_VULNERABILIDADE() != null) {
                        for (Integer index : planilha.getINDEX_INFO_VALOR_VULNERABILIDADE()) {
                            valorVulnerabilidade += linhaDados.getCell(index).getNumericCellValue();
                        }
                    }

                    if (planilha.getINDEX_INFO_VALOR_EXTRAORDINARIO() != null) {
                        for (Integer index : planilha.getINDEX_INFO_VALOR_EXTRAORDINARIO()) {
                            valorExtraordinario += linhaDados.getCell(index).getNumericCellValue();
                        }
                    }

                    if (planilha.getINDEX_INFO_VALOR_GREMIO() != null) {
                        for (Integer index : planilha.getINDEX_INFO_VALOR_GREMIO()) {
                            valorGremio += linhaDados.getCell(index).getNumericCellValue();
                        }
                    }

                    listaVerba.add(new Verba(planilha.getAnoEmissao(), nome, primeiroRepasse, segundoRepasse, terceiroRepasse, valorVulnerabilidade, valorExtraordinario, valorGremio));

                }
            } catch (Exception e) {
                logger.error("Erro ao realizar leitura da Verba.", e);
                bancoRepositorio.getJdbcTemplate().update("INSERT INTO TB_Logs (data_hora, nivel, descricao, origem) VALUES (?, ?, ?, ?)", timestamp, "ERROR", "Erro realizar leitura da Verba.", "ApachePOI");
            }
        }
        return listaVerba;
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
