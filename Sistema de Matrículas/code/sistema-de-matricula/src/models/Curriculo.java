package models;

import java.util.Set;

public class Curriculo {
    private long id;
    private String nome;
    private Set<Disciplina> disciplinas;

    public Curriculo() {
    }

    public Curriculo(long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public void criarDisciplina(Disciplina disciplina){

    }
}
