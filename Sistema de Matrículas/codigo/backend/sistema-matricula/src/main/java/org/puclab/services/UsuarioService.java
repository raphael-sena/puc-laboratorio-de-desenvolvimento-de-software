package org.puclab.services;

import jakarta.enterprise.context.ApplicationScoped;
import org.puclab.models.Usuario;
import org.puclab.models.dtos.UsuarioDTO;

@ApplicationScoped
public class UsuarioService {


    public boolean login(UsuarioDTO usuarioDTO) {
        Usuario usuario = Usuario.findById(usuarioDTO.getId());
        return usuario.senha.equals(usuarioDTO.getSenha());
    }

    public String getTipoUsuario(long id) {
        Usuario usuario = Usuario.findById(id);
        return usuario.getClass().getSimpleName().toUpperCase();
    }
}
