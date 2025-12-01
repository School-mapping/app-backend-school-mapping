package school.sptech;

import java.sql.Timestamp;

public class Escola {

    private Integer id;
    private String nome;
    private String codigoInep;
    private Endereco endereco;
    private Timestamp dataProcessamento;

    public Escola(String nome, String codigoInep, Endereco endereco) {
        this.nome = nome;
        this.codigoInep = codigoInep;
        this.endereco = endereco;
    }

    public Escola() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigoInep() {
        return codigoInep;
    }

    public void setCodigoInep(String codigoInep) {
        this.codigoInep = codigoInep;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Timestamp getDataProcessamento() {
        return dataProcessamento;
    }

    public void setDataProcessamento(Timestamp dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    @Override
    public String toString() {
        return "Escola{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", codigoInep='" + codigoInep + '\'' +
                ", endereco=" + endereco +
                '}';
    }
}
