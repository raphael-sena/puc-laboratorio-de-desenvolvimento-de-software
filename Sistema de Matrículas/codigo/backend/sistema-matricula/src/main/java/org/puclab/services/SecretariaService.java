package org.puclab.services;

import jakarta.enterprise.context.ApplicationScoped;
import org.puclab.models.Usuario;
import org.puclab.models.dtos.UsuarioDTO;

@ApplicationScoped
public class SecretariaService {

    public Usuario criarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = UsuarioFactory.criarUsuario(usuarioDTO);
        usuario.persist();
        return usuario;
    }

}
