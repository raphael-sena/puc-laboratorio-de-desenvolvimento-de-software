package org.puclab.services;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.puclab.models.Curso;
import org.puclab.models.dtos.CursoDTO;

import java.util.List;

@ApplicationScoped
public class CursoService {

    public List<PanacheEntityBase> findAll(Integer page, Integer pageSize) {
        return Curso.findAll()
                .page(page, pageSize)
                .list();
    }

    public CursoDTO criarCurso(CursoDTO cursoDTO) {
        Curso novoCurso = new Curso();
        novoCurso.setCreditos(cursoDTO.getCreditos());
        novoCurso.setNome(cursoDTO.getNome());
        novoCurso.persist();
        cursoDTO.setId(novoCurso.getId());
        return cursoDTO;
    }


}
