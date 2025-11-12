package school.sptech;

import java.time.LocalDate;

public class Usuario {
    private Integer id;
    private Integer empresaId;
    private String nome;
    private Integer perfilId;
    private String email;
    private String senha;
    private String tipo;
    private LocalDate dataCriacao;

    public Usuario(Integer id, Integer empresaId, String nome, Integer perfilId, String email, String senha, String tipo, LocalDate dataCriacao) {
        this.id = id;
        this.empresaId = empresaId;
        this.nome = nome;
        this.perfilId = perfilId;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
        this.dataCriacao = dataCriacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(Integer perfilId) {
        this.perfilId = perfilId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", empresaId=" + empresaId +
                ", nome='" + nome + '\'' +
                ", perfilId=" + perfilId +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", tipo='" + tipo + '\'' +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
}
