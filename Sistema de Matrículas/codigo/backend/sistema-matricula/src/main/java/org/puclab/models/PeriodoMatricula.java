package org.puclab.models;

import java.time.LocalDate;

public interface PeriodoMatricula {

    public LocalDate gerarPeriodoMatricula(LocalDate dataInicio, LocalDate dataFim);
    public LocalDate obterPeriodoMatricula();
}
