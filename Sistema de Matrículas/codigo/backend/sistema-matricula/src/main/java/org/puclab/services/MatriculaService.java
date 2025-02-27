package org.puclab.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.puclab.models.Matricula;
import org.puclab.models.Aluno;
import org.puclab.models.Disciplina;
import org.puclab.models.enums.StatusMatricula;
import org.puclab.exceptions.BusinessException;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@ApplicationScoped
public class MatriculaService {

    @Inject
    SecretariaService secretariaService;

    /**
     * Matricula um aluno em disciplinas, verificando se está dentro do período de matrícula.
     */
    @Transactional
    public Matricula matricularAlunoEmDisciplinas(long alunoId, List<Long> disciplinaIds) {
        // Verifica se está dentro do período de matrícula
        verificarPeriodoMatricula();

        // Busca o aluno pelo ID
        Aluno aluno = Aluno.<Aluno>findByIdOptional(alunoId)
                .orElseThrow(() -> new BusinessException("Aluno não encontrado com ID: " + alunoId));

        // Busca matrícula existente ou cria uma nova
        Matricula matricula = Matricula.<Matricula>find("aluno.id", alunoId).firstResultOptional().orElseGet(() -> {
            Matricula novaMatricula = new Matricula();
            novaMatricula.setAluno(aluno);
            novaMatricula.setDataMatricula(LocalDate.now());
            novaMatricula.setStatusMatricula(StatusMatricula.ATIVA);
            novaMatricula.setDisciplinas(new HashSet<>());
            return novaMatricula;
        });

        // Reativa matrícula se estiver desativada
        if (StatusMatricula.DESATIVADO.equals(matricula.getStatusMatricula())) {
            matricula.setStatusMatricula(StatusMatricula.ATIVA);
            matricula.setDataMatricula(LocalDate.now());
        }

        // Adiciona as disciplinas à matrícula
        for (Long disciplinaId : disciplinaIds) {
            Disciplina disciplina = Disciplina.<Disciplina>findByIdOptional(disciplinaId)
                    .orElseThrow(() -> new BusinessException("Disciplina não encontrada com ID: " + disciplinaId));

            matricula.getDisciplinas().add(disciplina);
        }

        matricula.persist();
        return matricula;
    }

    /**
     * Desmatricula um aluno de disciplinas selecionadas.
     */
    @Transactional
    public Matricula desmatricularAlunoEmDisciplinas(long alunoId, List<Long> disciplinaIds) {
        // Verifica se está dentro do período de matrícula
        verificarPeriodoMatricula();

        // Verifica se o aluno existe
        Aluno.<Aluno>findByIdOptional(alunoId)
                .orElseThrow(() -> new BusinessException("Aluno não encontrado com ID: " + alunoId));

        // Busca a matrícula do aluno
        Matricula matricula = Matricula.<Matricula>find("aluno.id", alunoId).firstResultOptional()
                .orElseThrow(() -> new BusinessException("Matrícula não encontrada para o aluno com ID: " + alunoId));

        // Remove as disciplinas da matrícula
        for (Long disciplinaId : disciplinaIds) {
            Disciplina disciplina = Disciplina.<Disciplina>findByIdOptional(disciplinaId)
                    .orElseThrow(() -> new BusinessException("Disciplina não encontrada com ID: " + disciplinaId));

            if (!matricula.getDisciplinas().contains(disciplina)) {
                throw new BusinessException("Aluno não está matriculado na disciplina com ID: " + disciplinaId);
            }

            matricula.getDisciplinas().remove(disciplina);
        }

        // Se não houver mais disciplinas, atualiza o status da matrícula
        if (matricula.getDisciplinas().isEmpty()) {
            matricula.setStatusMatricula(StatusMatricula.DESATIVADO);
        }

        matricula.persist();
        return matricula;
    }

    /**
     * Verifica se está dentro do período de matrícula definido pela secretaria.
     */
    private void verificarPeriodoMatricula() {
        LocalDate hoje = LocalDate.now();
        LocalDate[] periodo = secretariaService.obterPeriodoMatricula();

        if (periodo == null) {
            throw new BusinessException("Período de matrícula não configurado");
        }

        LocalDate dataInicio = periodo[0];
        LocalDate dataFim = periodo[1];

        if (hoje.isBefore(dataInicio) || hoje.isAfter(dataFim)) {
            throw new BusinessException(
                    String.format("Fora do período de matrícula. Período atual: %s a %s",
                            dataInicio, dataFim)
            );
        }
    }
}