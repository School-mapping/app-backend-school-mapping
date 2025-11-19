package school.sptech;

import java.time.LocalDateTime;

public class Log {
    private Integer id;
    private Integer usuarioId;
    private LocalDateTime dataHoraOrigem;
    private Nivel nivel;
    private String descricao;
    private String origem;

    public Log(Integer usuarioId, LocalDateTime dataHoraOrigem, Nivel nivel, String descricao, String origem) {
        this.usuarioId = usuarioId;
        this.dataHoraOrigem = dataHoraOrigem;
        this.nivel = nivel;
        this.descricao = descricao;
        this.origem = origem;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LocalDateTime getDataHoraOrigem() {
        return dataHoraOrigem;
    }

    public void setDataHoraOrigem(LocalDateTime dataHoraOrigem) {
        this.dataHoraOrigem = dataHoraOrigem;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", usuarioId=" + usuarioId +
                ", dataHoraOrigem=" + dataHoraOrigem +
                ", nivel=" + nivel +
                ", descricao='" + descricao + '\'' +
                ", origem='" + origem + '\'' +
                '}';
    }
}
