package org.puclab.services;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import org.puclab.models.Aluno;
import org.puclab.models.Professor;
import org.puclab.models.Secretaria;
import org.puclab.models.Usuario;
import org.puclab.models.dtos.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class UsuarioService {

    public Object findById(Long id) {
        Usuario usuario = Usuario.findById(id);

        if (usuario instanceof Aluno) {
            AlunoResponseDTO alunoResponseDTO = new AlunoResponseDTO();

            alunoResponseDTO.setId(id);
            alunoResponseDTO.setNome(usuario.getNome());

            alunoResponseDTO.setMatriculas(((Aluno) usuario).getMatriculas()
                    .stream()
                    .map(matricula -> {
                        Set<DisciplinaDTO> disciplinaDTOS = new HashSet<>();

                        matricula.disciplinasObrigatorias
                                .forEach(disciplina -> disciplinaDTOS.add(new DisciplinaDTO(disciplina.id, disciplina.nome, disciplina.tipo.name())));

                        matricula.disciplinasOptativas
                                .forEach(disciplina -> disciplinaDTOS.add(new DisciplinaDTO(disciplina.id, disciplina.nome, disciplina.tipo.name())));

                        return new MatriculaDTO(matricula.id, matricula.dataMatricula, matricula.aluno.id, disciplinaDTOS, matricula.statusMatricula);
                    })
                    .collect(Collectors.toSet()));

            return alunoResponseDTO;
        }


        else if (usuario instanceof Secretaria) {
            return Secretaria.findById(id);
        }


        else if (usuario instanceof Professor) {
            ProfessorResponseDTO professorResponseDTO = new ProfessorResponseDTO();

            List<DisciplinaDTO> disciplinaDTOS = ((Professor) usuario).getDisciplinas()
                    .stream()
                    .map(disciplina -> new DisciplinaDTO(disciplina.id, disciplina.nome, disciplina.tipo.name()))
                    .toList();

            professorResponseDTO.setId(id);
            professorResponseDTO.setNome(usuario.getNome());
            professorResponseDTO.setDisciplinas(disciplinaDTOS);
            return professorResponseDTO;
        } else {
            return Usuario.findById(id);
        }
    }



    public List<UsuarioDTO> findAll(Integer page, Integer pageSize) {
        // 1) Buscar entidades do tipo Usuario
        List<Usuario> usuarios = Usuario.findAll()
                .page(page, pageSize)
                .list();

        // 2) Mapear cada Usuario para UsuarioDTO
        // Supondo que você tenha um construtor em UsuarioDTO que aceite Usuario:
        return usuarios.stream()
                .map(usuario -> new UsuarioDTO(usuario))
                .collect(Collectors.toList());
    }


    public List<Usuario> searchUsuarios(String query, int page, int pageSize) {
        PanacheQuery<Usuario> usuarioQuery;

        if (query.matches("\\d+")) {
            usuarioQuery = Usuario.find("id = ?1 OR LOWER(nome) LIKE ?2",
                    Integer.parseInt(query), "%" + query.toLowerCase() + "%");
        } else {
            usuarioQuery = Usuario.find("LOWER(nome) LIKE ?1", "%" + query.toLowerCase() + "%");
        }

        return usuarioQuery.page(page, pageSize)
                .list()
                .stream()
                .map(usuario -> (Usuario) Usuario.findById(usuario.id))
                .collect(Collectors.toList());
    }

    public Usuario criarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = UsuarioFactory.criarUsuario(usuarioDTO);
        usuario.persist();
        return usuario;
    }


    public boolean login(LoginDTO loginDTO) {
        Usuario usuario = Usuario.findById(loginDTO.getId());
        return usuario.senha.equals(loginDTO.getSenha());
    }

    public String getTipoUsuario(long id) {
        Usuario usuario = Usuario.findById(id);
        String tipo;

        if (usuario.tipo == "USUARIO") {
            throw new RuntimeException("Houve um erro de relacionamento entre entidades ao tentar identificar o tipo do usuário.");
        } if (usuario.tipo.equalsIgnoreCase("ALUNO")) {
            tipo = "ALUNO";
        } else if (usuario.tipo.equalsIgnoreCase("PROFESSOR")) {
            tipo = "PROFESSOR";
        } else if (usuario.tipo.equalsIgnoreCase("SECRETARIA")) {
            tipo = "SECRETARIA";
        } else {
            throw new RuntimeException("Houve um erro ao tentar identificar o tipo do usuário.");
        }
        return tipo;
    }

    public UsuarioDTO atualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuario = Usuario.findById(id);
        usuario.nome = usuarioDTO.getNome();
        usuario.senha = usuarioDTO.getSenha();
        usuario.tipo = usuarioDTO.getTipo();
        usuario.persist();
        usuarioDTO.setId(usuario.id);
        return usuarioDTO;
    }

    public void deletarUsuario(Long id) {
        Usuario usuario = Usuario.findById(id);
        usuario.delete();
    }

    public Integer getTotalAlunos() {
        return (int) Usuario.findAll()
                .stream()
                .filter(u -> u.getClass().getSimpleName().equalsIgnoreCase("ALUNO"))
                .count();
    }

    public Integer getTotalProfessores() {
        return (int) Usuario.findAll()
                .stream()
                .filter(u -> u.getClass().getSimpleName().equalsIgnoreCase("PROFESSOR"))
                .count();
    }

    public Integer getTotalSecretaria() {
        return (int) Usuario.findAll()
                .stream()
                .filter(u -> u.getClass().getSimpleName().equalsIgnoreCase("SECRETARIA"))
                .count();
    }

    public Integer getTotalUsuarios() {
        return (int) Usuario.findAll()
                .stream()
                .count();
    }
}
