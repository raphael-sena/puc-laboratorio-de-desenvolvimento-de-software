package org.puclab.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class PeriodoMatriculaDTO {
    public LocalDate dataInicio;
    public LocalDate dataFim;
}
