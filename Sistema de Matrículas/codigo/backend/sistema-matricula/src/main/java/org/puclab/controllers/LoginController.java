package org.puclab.controllers;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.puclab.models.dtos.UsuarioDTO;
import org.puclab.services.UsuarioService;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginController {

    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @POST
    @Transactional
    public Response login(UsuarioDTO usuarioDTO) {
        return Response.ok().entity(usuarioService.login(usuarioDTO)).build();
    }

}
