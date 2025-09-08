package school.sptech;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AplicacaoIdeb {

    private static final DateTimeFormatter FORMATADOR_DATA_HORA = DateTimeFormatter.ofPattern("yyyy/MM/dd H:mm:ss");

    public static void main(String[] argumentos) throws InterruptedException {
        String[] listaDeEscolas = {
                "Padre Antônio de Almeida",
                "Professora Marli Ferreira da Costa",
                "Professor Renato Souza Lima",
                "Capitão Sérgio Oliveira Mendes",
                "Professor Álvaro Nogueira Pinto",
                "Doutor Henrique Bastos de Moraes",
                "Maestro Celso Andrade Figueira",
                "Professor Ricardo Tavares Monteiro",
                "Professora Lúcia Helena Barbosa",
                "Doutor Paulo César Martins"
        };

        Double[] listaDeNotasIdeb = {
                4.2, 6.1, 7.5, 5.3, 3.8, 6.9, 5.0, 4.7, 7.8, 6.4
        };

        registrarLog("INFO", "Inicializando sistema de geração de relatórios...");
        RelatorioDesempenhoIdeb.exibirMensagemDigitada("Gerando relatório de desempenho IDEB");

        System.out.println();
        simularProcessamentoDados();

        registrarLog("SUCESSO", "Relatório pronto para visualização.");
        RelatorioDesempenhoIdeb.exibirBarraDeProgresso(100);

        registrarLog("INFO", "Exibindo resultados individuais...");
        RelatorioDesempenhoIdeb.exibirRelatorioDeEscolas(listaDeEscolas, listaDeNotasIdeb);

        Double mediaGeral = RelatorioDesempenhoIdeb.calcularMediaIdeb(listaDeNotasIdeb);
        registrarLog("INFO", "Média geral do IDEB: " + String.format("%.2f", mediaGeral));
    }

    public static void registrarLog(String tipo, String mensagem) {
        String dataHoraAtual = LocalDateTime.now().format(FORMATADOR_DATA_HORA);
        String prefixo;

        switch (tipo.toUpperCase()) {
            case "INFO":
                prefixo = "INFO";
                break;
            case "PROCESSO":
                prefixo = "PROCESSO";
                break;
            case "SUCESSO":
                prefixo = "SUCESSO";
                break;
            case "ERRO":
                prefixo = "ERRO";
                break;
            case "ALERTA":
                prefixo = "ALERTA";
                break;
            default:
                prefixo = "LOG";
        }

        System.out.println("[" + dataHoraAtual + "] " + prefixo + ": " + mensagem);
    }

    public static void simularProcessamentoDados() throws InterruptedException {
        registrarLog("PROCESSO", "Conectando ao banco de dados...");
        Thread.sleep(1000);

        registrarLog("PROCESSO", "Validando estrutura dos dados...");
        Thread.sleep(1000);

        registrarLog("PROCESSO", "Verificando inconsistências...");
        Thread.sleep(1000);

        registrarLog("SUCESSO", "Dados validados com sucesso.");
        RelatorioDesempenhoIdeb.exibirAnimacaoProcessamento("Finalizando processamento", 2);
    }
}