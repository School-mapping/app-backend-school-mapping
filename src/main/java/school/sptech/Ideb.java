package school.sptech;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Ideb {

    private String codigoInep;
    private Double ideb;
    private Double fluxo;
    private Integer anoEmissao;
    private Escola escola;
    private LocalDateTime dataProcessamento;

    public Ideb(String codigoInep, Double ideb, Double fluxo, Integer anoEmissao, Escola escola) {
        this.codigoInep = codigoInep;
        this.ideb = ideb;
        this.fluxo = fluxo;
        this.anoEmissao = anoEmissao;
        this.escola = escola;
    }

    public Ideb() {
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

    public Double getFluxo() {
        return fluxo;
    }

    public void setFluxo(Double fluxo) {
        this.fluxo = fluxo;
    }

    public Integer getAnoEmissao() {
        return anoEmissao;
    }

    public void setAnoEmissao(Integer anoEmissao) {
        this.anoEmissao = anoEmissao;
    }

    public Escola getEscola() {
        return escola;
    }

    public void setEscola(Escola escola) {
        this.escola = escola;
    }

    public LocalDateTime getDataProcessamento() {
        return dataProcessamento;
    }

    public void setDataProcessamento(LocalDateTime dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
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
