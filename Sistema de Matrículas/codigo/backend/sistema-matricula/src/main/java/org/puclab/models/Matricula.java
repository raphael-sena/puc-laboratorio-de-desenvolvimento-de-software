package org.puclab.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.*;
import org.puclab.models.enums.StatusMatricula;
import org.puclab.models.enums.TipoDisciplina;

import java.time.LocalDate;
import java.util.HashSet;
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
    @JoinTable(name = "tb_matricula_disciplina_obrigatoria",
            joinColumns = @JoinColumn(name = "matricula_id"),
            inverseJoinColumns = @JoinColumn(name = "disciplina_id"))
    public Set<Disciplina> disciplinasObrigatorias = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "tb_matricula_disciplina_optativa",
            joinColumns = @JoinColumn(name = "matricula_id"),
            inverseJoinColumns = @JoinColumn(name = "disciplina_id"))
    public Set<Disciplina> disciplinasOptativas = new HashSet<>();

    @Enumerated
    public StatusMatricula statusMatricula;

    /**
     * Retorna todas as disciplinas da matrícula (obrigatórias + optativas)
     */
    @Transient
    public Set<Disciplina> getDisciplinas() {
        Set<Disciplina> todasDisciplinas = new HashSet<>();
        todasDisciplinas.addAll(disciplinasObrigatorias);
        todasDisciplinas.addAll(disciplinasOptativas);
        return todasDisciplinas;
    }

    /**
     * Verifica se existem vagas disponíveis para o tipo de disciplina especificado
     */
    public boolean temVagasDisponiveisPara(TipoDisciplina tipo) {
        if (tipo == TipoDisciplina.OBRIGATORIA) {
            return disciplinasObrigatorias.size() < 4;
        } else {
            return disciplinasOptativas.size() < 2;
        }
    }

    /**
     * Adiciona uma disciplina respeitando o limite de cada tipo
     * @return true se a disciplina foi adicionada, false caso contrário
     */
    public boolean adicionarDisciplina(Disciplina disciplina, TipoDisciplina tipo) {
        if (!temVagasDisponiveisPara(tipo)) {
            return false;
        }

        if (tipo == TipoDisciplina.OBRIGATORIA) {
            return disciplinasObrigatorias.add(disciplina);
        } else {
            return disciplinasOptativas.add(disciplina);
        }
    }

    /**
     * Remove uma disciplina da matrícula
     * @return true se a disciplina foi removida, false caso contrário
     */
    public boolean removerDisciplina(Disciplina disciplina) {
        boolean removidaObrigatoria = disciplinasObrigatorias.remove(disciplina);
        if (removidaObrigatoria) {
            return true;
        }
        return disciplinasOptativas.remove(disciplina);
    }
}