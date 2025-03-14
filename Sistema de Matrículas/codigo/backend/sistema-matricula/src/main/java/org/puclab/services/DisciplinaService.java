package org.puclab.services;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.puclab.models.Disciplina;
import org.puclab.models.Professor;
import org.puclab.models.Secretaria;
import org.puclab.models.Usuario;
import org.puclab.models.dtos.DisciplinaDTO;
import org.puclab.models.enums.StatusDisciplina;
import org.puclab.models.enums.TipoDisciplina;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class DisciplinaService {

    private final SecretariaService secretariaService;

    public DisciplinaService(SecretariaService secretariaService) {
        this.secretariaService = secretariaService;
    }

    public List<DisciplinaDTO> findAll() {
        return Disciplina.findAll().list().stream().map(d -> {
            DisciplinaDTO dto = new DisciplinaDTO();
            Disciplina disciplina = (Disciplina) d;
            dto.setId(disciplina.getId());
            dto.setNome(disciplina.getNome());
            dto.setTipo(disciplina.getTipo().name());
            return dto;
        }).collect(Collectors.toList());
    }


    public List<DisciplinaDTO> findObrigatorias() {
        // Filtra as disciplinas cujo tipo seja OBRIGATORIA
        List<Disciplina> lista = Disciplina.list("tipo", TipoDisciplina.OBRIGATORIA);

        // Converte cada Disciplina para DisciplinaDTO
        return lista.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<DisciplinaDTO> findOptativas() {
        // Filtra as disciplinas cujo tipo seja OPTATIVA
        List<Disciplina> lista = Disciplina.list("tipo", TipoDisciplina.OPTATIVA);

        return lista.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converte uma entidade Disciplina em DisciplinaDTO.
     */
    private DisciplinaDTO toDTO(Disciplina disciplina) {
        DisciplinaDTO dto = new DisciplinaDTO();
        dto.setId(disciplina.getId());
        dto.setNome(disciplina.getNome());

        dto.setTipo(disciplina.getTipo().name());

        return dto;
    }


    @Transactional
    public Disciplina criarDisciplina(long usuarioId, DisciplinaDTO disciplinaDTO) {

        System.out.println("Criando disciplina");

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
        disciplina.setTipo(TipoDisciplina.valueOf(disciplinaDTO.getTipo()));
        disciplina.persist();

        return disciplina;
    }

    public Disciplina atualizarDisciplina(long usuarioId, long disciplinaId, DisciplinaDTO disciplinaDTO) {
        Usuario usuario = Usuario.findById(usuarioId);
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado");
        }

        if (!(usuario instanceof Secretaria)) {
            throw new RuntimeException("Apenas usuários do tipo secretaria podem atualizar disciplinas.");
        }

        Disciplina disciplina = Disciplina.findById(disciplinaId);
        if (disciplina == null) {
            throw new RuntimeException("Disciplina não encontrada");
        }

        disciplina.setNome(disciplinaDTO.getNome());
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


    public Disciplina associarProfessor(long usuarioId, long disciplinaId) {
        Professor usuario = Professor.findById(usuarioId);
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado");
        }

        Disciplina disciplina = Disciplina.findById(disciplinaId);
        if (disciplina == null) {
            throw new RuntimeException("Disciplina não encontrada");
        }

        if (disciplina.getProfessor() != null) {
            throw new RuntimeException("Disciplina já possui um professor associado");
        }

        disciplina.setProfessor(usuario);
        disciplina.persist();

        return disciplina;
    }

    public Disciplina desassociarProfessor(long disciplinaId) {
        Disciplina disciplina = Disciplina.findById(disciplinaId);
        if (disciplina == null) {
            throw new RuntimeException("Disciplina não encontrada");
        }

        disciplina.setProfessor(null);
        disciplina.persist();

        return disciplina;
    }

    public Object deletarDisciplina(long usuarioId, long disciplinaId) {
        Usuario usuario = Usuario.findById(usuarioId);
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado");
        }

        if (!(usuario instanceof Secretaria)) {
            throw new RuntimeException("Apenas usuários do tipo secretaria podem deletar disciplinas.");
        }

        Disciplina disciplina = Disciplina.findById(disciplinaId);
        if (disciplina == null) {
            throw new RuntimeException("Disciplina não encontrada");
        }

        disciplina.delete();
        return "Disciplina deletada com sucesso";
    }

    public DisciplinaDTO findById(long disciplinaId) {
        Disciplina disciplina = Disciplina.findById(disciplinaId);
        if (disciplina == null) {
            throw new RuntimeException("Disciplina não encontrada");
        }

        return new DisciplinaDTO(disciplinaId, disciplina.getNome(), disciplina.getTipo().name());
    }
}
