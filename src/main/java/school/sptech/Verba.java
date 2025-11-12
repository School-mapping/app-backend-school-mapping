package school.sptech;

import java.math.BigDecimal;
import java.time.Year;

public class Verba {
    private Integer id;
    private Year ano;
    private String portariaSME;
    private BigDecimal valorPrimeiraParcela;
    private BigDecimal valorSegundaParcela;
    private BigDecimal valorTerceiraParcela;
    private BigDecimal valorVulnerabilidade;
    private BigDecimal valorExtraordinario;
    private BigDecimal valorGremio;

    public Verba(Year ano, String portariaSME, BigDecimal valorPrimeiraParcela, BigDecimal valorSegundaParcela, BigDecimal valorTerceiraParcela, BigDecimal valorVulnerabilidade, BigDecimal valorExtraordinario, BigDecimal valorGremio) {
        this.ano = ano;
        this.portariaSME = portariaSME;
        this.valorPrimeiraParcela = valorPrimeiraParcela;
        this.valorSegundaParcela = valorSegundaParcela;
        this.valorTerceiraParcela = valorTerceiraParcela;
        this.valorVulnerabilidade = valorVulnerabilidade;
        this.valorExtraordinario = valorExtraordinario;
        this.valorGremio = valorGremio;
    }

    public Year getAno() {
        return ano;
    }

    public void setAno(Year ano) {
        this.ano = ano;
    }

    public String getPortariaSME() {
        return portariaSME;
    }

    public void setPortariaSME(String portariaSME) {
        this.portariaSME = portariaSME;
    }

    public BigDecimal getValorPrimeiraParcela() {
        return valorPrimeiraParcela;
    }

    public void setValorPrimeiraParcela(BigDecimal valorPrimeiraParcela) {
        this.valorPrimeiraParcela = valorPrimeiraParcela;
    }

    public BigDecimal getValorSegundaParcela() {
        return valorSegundaParcela;
    }

    public void setValorSegundaParcela(BigDecimal valorSegundaParcela) {
        this.valorSegundaParcela = valorSegundaParcela;
    }

    public BigDecimal getValorTerceiraParcela() {
        return valorTerceiraParcela;
    }

    public void setValorTerceiraParcela(BigDecimal valorTerceiraParcela) {
        this.valorTerceiraParcela = valorTerceiraParcela;
    }

    public BigDecimal getValorVulnerabilidade() {
        return valorVulnerabilidade;
    }

    public void setValorVulnerabilidade(BigDecimal valorVulnerabilidade) {
        this.valorVulnerabilidade = valorVulnerabilidade;
    }

    public BigDecimal getValorExtraordinario() {
        return valorExtraordinario;
    }

    public void setValorExtraordinario(BigDecimal valorExtraordinario) {
        this.valorExtraordinario = valorExtraordinario;
    }

    public BigDecimal getValorGremio() {
        return valorGremio;
    }

    public void setValorGremio(BigDecimal valorGremio) {
        this.valorGremio = valorGremio;
    }

    @Override
    public String toString() {
        return "Verba{" +
                "ano=" + ano +
                ", portariaSME='" + portariaSME + '\'' +
                ", valorPrimeiraParcela=" + valorPrimeiraParcela +
                ", valorSegundaParcela=" + valorSegundaParcela +
                ", valorTerceiraParcela=" + valorTerceiraParcela +
                ", valorVulnerabilidade=" + valorVulnerabilidade +
                ", valorExtraordinario=" + valorExtraordinario +
                ", valorGremio=" + valorGremio +
                '}';
    }
}
