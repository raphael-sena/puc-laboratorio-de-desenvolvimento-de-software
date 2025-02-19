package models;

import models.enums.StatusMatricula;

import java.time.LocalDate;
import java.util.Objects;

public class Matricula {

    private Long id;
    private LocalDate dataMatricula;
    private Aluno aluno;
//    private Set<Disciplina> disciplinas;
    private StatusMatricula statusMatricula;

    public Matricula() {
    }

    public Matricula(Long id, LocalDate dataMatricula, Aluno aluno, StatusMatricula statusMatricula) {
        this.id = id;
        this.dataMatricula = dataMatricula;
        this.aluno = aluno;
        this.statusMatricula = statusMatricula;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(LocalDate dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public StatusMatricula getStatusMatricula() {
        return statusMatricula;
    }

    public void setStatusMatricula(StatusMatricula statusMatricula) {
        this.statusMatricula = statusMatricula;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matricula matricula = (Matricula) o;
        return Objects.equals(id, matricula.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Matricula{" +
                "id=" + id +
                ", dataMatricula=" + dataMatricula +
                ", aluno=" + aluno +
                ", statusMatricula=" + statusMatricula +
                '}';
    }

    // TODO
    public void matricularAluno(Aluno aluno) {

    }

    // TODO
    public void desmatricularAluno(Aluno aluno) {

    }


}
