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
        String tipo = usuarioDTO.getTipo().toUpperCase();
        switch (tipo) {
            case "ALUNO" -> usuario = new Aluno(usuarioDTO.getNome(), usuarioDTO.getSenha(), tipo);
            case "PROFESSOR" -> usuario = new Professor(usuarioDTO.getNome(), usuarioDTO.getSenha(), tipo);
            case "SECRETARIA" -> usuario = new Secretaria(usuarioDTO.getNome(), usuarioDTO.getSenha(), tipo);
            default -> throw new IllegalArgumentException("Tipo de usuário inválido: " + usuarioDTO.getTipo());
        };
        return usuario;
    }
}
