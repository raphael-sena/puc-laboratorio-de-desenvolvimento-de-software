package org.puclab.models;

import jakarta.persistence.*;
import lombok.*;
import org.puclab.models.enums.StatusMatricula;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tb_matricula")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataMatricula;

    @ManyToOne
    private Aluno aluno;

//    private Set<Disciplina> disciplinas;

    @Enumerated
    private StatusMatricula statusMatricula;


    // TODO
    public void matricularAluno(Aluno aluno) {

    }

    // TODO
    public void desmatricularAluno(Aluno aluno) {

    }


}
