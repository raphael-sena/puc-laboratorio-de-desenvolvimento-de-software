package org.puclab.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_curso")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Curso extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Curriculo> curriculos = new HashSet<>();

    private int creditos;
}
