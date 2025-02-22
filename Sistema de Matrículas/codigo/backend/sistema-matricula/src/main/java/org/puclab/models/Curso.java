package org.puclab.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "tb_disciplina")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany
    private Set<Curriculo> curriculos;

    private int creditos;
}
