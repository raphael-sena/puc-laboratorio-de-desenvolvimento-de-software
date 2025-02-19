package models;

import java.util.Set;

public class Curso {
    private long id;
    private String nome;
    private Set<Curriculo> curriculos;
    private int creditos;

    public Curso() {

    }
    public Curso(long id, String nome, int creditos) {
        this.id = id;
        this.nome = nome;
        this.creditos = creditos;
    }

    public Set<Curriculo> obterCurriculos() {
        return curriculos;
    }
}
