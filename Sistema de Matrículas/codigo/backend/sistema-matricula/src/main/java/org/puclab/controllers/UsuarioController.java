package org.puclab.controllers;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.puclab.models.dtos.UsuarioDTO;
import org.puclab.services.SecretariaService;

@Path("/usuario")

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioController {

    private final SecretariaService secretariaService;

    public UsuarioController(SecretariaService secretariaService) {
        this.secretariaService = secretariaService;
    }

    @POST
    @Transactional
    public Response criarUsuario(UsuarioDTO usuarioDTO) {
        return Response.ok().entity(secretariaService.criarUsuario(usuarioDTO)).build();
    }
}
