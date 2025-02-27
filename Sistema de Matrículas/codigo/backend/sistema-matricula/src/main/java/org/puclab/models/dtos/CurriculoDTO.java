package org.puclab.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.puclab.models.Disciplina;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CurriculoDTO {
    private Long id;
    private String nome;
    private Set<Disciplina> disciplinas;
}
