package org.puclab.services;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import org.puclab.models.Aluno;
import org.puclab.models.Professor;
import org.puclab.models.Secretaria;
import org.puclab.models.Usuario;
import org.puclab.models.dtos.DisciplinaDTO;
import org.puclab.models.dtos.LoginDTO;
import org.puclab.models.dtos.ProfessorResponseDTO;
import org.puclab.models.dtos.UsuarioDTO;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UsuarioService {

    public Object findById(Long id) {
        Usuario usuario = Usuario.findById(id);
        if (usuario instanceof Aluno) {
            return Aluno.findById(id);
        } else if (usuario instanceof Secretaria) {
            return Secretaria.findById(id);
        } else if (usuario instanceof Professor) {
            ProfessorResponseDTO professorResponseDTO = new ProfessorResponseDTO();

            List<DisciplinaDTO> disciplinaDTOS = ((Professor) usuario).getDisciplinas()
                    .stream()
                    .map(disciplina -> new DisciplinaDTO(disciplina.id, disciplina.nome))
                    .toList();

            professorResponseDTO.setId(id);
            professorResponseDTO.setNome(usuario.getNome());
            professorResponseDTO.setDisciplinas(disciplinaDTOS);
            return professorResponseDTO;
        } else {
            return Usuario.findById(id);
        }
    }

    public List<PanacheEntityBase> findAll(Integer page, Integer pageSize) {
        return Usuario.findAll()
                .page(page, pageSize)
                .list();
    }

    public List<Usuario> searchUsuarios(String query, int page, int pageSize) {
        PanacheQuery<Usuario> usuarioQuery;

        // Verifica se a query é um número válido para buscar pelo ID exato
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
        return usuario.getClass().getSimpleName().toUpperCase();
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
