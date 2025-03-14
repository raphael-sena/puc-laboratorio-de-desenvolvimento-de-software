package org.puclab.services;

import jakarta.enterprise.context.ApplicationScoped;
import org.puclab.exceptions.ObjectNotFoundException;
import org.puclab.models.Curriculo;
import org.puclab.models.Curso;
import org.puclab.models.Disciplina;
import org.puclab.models.dtos.CurriculoDTO;
import org.puclab.models.dtos.CursoDTO;
import org.puclab.models.dtos.DisciplinaDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class CurriculoService {

    public List<Curriculo> findAll(Integer page, Integer pageSize) {
        return Curriculo.findAll()
                .page(page, pageSize)
                .list();
    }

    public CurriculoDTO findById(Long id) {
        Curriculo curriculo = (Curriculo) Curriculo
                .findByIdOptional(id)
                .orElseThrow(
                        () -> new ObjectNotFoundException("Currículo não encontrado!")
                );

        return fromCurriculoDTO(curriculo);
    }

    public CurriculoDTO criarCurriculo(CurriculoDTO curriculoDTO) {
        Curriculo novoCurriculo = new Curriculo();
        fromDTO(curriculoDTO, novoCurriculo);
        return curriculoDTO;
    }

    public CurriculoDTO atualizarCurriculo(CurriculoDTO curriculoDTO, Long id) {
        Curriculo curriculo = Curriculo.findById(id);
        fromDTO(curriculoDTO, curriculo);
        return curriculoDTO;
    }

    public void deletarCurriculo(Long id) {
        Curriculo curriculo = Curriculo.findById(id);
        curriculo.delete();
    }

    private static void fromDTO(CurriculoDTO curriculoDTO, Curriculo curriculo) {

        if (curriculoDTO.getDisciplinas() != null && !curriculoDTO.getDisciplinas().isEmpty()) {
            List<Disciplina> disciplinas = curriculoDTO.getDisciplinas()
                    .stream()
                    .map(d -> {
                        Disciplina disciplina = Disciplina.findById(d);
                        if (disciplina == null) {
                            throw new ObjectNotFoundException("Disciplina não encontrada!");
                        }
                        return disciplina;
                    })
                    .toList();

            curriculo.setDisciplinas(disciplinas);
        } else {
            curriculo.setDisciplinas(new ArrayList<>());
        }
        curriculo.setNome(curriculoDTO.getNome());
        curriculo.persist();
        curriculoDTO.setId(curriculo.getId());
    }

    public CurriculoDTO associarDisciplinas(long curriculoId, long disciplinaId) {
        Curriculo curriculo = Curriculo.findById(curriculoId);
        Disciplina disciplina = Disciplina.findById(disciplinaId);

        if (curriculo == null) {
            throw new ObjectNotFoundException("Currículo não encontrado!");
        }

        if (disciplina == null) {
            throw new ObjectNotFoundException("Disciplina não encontrada!");
        }

        if (curriculo.getDisciplinas().contains(disciplina)) {
            throw new RuntimeException("Disciplina já associada ao currículo!");
        }

        curriculo.getDisciplinas().add(disciplina);
        curriculo.persist();

        return fromCurriculoDTO(curriculo);

    }

    private CurriculoDTO fromCurriculoDTO(Curriculo curriculo) {
        CurriculoDTO curriculoDTO = new CurriculoDTO();
        curriculoDTO.setId(curriculo.getId());
        curriculoDTO.setNome(curriculo.getNome());
        curriculoDTO.setDisciplinas(curriculo.getDisciplinas()
                .stream()
                .map(Disciplina::getId)
                .collect(Collectors.toList()));

        return curriculoDTO;
    }

    public void desassociarDisciplinas(long curriculoId, long disciplinaId) {
        Curriculo curriculo = Curriculo.findById(curriculoId);
        Disciplina disciplina = Disciplina.findById(disciplinaId);

        if (curriculo == null) {
            throw new ObjectNotFoundException("Currículo não encontrado!");
        }

        if (disciplina == null) {
            throw new ObjectNotFoundException("Disciplina não encontrada!");
        }

        if (!curriculo.getDisciplinas().contains(disciplina)) {
            throw new RuntimeException("Disciplina não associada ao currículo!");
        }

        curriculo.getDisciplinas().remove(disciplina);
        curriculo.persist();
    }
}
