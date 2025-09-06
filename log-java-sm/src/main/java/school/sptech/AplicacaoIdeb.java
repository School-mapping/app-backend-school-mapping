package school.sptech;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AplicacaoIdeb {

    // Criamos um formatador para deixar a data e hora bonitinhas no log
    private static final DateTimeFormatter FORMATADOR_DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static void main(String[] argumentos) throws InterruptedException {
        // Lista de escolas que vamos usar no relatório
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

        // Notas IDEB correspondentes às escolas acima (mesma ordem)
        Double[] listaDeNotasIdeb = {
                4.2, 6.1, 7.5, 5.3, 3.8, 6.9, 5.0, 4.7, 7.8, 6.4
        };

        // Aqui começa o fluxo do programa
        // Primeiro registramos no log (com data/hora) e depois mostramos a mensagem animada
        registrarLog("Inicializando sistema de geração de relatórios...");
        RelatorioDesempenhoIdeb.exibirMensagemDigitada("Gerando relatório de desempenho IDEB");

        System.out.println();
        registrarLog("Carregando e processando dados das escolas...");
        RelatorioDesempenhoIdeb.exibirAnimacaoProcessamento("Processando dados", 3);

        // Mostramos que o relatório está pronto e simulamos uma barra de progresso
        registrarLog("Relatório pronto para visualização.");
        RelatorioDesempenhoIdeb.exibirBarraDeProgresso(100);

        // Agora exibimos cada escola com sua nota e classificação (baixo, médio, alto)
        registrarLog("Exibindo resultados individuais...");
        RelatorioDesempenhoIdeb.exibirRelatorioDeEscolas(listaDeEscolas, listaDeNotasIdeb);

        // Calculamos a média geral do IDEB e exibimos no final
        Double mediaGeral = RelatorioDesempenhoIdeb.calcularMediaIdeb(listaDeNotasIdeb);
        registrarLog("Cálculo concluído da média geral do IDEB.");
        RelatorioDesempenhoIdeb.exibirMensagemDigitada("📈 Média geral do IDEB: " + String.format("%.2f", mediaGeral));
    }

    // Esse método é nosso "simulador de log" → imprime a mensagem com data/hora
    public static void registrarLog(String mensagem) {
        String dataHoraAtual = LocalDateTime.now().format(FORMATADOR_DATA_HORA);
        System.out.println("[" + dataHoraAtual + "] " + mensagem);
    }
}
