package school.sptech;

import java.time.Year;

public class Verba {
    private Integer id;
    private Integer ano;
    private String nomeEscola;
    private Double valorPrimeiraParcela;
    private Double valorSegundaParcela;
    private Double valorTerceiraParcela;
    private Double valorVulnerabilidade;
    private Double valorExtraordinario;
    private Double valorGremio;

    public Verba(Integer ano, String nomeEscola, Double valorPrimeiraParcela, Double valorSegundaParcela, Double valorTerceiraParcela, Double valorVulnerabilidade, Double valorExtraordinario, Double valorGremio) {
        this.ano = ano;
        this.nomeEscola = nomeEscola;
        this.valorPrimeiraParcela = valorPrimeiraParcela;
        this.valorSegundaParcela = valorSegundaParcela;
        this.valorTerceiraParcela = valorTerceiraParcela;
        this.valorVulnerabilidade = valorVulnerabilidade;
        this.valorExtraordinario = valorExtraordinario;
        this.valorGremio = valorGremio;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Double getValorPrimeiraParcela() {
        return valorPrimeiraParcela;
    }

    public void setValorPrimeiraParcela(Double valorPrimeiraParcela) {
        this.valorPrimeiraParcela = valorPrimeiraParcela;
    }

    public Double getValorSegundaParcela() {
        return valorSegundaParcela;
    }

    public void setValorSegundaParcela(Double valorSegundaParcela) {
        this.valorSegundaParcela = valorSegundaParcela;
    }

    public Double getValorTerceiraParcela() {
        return valorTerceiraParcela;
    }

    public void setValorTerceiraParcela(Double valorTerceiraParcela) {
        this.valorTerceiraParcela = valorTerceiraParcela;
    }

    public Double getValorVulnerabilidade() {
        return valorVulnerabilidade;
    }

    public void setValorVulnerabilidade(Double valorVulnerabilidade) {
        this.valorVulnerabilidade = valorVulnerabilidade;
    }

    public Double getValorExtraordinario() {
        return valorExtraordinario;
    }

    public void setValorExtraordinario(Double valorExtraordinario) {
        this.valorExtraordinario = valorExtraordinario;
    }

    public Double getValorGremio() {
        return valorGremio;
    }

    public void setValorGremio(Double valorGremio) {
        this.valorGremio = valorGremio;
    }

    public String getNomeEscola() {
        return nomeEscola;
    }

    public void setNomeEscola(String nomeEscola) {
        this.nomeEscola = nomeEscola;
    }

    @Override
    public String toString() {
        return "Verba{" +
                "id=" + id +
                ", ano=" + ano +
                ", nomeEscola='" + nomeEscola + '\'' +
                ", valorPrimeiraParcela=" + valorPrimeiraParcela +
                ", valorSegundaParcela=" + valorSegundaParcela +
                ", valorTerceiraParcela=" + valorTerceiraParcela +
                ", valorVulnerabilidade=" + valorVulnerabilidade +
                ", valorExtraordinario=" + valorExtraordinario +
                ", valorGremio=" + valorGremio +
                '}';
    }
}
