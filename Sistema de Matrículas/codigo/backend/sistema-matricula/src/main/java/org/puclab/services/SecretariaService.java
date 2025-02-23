package org.puclab.services;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.puclab.models.Usuario;
import org.puclab.models.dtos.UsuarioDTO;

import java.util.List;

@ApplicationScoped
public class SecretariaService {

    public List<PanacheEntityBase> findAll(Integer page, Integer pageSize) {
        return Usuario.findAll()
                .page(page, pageSize)
                .list();
    }

    public Usuario criarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = UsuarioFactory.criarUsuario(usuarioDTO);
        usuario.persist();
        return usuario;
    }
}
