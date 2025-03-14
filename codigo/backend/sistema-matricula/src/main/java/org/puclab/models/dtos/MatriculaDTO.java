package org.puclab.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.puclab.models.enums.StatusMatricula;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MatriculaDTO {
    public Long id;
    public LocalDate dataMatricula;
    public Long alunoId;
    public Set<DisciplinaDTO> disciplinas;
    public StatusMatricula statusMatricula;
}
