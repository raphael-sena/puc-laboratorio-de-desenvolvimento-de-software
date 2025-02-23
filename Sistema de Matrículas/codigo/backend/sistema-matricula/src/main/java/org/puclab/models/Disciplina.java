package org.puclab.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
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
public class Disciplina extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String nome;

    @ManyToOne
    @JoinColumn(name = "disciplina_id")
    public Professor professor;

    @Enumerated(EnumType.STRING)
    public StatusDisciplina status;

    @ManyToMany(mappedBy = "disciplinas")
    public Set<Matricula> matriculas;

    // TODO
    private void cancelarDisciplina() {

    }
}
