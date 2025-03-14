package org.puclab.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AlunoResponseDTO {
    private Long id;
    private String nome;
    public Set<MatriculaDTO> matriculas;
}
