package org.puclab.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.puclab.models.Aluno;
import org.puclab.models.Disciplina;
import org.puclab.models.Matricula;
import org.puclab.models.enums.StatusMatricula;
import org.puclab.exceptions.BusinessException;
import org.puclab.models.enums.TipoDisciplina;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ApplicationScoped
public class MatriculaService {

    @Inject
    private SecretariaService secretariaService;

    @Transactional
    public Matricula matricularAlunoEmDisciplinas(long alunoId, Map<TipoDisciplina, List<Long>> disciplinasMap) {
        validatePeriodoMatricula();

        Aluno aluno = (Aluno) Aluno.findByIdOptional(alunoId)
                .orElseThrow(() -> new BusinessException("Aluno não encontrado com ID: " + alunoId));

        Matricula matricula = (Matricula) Matricula.find("aluno.id", alunoId)
                .firstResultOptional()
                .orElseGet(() -> createNewMatricula(aluno));

        reactivateIfNeeded(matricula);

        List<Long> obrigatoriaIds = disciplinasMap.getOrDefault(TipoDisciplina.OBRIGATORIA, List.of());
        if (obrigatoriaIds.size() > 4) {
            throw new BusinessException("Não é possível matricular em mais de 4 disciplinas obrigatórias");
        }

        List<Long> optativaIds = disciplinasMap.getOrDefault(TipoDisciplina.OPTATIVA, List.of());
        if (optativaIds.size() > 2) {
            throw new BusinessException("Não é possível matricular em mais de 2 disciplinas optativas");
        }

        addDisciplinas(obrigatoriaIds, matricula, true);
        addDisciplinas(optativaIds, matricula, false);

        matricula.persist();
        return matricula;
    }

    @Transactional
    public Matricula matricularAluno(long alunoId, List<Long> disciplinasObrigatorias, List<Long> disciplinasOptativas) {
        return matricularAlunoEmDisciplinas(alunoId, Map.of(
                TipoDisciplina.OBRIGATORIA, disciplinasObrigatorias,
                TipoDisciplina.OPTATIVA, disciplinasOptativas
        ));
    }

    @Transactional
    public Matricula desmatricularAlunoEmDisciplinas(long alunoId, List<Long> disciplinaIds) {
        validatePeriodoMatricula();

        Aluno.findByIdOptional(alunoId)
                .orElseThrow(() -> new BusinessException("Aluno não encontrado com ID: " + alunoId));

        Matricula matricula = (Matricula) Matricula.find("aluno.id", alunoId)
                .firstResultOptional()
                .orElseThrow(() -> new BusinessException("Matrícula não encontrada para o aluno com ID: " + alunoId));

        Set<Disciplina> allDisciplinas = getAllDisciplinas(matricula);

        for (Long disciplinaId : disciplinaIds) {
            Disciplina disciplina = (Disciplina) Disciplina.findByIdOptional(disciplinaId)
                    .orElseThrow(() -> new BusinessException("Disciplina não encontrada com ID: " + disciplinaId));
            if (!allDisciplinas.contains(disciplina)) {
                throw new BusinessException("Aluno não está matriculado na disciplina com ID: " + disciplinaId);
            }
            removeDisciplina(matricula, disciplina);
        }

        if (getAllDisciplinas(matricula).isEmpty()) {
            matricula.setStatusMatricula(StatusMatricula.DESATIVADO);
        }

        matricula.persist();
        return matricula;
    }

    private void validatePeriodoMatricula() {
        LocalDate hoje = LocalDate.now();
        LocalDate[] periodo = secretariaService.obterPeriodoMatricula();

        if (periodo == null) {
            throw new BusinessException("Período de matrícula não configurado");
        }

        LocalDate dataInicio = periodo[0];
        LocalDate dataFim = periodo[1];

        if (hoje.isBefore(dataInicio) || hoje.isAfter(dataFim)) {
            throw new BusinessException(String.format("Fora do período de matrícula. Período atual: %s a %s", dataInicio, dataFim));
        }
    }

    private Matricula createNewMatricula(Aluno aluno) {
        Matricula nova = new Matricula();
        nova.setAluno(aluno);
        nova.setDataMatricula(LocalDate.now());
        nova.setStatusMatricula(StatusMatricula.ATIVA);
        nova.setDisciplinasObrigatorias(new HashSet<>());
        nova.setDisciplinasOptativas(new HashSet<>());
        return nova;
    }

    private void reactivateIfNeeded(Matricula matricula) {
        if (StatusMatricula.DESATIVADO.equals(matricula.getStatusMatricula())) {
            matricula.setStatusMatricula(StatusMatricula.ATIVA);
            matricula.setDataMatricula(LocalDate.now());
        }
    }

    private void addDisciplinas(List<Long> disciplinaIds, Matricula matricula, boolean obrigatoria) {
        for (Long id : disciplinaIds) {
            Disciplina disciplina = (Disciplina) Disciplina.findByIdOptional(id)
                    .orElseThrow(() -> new BusinessException(
                            (obrigatoria ? "Disciplina obrigatória" : "Disciplina optativa") +
                                    " não encontrada com ID: " + id));
            if (obrigatoria) {
                if (!disciplina.podeMatricular()){
                    throw new BusinessException("Disciplina não pode ser matriculada: " + disciplina.getNome());
                }
                matricula.getDisciplinasObrigatorias().add(disciplina);
            } else {
                if (!disciplina.podeMatricular()){
                    throw new BusinessException("Disciplina não pode ser matriculada: " + disciplina.getNome());
                }
                matricula.getDisciplinasOptativas().add(disciplina);
            }
        }
    }

    private void removeDisciplina(Matricula matricula, Disciplina disciplina) {
        if (matricula.getDisciplinasObrigatorias().remove(disciplina) ||
                matricula.getDisciplinasOptativas().remove(disciplina)) {
        }
    }

    private Set<Disciplina> getAllDisciplinas(Matricula matricula) {
        Set<Disciplina> todas = new HashSet<>();
        if (matricula.getDisciplinasObrigatorias() != null) {
            todas.addAll(matricula.getDisciplinasObrigatorias());
        }
        if (matricula.getDisciplinasOptativas() != null) {
            todas.addAll(matricula.getDisciplinasOptativas());
        }
        return todas;
    }
}
