package org.puclab.services;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.puclab.exceptions.ObjectNotFoundException;
import org.puclab.models.Curriculo;
import org.puclab.models.Curso;
import org.puclab.models.Disciplina;
import org.puclab.models.dtos.CurriculoDTO;
import org.puclab.models.dtos.CursoDTO;
import org.puclab.models.dtos.DisciplinaDTO;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class CursoService {

    public List<CursoDTO> findAll(Integer page, Integer pageSize) {
        List<Curso> cursos = Curso.findAll()
                .page(page, pageSize)
                .list();

        // Converter cada Curso em CursoDTO
        return cursos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private CursoDTO toDTO(Curso entity) {
        // Ajustar conforme seus campos
        return new CursoDTO(
                entity.getId(),
                entity.getNome(),
                entity.getCreditos()
        );
    }


    public CursoDTO findById(Long id) {
        Curso curso = (Curso) Curso
                .findByIdOptional(id)
                .orElseThrow(
                        () -> new ObjectNotFoundException("Curso não encontrado")
                );

        CursoDTO cursoDTO = new CursoDTO();
        cursoDTO.setId(curso.getId());
        cursoDTO.setNome(curso.getNome());
        cursoDTO.setCreditos(curso.getCreditos());
        return cursoDTO;
    }

    public CursoDTO criarCurso(CursoDTO cursoDTO) {
        Curso novoCurso = new Curso();
        fromDTO(cursoDTO, novoCurso);
        return cursoDTO;
    }


    public CursoDTO atualizarCurso(CursoDTO cursoDTO, Long id) {
        Curso curso = Curso.findById(id);
        fromDTO(cursoDTO, curso);
        return cursoDTO;
    }

    public void deletarCurso(Long id) {
        Curso curso = Curso.findById(id);
        curso.delete();
    }

    private static void fromDTO(CursoDTO cursoDTO, Curso curso) {
        curso.setCreditos(cursoDTO.getCreditos());
        curso.setNome(cursoDTO.getNome());
        curso.persist();
        cursoDTO.setId(curso.getId());
    }

    public CurriculoDTO associarCurriculoEmCurso(Long curriculoId, Long id) {

        Curso curso = Curso.findById(id);
        Curriculo curriculo = Curriculo.findById(curriculoId);

        CurriculoDTO curriculoDTO = new CurriculoDTO();

        curso.getCurriculos().add(curriculo);
        curso.persist();

        curriculoDTO.setId(curriculoId);
        curriculoDTO.setDisciplinas(curriculo.getDisciplinas().stream()
                .map(Disciplina::getId)
                .collect(Collectors.toList()));

        curriculoDTO.setNome(curriculo.getNome());

        return curriculoDTO;
    }

    public Set<Curriculo> obterCurriculos(Long id) {
        Curso curso = Curso.findById(id);
        return curso.getCurriculos();
    }

    public void desassociarCurriculoEmCurso(Long curriculoId, Long id) {
        Curso curso = Curso.findById(id);
        Curriculo curriculo = Curriculo.findById(curriculoId);

        curso.getCurriculos().remove(curriculo);
    }
}
