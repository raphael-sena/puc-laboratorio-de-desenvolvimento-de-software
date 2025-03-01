package org.puclab.controllers;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.puclab.models.dtos.UsuarioDTO;
import org.puclab.services.SecretariaService;
import org.puclab.services.UsuarioService;

@Path("/usuario")

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioController {

    private final UsuarioService usuarioService;

    private final SecretariaService secretariaService;

    public UsuarioController(UsuarioService usuarioService, SecretariaService secretariaService) {
        this.usuarioService = usuarioService;
        this.secretariaService = secretariaService;
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        var usuario = usuarioService.findById(id);
        return Response.ok().entity(usuario).build();
    }

    @GET
    public Response findAll(@QueryParam("page") @DefaultValue("0") Integer page,
                            @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
        var usuarios = usuarioService.findAll(page, pageSize);
        return Response.ok().entity(usuarios).build();
    }

    @GET
    @Path("/search")
    public Response searchUsuarios(@QueryParam("query") String query,
                                   @QueryParam("page") @DefaultValue("0") Integer page,
                                   @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
        var usuarios = usuarioService.searchUsuarios(query, page, pageSize);
        return Response.ok().entity(usuarios).build();
    }

    @POST
    @Transactional
    public Response criarUsuario(UsuarioDTO usuarioDTO) {
        return Response.ok().entity(usuarioService.criarUsuario(usuarioDTO)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizarUsuario(@PathParam("id") Long id, UsuarioDTO usuarioDTO) {
        return Response.ok().entity(usuarioService.atualizarUsuario(id, usuarioDTO)).build();
    }

    @GET
    @Path("/{id}/tipo")
    public Response getTipoUsuario(@PathParam("id") Long id) {
        return Response.ok().entity(usuarioService.getTipoUsuario(id)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletarUsuario(@PathParam("id") Long id) {
        usuarioService.deletarUsuario(id);
        return Response.noContent().build();
    }
}
