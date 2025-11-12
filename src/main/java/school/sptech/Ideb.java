package school.sptech;

import java.time.Year;

public class Ideb {

    private String codigoInep;
    private Double ideb;
    private Year anoEmissao;
    private Escola escola;

    public Ideb(String codigoInep, Double ideb, Year anoEmissao, Escola escola) {
        this.codigoInep = codigoInep;
        this.ideb = ideb;
        this.anoEmissao = anoEmissao;
        this.escola = escola;
    }

    public String getCodigoInep() {
        return codigoInep;
    }

    public void setCodigoInep(String codigoInep) {
        this.codigoInep = codigoInep;
    }

    public Double getIdeb() {
        return ideb;
    }

    public void setIdeb(Double ideb) {
        this.ideb = ideb;
    }

    public Year getAnoEmissao() {
        return anoEmissao;
    }

    public void setAnoEmissao(Year anoEmissao) {
        this.anoEmissao = anoEmissao;
    }

    public Escola getEscola() {
        return escola;
    }

    public void setEscola(Escola escola) {
        this.escola = escola;
    }

    @Override
    public String toString() {
        return "Ideb{" +
                "codigoInep='" + codigoInep + '\'' +
                ", ideb=" + ideb +
                ", anoEmissao=" + anoEmissao +
                ", escola=" + escola +
                '}';
    }
}
