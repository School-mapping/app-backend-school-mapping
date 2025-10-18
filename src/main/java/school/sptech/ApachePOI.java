package school.sptech;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApachePOI {

    Integer linhasPuladas = 0;
    Integer linhasLidas = 0;
    List<Endereco> listaEnderecos = new ArrayList<>();

//    Lendo Info_escolas_municipais
    public List<Escola> extrairEscolas() {

        List<Escola> listaEscolas = new ArrayList<>();

        try (
                InputStream arquivo = new FileInputStream("Info_escolas_municipais.xlsx");
                Workbook workbook = new XSSFWorkbook(arquivo)
        ) {

            System.out.println("Iniciando leitura [Info_escolas_municipais.xlsx]...");

            Sheet folha = workbook.getSheetAt(0); // Pegando a primeira folha (única) da planilha.

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

                String nome = linha.getCell(1).getStringCellValue();
//                System.out.println(linha.getCell(2).getCellType());
                String codigoInep = String.valueOf((long) linha.getCell(2).getNumericCellValue());
                String endereco = linha.getCell(8).getStringCellValue();
                String logradouro = extrairLogradouro(endereco);
                String numero = extrairNumero(endereco);
                String bairro = extrairBairro(endereco);
                String cep = extrairCep(endereco);
                Long latitude = (long) linha.getCell(17).getNumericCellValue();
                Long longitude = (long) linha.getCell(18).getNumericCellValue();

                String zona;
                Character digitoCep = cep.charAt(1);

//                Centro: CEP de 01000 a 01599
//                Zona Norte: CEP de 02000 a 02999
//                Zona Leste: CEP de 03000 a 03999 e 08000 a 08499
//                Zona Sul: CEP de 04000 a 04999
//                Zona Oeste: CEP de 05000 a 05899

                switch (digitoCep) {
                    case '1':
                        zona = "CENTRO";
                        break;
                    case '2':
                        zona = "NORTE";
                        break;
                    case '3':
                    case '8':
                        zona = "LESTE";
                        break;
                    case '4':
                        zona = "SUL";
                        break;
                    case '5':
                        zona = "OESTE";
                        break;
                    default:
                        zona = "";
                }

                Escola escola = new Escola(nome, codigoInep, null, cep, latitude, longitude);
                Endereco enderecoEscola = new Endereco(logradouro, numero, bairro, cep, zona);
                listaEscolas.add(escola);
                listaEnderecos.add(enderecoEscola);
            }
        } catch (IOException e) {

            System.out.println(e);
//          adicionar logs dos erros no BD
        }

        return listaEscolas;
    }

    private String extrairLogradouro(String endereco) {

        String[] logradouro = endereco.split(",");
        return logradouro[0].trim();
    }

    private String extrairNumero(String endereco) {

        String[] divisor = endereco.split(",");
        String depoisDaVirgula = divisor[1].trim();

        String[] numero = depoisDaVirgula.split(" ");
        return numero[0].trim();
    }

    private String extrairBairro(String endereco) {

        String bairro = "";

        String[] divisor = endereco.split(",");
        String depoisDaVirgula = divisor[1].trim();

        String[] depoisDoNumero = depoisDaVirgula.split(" ");

        for (int i = 1; i < depoisDoNumero.length; i++) {

            String palavra = depoisDoNumero[i];

            if (palavra.endsWith(".")) {
                palavra = palavra.replace(".", "");
                bairro += palavra;
                break;
            } else {
                bairro += palavra + " ";
            }
        }

        return bairro.trim();
    }

    //    Metodo com Regex para encontrar o CEP no endereço.
    private String extrairCep(String endereco) {

        if (endereco == null) return "";

        Pattern padrao = Pattern.compile("\\d{5}-\\d{3}");
        Matcher procurarCep = padrao.matcher(endereco);

            if (procurarCep.find()) {
                return procurarCep.group();
            } else {
                System.out.println("CEP está undefined!");
                return null;
        }
    }

//    Lendo ideb_territorios-3550308-2023-EM
    public List<Ideb> extrairIdeb() {

        List<Ideb> listaDadosIdeb = new ArrayList<>();

        try (
                InputStream arquivo = new FileInputStream("ideb_territorios-3550308-2023-EM.xlsx");
                Workbook workbook = new XSSFWorkbook(arquivo)
        ) {

            System.out.println("Iniciando leitura [ideb_territorios-3550308-2023-EM.xlsx]...");

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

                Ideb idebEscolar = new Ideb(codigoInepTratado, ideb);
                listaDadosIdeb.add(idebEscolar);
            }
        } catch (IOException e) {

            System.out.println(e);
//          adicionar Logs no BD
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
