package org.puclab.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_secretaria")
public class Secretaria extends Usuario {
    private Set<Usuario> usuarios;

    public Secretaria(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Secretaria(Long id, String nome, String senha, Set<Usuario> usuarios) {
        super(id, nome, senha);
        this.usuarios = usuarios;
    }

    public Secretaria() {
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    // TODO
    public Set<Aluno> getAlunos() {
        return new HashSet<>();
    }

    // TODO
    public Set<Aluno> getProfessores() {
        return new HashSet<>();
    }

    // TODO
    public void editarUsuario(Usuario usuario) {
    }

//    // TODO
//    public Curriculo gerarCurriculo(Set<Disciplina> disciplinas) {
//        return new Curriculo();
//    }

    // TODO
    public void criarUsuario(Usuario usuario) {
    }
}
