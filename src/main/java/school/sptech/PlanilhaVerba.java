package school.sptech;


import java.util.ArrayList;
import java.util.List;

public class PlanilhaVerba {
    private String nome;
    private Integer INDEX_INFO_NOME_ESCOLA;
    private Integer INDEX_INFO_CODIGO_INEP;
    private Integer INDEX_INFO_DRE;
    private Integer INDEX_INFO_DISTRITO;
    private Integer INDEX_INFO_SUBPREFEITURA;
    private Integer INDEX_INFO_PRIMEIRO_REPASSE;
    private Integer INDEX_INFO_SEGUNDO_REPASSE;
    private Integer INDEX_INFO_TERCEIRO_REPASSE;
    private List<Integer> INDEX_INFO_VALOR_VULNERABILIDADE = new ArrayList<>();
    private List<Integer> INDEX_INFO_VALOR_EXTRAORDINARIO = new ArrayList<>();
    private List<Integer> INDEX_INFO_VALOR_GREMIO = new ArrayList<>();
    private Integer anoEmissao;

    public PlanilhaVerba(String nome,Integer INDEX_INFO_NOME_ESCOLA, Integer INDEX_INFO_CODIGO_INEP, Integer INDEX_INFO_DRE, Integer INDEX_INFO_DISTRITO,
                         Integer INDEX_INFO_SUBPREFEITURA, Integer INDEX_INFO_PRIMEIRO_REPASSE, Integer INDEX_INFO_SEGUNDO_REPASSE, Integer INDEX_INFO_TERCEIRO_REPASSE,
                         List<Integer> INDEX_INFO_VALOR_VULNERABILIDADE, List<Integer> INDEX_INFO_VALOR_EXTRAORDINARIO, List<Integer> INDEX_INFO_VALOR_GREMIO, Integer anoEmissao) {
        this.nome = nome;
        this.INDEX_INFO_NOME_ESCOLA = INDEX_INFO_NOME_ESCOLA;
        this.INDEX_INFO_CODIGO_INEP = INDEX_INFO_CODIGO_INEP;
        this.INDEX_INFO_DRE = INDEX_INFO_DRE;
        this.INDEX_INFO_DISTRITO = INDEX_INFO_DISTRITO;
        this.INDEX_INFO_SUBPREFEITURA = INDEX_INFO_SUBPREFEITURA;
        this.INDEX_INFO_PRIMEIRO_REPASSE = INDEX_INFO_PRIMEIRO_REPASSE;
        this.INDEX_INFO_SEGUNDO_REPASSE = INDEX_INFO_SEGUNDO_REPASSE;
        this.INDEX_INFO_TERCEIRO_REPASSE = INDEX_INFO_TERCEIRO_REPASSE;
        this.INDEX_INFO_VALOR_VULNERABILIDADE = INDEX_INFO_VALOR_VULNERABILIDADE;
        this.INDEX_INFO_VALOR_EXTRAORDINARIO = INDEX_INFO_VALOR_EXTRAORDINARIO;
        this.INDEX_INFO_VALOR_GREMIO = INDEX_INFO_VALOR_GREMIO;
        this.anoEmissao = anoEmissao;
    }

    public Integer getINDEX_INFO_NOME_ESCOLA() {
        return INDEX_INFO_NOME_ESCOLA;
    }

    public void setINDEX_INFO_NOME_ESCOLA(Integer INDEX_INFO_NOME_ESCOLA) {
        this.INDEX_INFO_NOME_ESCOLA = INDEX_INFO_NOME_ESCOLA;
    }

    public Integer getINDEX_INFO_CODIGO_INEP() {
        return INDEX_INFO_CODIGO_INEP;
    }

    public void setINDEX_INFO_CODIGO_INEP(Integer INDEX_INFO_CODIGO_INEP) {
        this.INDEX_INFO_CODIGO_INEP = INDEX_INFO_CODIGO_INEP;
    }

    public Integer getINDEX_INFO_DRE() {
        return INDEX_INFO_DRE;
    }

    public void setINDEX_INFO_DRE(Integer INDEX_INFO_DRE) {
        this.INDEX_INFO_DRE = INDEX_INFO_DRE;
    }

    public Integer getINDEX_INFO_DISTRITO() {
        return INDEX_INFO_DISTRITO;
    }

    public void setINDEX_INFO_DISTRITO(Integer INDEX_INFO_DISTRITO) {
        this.INDEX_INFO_DISTRITO = INDEX_INFO_DISTRITO;
    }

    public Integer getINDEX_INFO_SUBPREFEITURA() {
        return INDEX_INFO_SUBPREFEITURA;
    }

    public void setINDEX_INFO_SUBPREFEITURA(Integer INDEX_INFO_SUBPREFEITURA) {
        this.INDEX_INFO_SUBPREFEITURA = INDEX_INFO_SUBPREFEITURA;
    }

    public Integer getINDEX_INFO_PRIMEIRO_REPASSE() {
        return INDEX_INFO_PRIMEIRO_REPASSE;
    }

    public void setINDEX_INFO_PRIMEIRO_REPASSE(Integer INDEX_INFO_PRIMEIRO_REPASSE) {
        this.INDEX_INFO_PRIMEIRO_REPASSE = INDEX_INFO_PRIMEIRO_REPASSE;
    }

    public Integer getINDEX_INFO_SEGUNDO_REPASSE() {
        return INDEX_INFO_SEGUNDO_REPASSE;
    }

    public void setINDEX_INFO_SEGUNDO_REPASSE(Integer INDEX_INFO_SEGUNDO_REPASSE) {
        this.INDEX_INFO_SEGUNDO_REPASSE = INDEX_INFO_SEGUNDO_REPASSE;
    }

    public Integer getINDEX_INFO_TERCEIRO_REPASSE() {
        return INDEX_INFO_TERCEIRO_REPASSE;
    }

    public void setINDEX_INFO_TERCEIRO_REPASSE(Integer INDEX_INFO_TERCEIRO_REPASSE) {
        this.INDEX_INFO_TERCEIRO_REPASSE = INDEX_INFO_TERCEIRO_REPASSE;
    }

    public List<Integer> getINDEX_INFO_VALOR_VULNERABILIDADE() {
        return INDEX_INFO_VALOR_VULNERABILIDADE;
    }

    public void setINDEX_INFO_VALOR_VULNERABILIDADE(List<Integer> INDEX_INFO_VALOR_VULNERABILIDADE) {
        this.INDEX_INFO_VALOR_VULNERABILIDADE = INDEX_INFO_VALOR_VULNERABILIDADE;
    }

    public List<Integer> getINDEX_INFO_VALOR_EXTRAORDINARIO() {
        return INDEX_INFO_VALOR_EXTRAORDINARIO;
    }

    public void setINDEX_INFO_VALOR_EXTRAORDINARIO(List<Integer> INDEX_INFO_VALOR_EXTRAORDINARIO) {
        this.INDEX_INFO_VALOR_EXTRAORDINARIO = INDEX_INFO_VALOR_EXTRAORDINARIO;
    }

    public List<Integer> getINDEX_INFO_VALOR_GREMIO() {
        return INDEX_INFO_VALOR_GREMIO;
    }

    public void setINDEX_INFO_VALOR_GREMIO(List<Integer> INDEX_INFO_VALOR_GREMIO) {
        this.INDEX_INFO_VALOR_GREMIO = INDEX_INFO_VALOR_GREMIO;
    }

    public Integer getAnoEmissao() {
        return anoEmissao;
    }

    public void setAnoEmissao(Integer anoEmissao) {
        this.anoEmissao = anoEmissao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "PlanilhaVerba{" +
                "INDEX_INFO_NOME_ESCOLA=" + INDEX_INFO_NOME_ESCOLA +
                ", INDEX_INFO_CODIGO_INEP=" + INDEX_INFO_CODIGO_INEP +
                ", INDEX_INFO_DRE=" + INDEX_INFO_DRE +
                ", INDEX_INFO_DISTRITO=" + INDEX_INFO_DISTRITO +
                ", INDEX_INFO_SUBPREFEITURA=" + INDEX_INFO_SUBPREFEITURA +
                ", INDEX_INFO_PRIMEIRO_REPASSE=" + INDEX_INFO_PRIMEIRO_REPASSE +
                ", INDEX_INFO_SEGUNDO_REPASSE=" + INDEX_INFO_SEGUNDO_REPASSE +
                ", INDEX_INFO_TERCEIRO_REPASSE=" + INDEX_INFO_TERCEIRO_REPASSE +
                ", INDEX_INFO_VALOR_VULNERABILIDADE=" + INDEX_INFO_VALOR_VULNERABILIDADE +
                ", INDEX_INFO_VALOR_EXTRAORDINARIO=" + INDEX_INFO_VALOR_EXTRAORDINARIO +
                ", INDEX_INFO_VALOR_GREMIO=" + INDEX_INFO_VALOR_GREMIO +
                ", anoEmissao=" + anoEmissao +
                '}';
    }
}
