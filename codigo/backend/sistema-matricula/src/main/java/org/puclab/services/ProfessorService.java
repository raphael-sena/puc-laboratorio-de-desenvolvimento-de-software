// ProfessorService.java (ou DisciplinaService, se preferir)
package org.puclab.services;

import jakarta.enterprise.context.ApplicationScoped;
import org.puclab.models.Aluno;
import org.puclab.models.Disciplina;
import org.puclab.models.Matricula;
import org.puclab.models.dtos.AlunoDTO;
import org.puclab.models.dtos.DisciplinaProfessorDTO;

import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProfessorService {

    public List<DisciplinaProfessorDTO> findDisciplinasEAlunos(Long professorId) {
        // 1) Buscar todas as disciplinas do professor
        List<Disciplina> disciplinas = Disciplina.list("professor.id", professorId);
        if (disciplinas.isEmpty()) {
            return Collections.emptyList();
        }

        // 2) Extrair IDs das disciplinas
        List<Long> disciplinaIds = disciplinas.stream()
                .map(d -> d.id)
                .collect(Collectors.toList());

        // 3) Buscar matrículas que contenham essas disciplinas
        // Podemos fazer uma única query com 'OR' (obrigatórias ou optativas).
        String jpql = "SELECT DISTINCT m " +
                "FROM Matricula m " +
                "JOIN m.disciplinasObrigatorias dob " +
                "JOIN m.disciplinasOptativas dop " +
                "WHERE dob.id IN ?1 OR dop.id IN ?1";

        List<Matricula> matriculas = Matricula.find(jpql, disciplinaIds).list();

        // 4) Criar um map Disciplina.id -> Set<Aluno>
        Map<Long, Set<Aluno>> mapDisciplinaAlunos = new HashMap<>();
        for (Disciplina d : disciplinas) {
            mapDisciplinaAlunos.put(d.id, new HashSet<>());
        }

        // Percorrer as matrículas e preencher o map
        for (Matricula m : matriculas) {
            // Disciplinas obrigatórias
            for (Disciplina dObg : m.disciplinasObrigatorias) {
                if (mapDisciplinaAlunos.containsKey(dObg.id)) {
                    mapDisciplinaAlunos.get(dObg.id).add(m.aluno);
                }
            }
            // Disciplinas optativas
            for (Disciplina dOpt : m.disciplinasOptativas) {
                if (mapDisciplinaAlunos.containsKey(dOpt.id)) {
                    mapDisciplinaAlunos.get(dOpt.id).add(m.aluno);
                }
            }
        }

        // 5) Montar a lista de DisciplinaProfessorDTO
        List<DisciplinaProfessorDTO> result = new ArrayList<>();
        for (Disciplina d : disciplinas) {
            DisciplinaProfessorDTO dto = new DisciplinaProfessorDTO();
            dto.setDisciplinaId(d.id);
            dto.setDisciplinaNome(d.nome);

            Set<Aluno> alunosMatriculados = mapDisciplinaAlunos.get(d.id);
            if (alunosMatriculados == null) {
                dto.setAlunos(Collections.emptyList());
            } else {
                // Converter cada Aluno -> AlunoDTO
                List<AlunoDTO> alunoDTOs = alunosMatriculados.stream()
                        .map(a -> new AlunoDTO(a.id, a.nome))
                        .collect(Collectors.toList());
                dto.setAlunos(alunoDTOs);
            }

            result.add(dto);
        }

        return result;
    }
}
