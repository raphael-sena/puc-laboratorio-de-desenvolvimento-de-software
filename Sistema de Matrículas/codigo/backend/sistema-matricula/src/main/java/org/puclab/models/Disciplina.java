package org.puclab.models;

import jakarta.persistence.*;
import lombok.*;
import org.puclab.models.enums.StatusDisciplina;

import java.util.Set;

@Entity
@Table(name = "tb_disciplina")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;

    @ManyToOne
    private Professor professor;

    @Enumerated(EnumType.STRING)
    private StatusDisciplina status;

    @ManyToMany
    private Set<Aluno> alunos;

    // TODO
    private void cancelarDisciplina() {

    }
}
