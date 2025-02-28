package org.puclab.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.*;
import org.puclab.models.enums.StatusDisciplina;

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

    /**
     * Verifica se a disciplina pode continuar ativa ou se deve ser cancelada
     * (chamado no final do período de matrículas)
     */
    public void verificarStatusDisciplina() {
        long numInscritos = contarAlunosMatriculados();

        if (numInscritos < 3) {
            this.status = StatusDisciplina.DESATIVADA; // Cancela a disciplina
        } else {
            this.status = StatusDisciplina.ATIVA; // Mantém ativa
        }
    }

    /**
     * Conta quantos alunos estão matriculados nesta disciplina
     */
    public long contarAlunosMatriculados() {
        long numInscritos = 0;

        for (PanacheEntityBase entity : Matricula.listAll()) {
            Matricula matricula = (Matricula) entity; // Converte explicitamente para Matricula
            if (matricula.getDisciplinas().contains(this)) {
                numInscritos++;
            }
        }

        return numInscritos;
    }

    /**
     * Verifica se ainda é possível aceitar matrículas nesta disciplina
     */
    public boolean podeMatricular() {
        long numInscritos = contarAlunosMatriculados();

        // Permite matrícula apenas se a disciplina estiver ativa e tiver menos de 60 alunos
        return this.status == StatusDisciplina.ATIVA && numInscritos < 60;
    }

}
