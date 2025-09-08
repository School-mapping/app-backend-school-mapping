package school.sptech;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RelatorioDesempenhoIdeb {

    private static final DateTimeFormatter FORMATADOR_DATA_HORA = DateTimeFormatter.ofPattern("yyyy/MM/dd H:mm:ss");

    public static void exibirMensagemDigitada(String texto) throws InterruptedException {
        for (Character caractere : texto.toCharArray()) {
            System.out.print(caractere);
            Thread.sleep(60);
        }
        System.out.println();
    }

    public static void exibirAnimacaoProcessamento(String textoBase, Integer duracaoEmSegundos) throws InterruptedException {
        String[] simbolosAnimacao = {"|", "/", "-", "\\"};
        Long tempoFinal = System.currentTimeMillis() + duracaoEmSegundos * 1000;

        while (System.currentTimeMillis() < tempoFinal) {
            for (String simbolo : simbolosAnimacao) {
                System.out.print("\r" + textoBase + " " + simbolo);
                Thread.sleep(200);
            }
        }
        System.out.println();
    }

    public static void exibirBarraDeProgresso(int porcentagemFinal) throws InterruptedException {
        for (int i = 0; i <= porcentagemFinal; i += 10) {
            String barra = "[" + "=".repeat(i / 10) + " ".repeat((100 - i) / 10) + "] " + i + "%";
            System.out.print("\r" + barra);
            Thread.sleep(290);
        }
        System.out.println();
    }

    public static void exibirRelatorioDeEscolas(String[] escolas, Double[] notas) throws InterruptedException {
        for (int indice = 0; indice < escolas.length; indice++) {
            Thread.sleep(500);
            String classificacao = obterClassificacaoDesempenho(notas[indice]);
            String dataHora = LocalDateTime.now().format(FORMATADOR_DATA_HORA);

            System.out.println("[" + dataHora + "] LOG: Emitindo dados da escola...");
            exibirSeparador();
            System.out.println("   Escola: " + escolas[indice]);
            System.out.println("   Nota IDEB: " + notas[indice]);
            System.out.println("   Desempenho: " + classificacao);
            exibirSeparador();
            System.out.println();
        }
    }

    public static String obterClassificacaoDesempenho(Double notaIdeb) {
        if (notaIdeb < 5.0) {
            return "[▓░░] Baixo";
        } else if (notaIdeb < 6.5) {
            return "[▓▓░] Médio";
        } else {
            return "[▓▓▓] Alto";
        }
    }

    public static Double calcularMediaIdeb(Double[] notas) {
        Double soma = 0.0;
        for (Double nota : notas) {
            soma += nota;
        }
        return soma / notas.length;
    }

    public static void exibirSeparador() {
        System.out.println("────────────────────────────────────────────");
    }
}