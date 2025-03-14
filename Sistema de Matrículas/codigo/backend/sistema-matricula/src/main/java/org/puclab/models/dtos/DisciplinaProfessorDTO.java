
package org.puclab.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DisciplinaProfessorDTO {
    private Long disciplinaId;
    private String disciplinaNome;
    private List<AlunoDTO> alunos; // Lista de alunos que cursam esta disciplina

}
