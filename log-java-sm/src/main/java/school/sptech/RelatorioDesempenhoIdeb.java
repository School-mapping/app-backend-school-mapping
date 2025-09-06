package school.sptech;

public class RelatorioDesempenhoIdeb {

    // Exibe um texto como se estivesse sendo digitado "ao vivo", letra por letra
    public static void exibirMensagemDigitada(String texto) throws InterruptedException {
        for (Character caractere : texto.toCharArray()) {
            System.out.print(caractere);
            Thread.sleep(60); // pausa de 60ms entre cada caractere
        }
        System.out.println();
    }

    // Simula uma animação de processamento (girando | / - \ por alguns segundos)
    public static void exibirAnimacaoProcessamento(String textoBase, Integer duracaoEmSegundos) throws InterruptedException {
        String[] simbolosAnimacao = {"|", "/", "-", "\\"};
        Long tempoFinal = System.currentTimeMillis() + duracaoEmSegundos * 1000;

        while (System.currentTimeMillis() < tempoFinal) {
            for (String simbolo : simbolosAnimacao) {
                System.out.print("\r" + textoBase + " " + simbolo); // \r volta pro começo da linha
                Thread.sleep(200);
            }
        }
        System.out.println();
    }

    // Exibe uma barra de progresso de 0% a 100%, crescendo aos poucos
    public static void exibirBarraDeProgresso(int porcentagemFinal) throws InterruptedException {
        for (int i = 0; i <= porcentagemFinal; i += 10) {
            String barra = "[" + "=".repeat(i / 10) + " ".repeat((100 - i) / 10) + "] " + i + "%";
            System.out.print("\r" + barra);
            Thread.sleep(290); // tempo entre cada atualização
        }
        System.out.println();
    }

    // Mostra o relatório completo de todas as escolas
    public static void exibirRelatorioDeEscolas(String[] escolas, Double[] notas) throws InterruptedException {
        for (int indice = 0; indice < escolas.length; indice++) {
            Thread.sleep(500); // pausa para dar sensação de "carregando"
            String classificacao = obterClassificacaoDesempenho(notas[indice]);

            exibirSeparador();
            System.out.println("   Escola: " + escolas[indice]);
            System.out.println("   Nota IDEB: " + notas[indice]);
            System.out.println("   Desempenho: " + classificacao);
            exibirSeparador();
            System.out.println();
        }
    }

    // Define a classificação baseada na nota IDEB
    public static String obterClassificacaoDesempenho(Double notaIdeb) {
        if (notaIdeb < 5.0) {
            return "[▓░░] Baixo";  // notas abaixo de 5 são classificadas como Baixo
        } else if (notaIdeb < 6.5) {
            return "[▓▓░] Médio"; // notas entre 5 e 6.5 ficam como Médio
        } else {
            return "[▓▓▓] Alto";  // acima de 6.5 é considerado Alto
        }
    }

    // Faz a média das notas IDEB
    public static Double calcularMediaIdeb(Double[] notas) {
        Double soma = 0.0;
        for (Double nota : notas) {
            soma += nota;
        }
        return soma / notas.length;
    }

    // Só imprime uma linha separadora bonita pra organizar o relatório
    public static void exibirSeparador() {
        System.out.println("────────────────────────────────────────────");
    }
}
