package org.puclab.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Professor extends Usuario {

    @OneToMany(mappedBy = "professor", orphanRemoval = true)
    public Set<Disciplina> disciplinas;

    public Professor(String nome, String senha) {
        super(nome, senha);
    }

    // TODO
    public Set<Aluno> obterAlunosDisciplina(Disciplina disciplina) {
        return new HashSet<>();
    }
}
