package org.puclab.models;

import models.enums.StatusDisciplina;

import java.util.Set;

public class Disciplina {
    private long id;
    private String nome;
    private Professor professor;
    private StatusDisciplina status;
    private Set<Matricula> matriculas;

    public Disciplina(long id, String nome, Professor professor, StatusDisciplina status) {
        this.id = id;
        this.nome = nome;
        this.professor = professor;
        this.status = status;
    }

    public Disciplina() {
    }

    private void cancelarDisciplina() {

    }
}
