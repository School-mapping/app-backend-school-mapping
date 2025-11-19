package school.sptech;

public enum Regiao {
    NORTE(1),
    LESTE(2),
    SUL(3),
    CENTRO(4),
    OESTE(5);

    private final Integer id;

    Regiao(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
