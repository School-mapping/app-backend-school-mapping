package school.sptech;

public class Planilha {
    private String nome;
    private Integer anoEmissao;

    public Planilha(String nome, Integer anoEmissao) {
        this.nome = nome;
        this.anoEmissao = anoEmissao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoEmissao() {
        return anoEmissao;
    }

    public void setAnoEmissao(Integer anoEmissao) {
        this.anoEmissao = anoEmissao;
    }

    @Override
    public String toString() {
        return "Planilha{" +
                "nome='" + nome + '\'' +
                ", anoEmissao=" + anoEmissao +
                '}';
    }
}
