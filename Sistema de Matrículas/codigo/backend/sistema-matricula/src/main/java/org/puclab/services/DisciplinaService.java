package org.puclab.services;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.puclab.models.Disciplina;
import org.puclab.models.Secretaria;
import org.puclab.models.Usuario;
import org.puclab.models.dtos.DisciplinaDTO;
import org.puclab.models.enums.StatusDisciplina;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class DisciplinaService {


    private final SecretariaService secretariaService;

    public DisciplinaService(SecretariaService secretariaService) {
        this.secretariaService = secretariaService;
    }

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

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void verificarDisciplinasComMenosTresAlunos() {
        LocalDate hoje = LocalDate.now();
        LocalDate dataFim = secretariaService.obterPeriodoMatricula()[1];
        if (hoje.isAfter(dataFim)) {
            List<Disciplina> disciplinas = Disciplina.findAll().list();
            disciplinas.forEach(Disciplina::verificarStatusDisciplina);
        }
    }

}
