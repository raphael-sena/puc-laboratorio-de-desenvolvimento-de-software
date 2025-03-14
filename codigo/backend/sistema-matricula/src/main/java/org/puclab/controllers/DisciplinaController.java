package org.puclab.controllers;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.puclab.models.Disciplina;
import org.puclab.models.dtos.DisciplinaDTO;
import org.puclab.services.DisciplinaService;

@Path("/disciplinas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DisciplinaController {

    @Inject
    DisciplinaService disciplinaService;

    @GET
    public Response findAll() {
        return Response.ok().entity(disciplinaService.findAll()).build();
    }

    @GET
    @Path("/obrigatorias")
    public Response findAllObrigatorias() {
        return Response.ok(disciplinaService.findObrigatorias()).build();
    }

    @GET
    @Path("/optativas")
    public Response findAllOptativas() {
        return Response.ok(disciplinaService.findOptativas()).build();
    }

    @GET
    @Path("/{disciplinaId}")
    public Response findById(@PathParam("disciplinaId") long disciplinaId) {
        return Response.ok().entity(disciplinaService.findById(disciplinaId)).build();
    }

    @POST
    @Path("/secretaria/{usuarioId}")
    @Transactional
    public Response criarDisciplina(@PathParam("usuarioId") long usuarioId, DisciplinaDTO disciplinaDTO) {
        try {
            Disciplina novaDisciplina = disciplinaService.criarDisciplina(usuarioId, disciplinaDTO);
            return Response.status(Response.Status.CREATED).entity(novaDisciplina).build();
        } catch (RuntimeException ex) {
            return Response.status(Response.Status.FORBIDDEN).entity(ex.getMessage()).build();
        }
    }

    @PUT
    @Path("/secretaria/{usuarioId}/{disciplinaId}")
    @Transactional
    public Response atualizarDisciplina(@PathParam("usuarioId") long usuarioId, @PathParam("disciplinaId") long disciplinaId, DisciplinaDTO disciplinaDTO) {
        try {
            Disciplina disciplinaAtualizada = disciplinaService.atualizarDisciplina(usuarioId, disciplinaId, disciplinaDTO);
            return Response.status(Response.Status.OK).entity(disciplinaAtualizada).build();
        } catch (RuntimeException ex) {
            return Response.status(Response.Status.FORBIDDEN).entity(ex.getMessage()).build();
        }
    }

    @DELETE
    @Path("/secretaria/{usuarioId}/{disciplinaId}")
    @Transactional
    public Response deletarDisciplina(@PathParam("usuarioId") long usuarioId, @PathParam("disciplinaId") long disciplinaId) {
        try {
            return Response.noContent().entity(disciplinaService.deletarDisciplina(usuarioId, disciplinaId)).build();
        } catch (RuntimeException ex) {
            return Response.status(Response.Status.FORBIDDEN).entity(ex.getMessage()).build();
        }
    }

    @PUT
    @Path("/{disciplinaId}/professor/{usuarioId}")
    @Transactional
    public Response associarProfessor(@PathParam("usuarioId") long usuarioId, @PathParam("disciplinaId") long disciplinaId) {
        try {
            Disciplina disciplinaAtualizada = disciplinaService.associarProfessor(usuarioId, disciplinaId);
            return Response.status(Response.Status.OK).entity(disciplinaAtualizada).build();
        } catch (RuntimeException ex) {
            return Response.status(Response.Status.FORBIDDEN).entity(ex.getMessage()).build();
        }
    }

    @PUT
    @Path("/{disciplinaId}/professor")
    @Transactional
    public Response desassociarProfessor(@PathParam("disciplinaId") long disciplinaId) {
        try {
            Disciplina disciplinaAtualizada = disciplinaService.desassociarProfessor(disciplinaId);
            return Response.status(Response.Status.OK).entity(disciplinaAtualizada).build();
        } catch (RuntimeException ex) {
            return Response.status(Response.Status.FORBIDDEN).entity(ex.getMessage()).build();
        }
    }
}
