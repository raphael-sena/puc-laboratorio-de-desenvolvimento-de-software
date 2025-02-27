package org.puclab.services;

import jakarta.enterprise.context.ApplicationScoped;
import org.puclab.models.Aluno;
import org.puclab.models.Professor;
import org.puclab.models.Secretaria;
import org.puclab.models.Usuario;
import org.puclab.models.dtos.UsuarioDTO;

@ApplicationScoped
public class UsuarioFactory {

    public static Usuario criarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = null;
        switch (usuarioDTO.getTipo().toUpperCase()) {
            case "ALUNO" -> usuario = new Aluno(usuarioDTO.getNome(), usuarioDTO.getSenha());
            case "PROFESSOR" -> usuario = new Professor(usuarioDTO.getNome(), usuarioDTO.getSenha());
            case "SECRETARIA" -> usuario = new Secretaria(usuarioDTO.getNome(), usuarioDTO.getSenha());
            default -> throw new IllegalArgumentException("Tipo de usuário inválido: " + usuarioDTO.getTipo());
        };
        return usuario;
    }
}
