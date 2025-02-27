package org.puclab.services;

import jakarta.enterprise.context.ApplicationScoped;
import org.puclab.exceptions.ObjectNotFoundException;
import org.puclab.models.Curriculo;
import org.puclab.models.Curso;
import org.puclab.models.dtos.CurriculoDTO;
import org.puclab.models.dtos.CursoDTO;

import java.util.List;

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

        CurriculoDTO curriculoDTO = new CurriculoDTO();
        curriculoDTO.setId(curriculo.getId());
        curriculoDTO.setNome(curriculo.getNome());
        curriculoDTO.setDisciplinas(curriculo.getDisciplinas());
        return curriculoDTO;
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
        curriculo.setDisciplinas(curriculoDTO.getDisciplinas());
        curriculo.setNome(curriculoDTO.getNome());
        curriculo.persist();
        curriculoDTO.setId(curriculo.getId());
    }


}
