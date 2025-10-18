package school.sptech;

public class Ideb {

    private String codigoInep;
    private Double ideb;

    public Ideb(String codigoInep, Double ideb) {
        this.codigoInep = codigoInep;
        this.ideb = ideb;
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

    @Override
    public String toString() {
        return "Ideb{" +
                "codigoInep='" + codigoInep + '\'' +
                ", ideb=" + ideb +
                '}';
    }
}
