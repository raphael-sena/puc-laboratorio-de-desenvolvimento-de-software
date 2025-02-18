package models;

import java.util.Objects;

public class Aluno extends Usuario {

    private String matricula;

    public Aluno(String matricula) {
        this.matricula = matricula;
    }

    public Aluno(Long id, String nome, String senha, String matricula) {
        super(id, nome, senha);
        this.matricula = matricula;
    }

    public Aluno() {
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Aluno aluno = (Aluno) o;
        return Objects.equals(matricula, aluno.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), matricula);
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "matricula='" + matricula + '\'' +
                '}';
    }
}
