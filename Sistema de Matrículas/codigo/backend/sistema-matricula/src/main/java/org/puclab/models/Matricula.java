package org.puclab.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.*;
import org.puclab.models.enums.StatusMatricula;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "tb_matricula")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Matricula extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public LocalDate dataMatricula;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    public Aluno aluno;

    @ManyToMany
    @JoinTable(name = "tb_matricula_disciplina",
            joinColumns = @JoinColumn(name = "matricula_id"),
            inverseJoinColumns = @JoinColumn(name = "disciplina_id"))
    public Set<Disciplina> disciplinas;

    @Enumerated
    public StatusMatricula statusMatricula;


    // TODO
    public void matricularAluno(Aluno aluno) {

    }

    // TODO
    public void desmatricularAluno(Aluno aluno) {

    }


}
