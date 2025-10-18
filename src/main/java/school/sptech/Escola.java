package school.sptech;

public class Escola {

    private String nome;
    private String codigoInep;
    private Double ideb;
    private String cep;
    private Long latitude;
    private Long longitude;

    public Escola(String nome, String codigoInep, Double ideb, String cep, Long latitude, Long longitude) {
        this.nome = nome;
        this.codigoInep = codigoInep;
        this.ideb = ideb;
        this.cep = cep;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public Double getIdeb() {
        return ideb;
    }

    public void setIdeb(Double ideb) {
        this.ideb = ideb;
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

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Escola{" +
                "nome='" + nome + '\'' +
                ", codigoInep='" + codigoInep + '\'' +
                ", ideb=" + ideb +
                ", cep='" + cep + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
