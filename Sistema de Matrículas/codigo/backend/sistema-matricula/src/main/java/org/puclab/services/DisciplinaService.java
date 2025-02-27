package org.puclab.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.puclab.models.Disciplina;
import org.puclab.models.Secretaria;
import org.puclab.models.Usuario;
import org.puclab.models.dtos.DisciplinaDTO;
import org.puclab.models.enums.StatusDisciplina;

@ApplicationScoped
public class DisciplinaService {

    @Transactional
    public Disciplina criarDisciplina(long usuarioId, DisciplinaDTO disciplinaDTO) {

        Usuario usuario = Usuario.findById(usuarioId);
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado");
        }


        if (!(usuario instanceof Secretaria)) {
            throw new RuntimeException("Apenas usuários do tipo secretaria podem criar disciplinas.");
        }

        Disciplina disciplina = new Disciplina();
        disciplina.setNome(disciplinaDTO.getNome());
        disciplina.setStatus(StatusDisciplina.ATIVA);
        disciplina.persist();

        return disciplina;
    }
}
