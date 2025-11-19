package school.sptech;

public class Token {
    private Integer id;
    private String token;
    private Boolean ativo;

    public Token(String token, Boolean ativo) {
        this.token = token;
        this.ativo = ativo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "Token{" +
                "token='" + token + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}
