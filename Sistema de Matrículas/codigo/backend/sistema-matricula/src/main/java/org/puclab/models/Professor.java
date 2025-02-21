package org.puclab.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_professor")
public class Professor extends Usuario {

    private Set<Disciplina> disciplinas;

    public Professor(Set<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public Professor(Long id, String nome, String senha, Set<Disciplina> disciplinas) {
        super(id, nome, senha);
        this.disciplinas = disciplinas;
    }

    public Professor() {
    }

    public Set<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(Set<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "disciplinas=" + disciplinas +
                '}';
    }

    public Set<Aluno> obterAlunosDisciplina(Disciplina disciplina) {
        return new HashSet<>();
    }
}
