package school.sptech;

public class PlanilhaIdeb extends Planilha {
    Integer INDEX_INEP_ID;
    Integer INDEX_NOTA_IDEB;
    Integer INDEX_NOTA_FLUXO;
    Integer INDEX_NOTA_APRENDIZADO;
    Integer INDEX_NOTA_MAT;
    Integer INDEX_NOTA_LP;

    public PlanilhaIdeb(String nome, Integer anoEmissao, Integer INDEX_INEP_ID, Integer INDEX_NOTA_IDEB, Integer INDEX_NOTA_FLUXO, Integer INDEX_NOTA_APRENDIZADO, Integer INDEX_NOTA_MAT, Integer INDEX_NOTA_LP) {
        super(nome, anoEmissao);
        this.INDEX_INEP_ID = INDEX_INEP_ID;
        this.INDEX_NOTA_IDEB = INDEX_NOTA_IDEB;
        this.INDEX_NOTA_FLUXO = INDEX_NOTA_FLUXO;
        this.INDEX_NOTA_APRENDIZADO = INDEX_NOTA_APRENDIZADO;
        this.INDEX_NOTA_MAT = INDEX_NOTA_MAT;
        this.INDEX_NOTA_LP = INDEX_NOTA_LP;
    }

    public Integer getINDEX_INEP_ID() {
        return INDEX_INEP_ID;
    }

    public void setINDEX_INEP_ID(Integer INDEX_INEP_ID) {
        this.INDEX_INEP_ID = INDEX_INEP_ID;
    }

    public Integer getINDEX_NOTA_IDEB() {
        return INDEX_NOTA_IDEB;
    }

    public void setINDEX_NOTA_IDEB(Integer INDEX_NOTA_IDEB) {
        this.INDEX_NOTA_IDEB = INDEX_NOTA_IDEB;
    }

    public Integer getINDEX_NOTA_FLUXO() {
        return INDEX_NOTA_FLUXO;
    }

    public void setINDEX_NOTA_FLUXO(Integer INDEX_NOTA_FLUXO) {
        this.INDEX_NOTA_FLUXO = INDEX_NOTA_FLUXO;
    }

    public Integer getINDEX_NOTA_APRENDIZADO() {
        return INDEX_NOTA_APRENDIZADO;
    }

    public void setINDEX_NOTA_APRENDIZADO(Integer INDEX_NOTA_APRENDIZADO) {
        this.INDEX_NOTA_APRENDIZADO = INDEX_NOTA_APRENDIZADO;
    }

    public Integer getINDEX_NOTA_MAT() {
        return INDEX_NOTA_MAT;
    }

    public void setINDEX_NOTA_MAT(Integer INDEX_NOTA_MAT) {
        this.INDEX_NOTA_MAT = INDEX_NOTA_MAT;
    }

    public Integer getINDEX_NOTA_LP() {
        return INDEX_NOTA_LP;
    }

    public void setINDEX_NOTA_LP(Integer INDEX_NOTA_LP) {
        this.INDEX_NOTA_LP = INDEX_NOTA_LP;
    }
}
