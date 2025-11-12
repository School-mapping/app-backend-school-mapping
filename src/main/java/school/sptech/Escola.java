package school.sptech;

public class Escola {

    private Integer id;
    private String nome;
    private String codigoInep;
    private String cep;
    private Endereco endereco;

    public Escola(String nome, String codigoInep, String cep, Endereco endereco) {
        this.nome = nome;
        this.codigoInep = codigoInep;
        this.cep = cep;
        this.endereco = endereco;
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
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

    public Regiao getRegiao() {
        return regiao;
    }

    public void setRegiao(Regiao regiao) {
        this.regiao = regiao;
    }

    @Override
    public String toString() {
        return "Escola{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", codigoInep='" + codigoInep + '\'' +
                ", ideb=" + ideb +
                ", cep='" + cep + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", endereco=" + endereco +
                ", regiao=" + regiao +
                '}';
    }
}
