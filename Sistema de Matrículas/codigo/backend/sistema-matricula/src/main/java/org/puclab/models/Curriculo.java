package org.puclab.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "tb_curriculo")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Curriculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @ManyToMany
    private Set<Disciplina> disciplinas;

    // TODO
    public void criarDisciplina(Disciplina disciplina){

    }
}
