package org.puclab.models.dtos;

import org.puclab.models.enums.TipoDisciplina;

import java.util.List;

public class MatriculaRequestDTO {
    public Long alunoId;
    public List<Long> disciplinasObrigatorias;
    public List<Long> disciplinasOptativas;
}