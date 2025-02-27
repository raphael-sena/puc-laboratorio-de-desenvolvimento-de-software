package org.puclab.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
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
public class Curriculo extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Disciplina> disciplinas;
}
