package org.puclab.controllers;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.puclab.models.dtos.CursoDTO;
import org.puclab.services.CursoService;

import java.util.List;

@Path("/curso")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GET
    public Response findAll(@QueryParam("page") @DefaultValue("0") Integer page,
                            @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
        List<CursoDTO> cursos = cursoService.findAll(page, pageSize);
        return Response.ok(cursos).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok().entity(cursoService.findById(id)).build();
    }

    @POST
    @Transactional
    public Response criarCurso(CursoDTO cursoDTO) {
        return Response.ok().entity(cursoService.criarCurso(cursoDTO)).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Response atualizarCurso(CursoDTO cursoDTO, @PathParam("id") Long id) {
        return Response.ok().entity(cursoService.atualizarCurso(cursoDTO, id)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletarCurso(@PathParam("id") Long id) {
        cursoService.deletarCurso(id);
        return Response.noContent().build();
    }

    @POST
    @Transactional
    @Path("/{id}/curriculos/{curriculoId}")
    public Response associarCurriculoEmCurso(@PathParam("curriculoId") Long curriculoId, @PathParam("id") Long id) {
        return Response.ok().entity(cursoService.associarCurriculoEmCurso(curriculoId, id)).build();
    }

    @PUT
    @Transactional
    @Path("/{id}/curriculos/{curriculoId}")
    public Response desassociarCurriculoEmCurso(@PathParam("curriculoId") Long curriculoId, @PathParam("id") Long id) {
        cursoService.desassociarCurriculoEmCurso(curriculoId, id);
        return Response.noContent().build();
    }

    @GET
    @Transactional
    @Path("/{id}/curriculos")
    public Response obterCurriculos(@PathParam("id") Long id) {
        return Response.ok().entity(cursoService.obterCurriculos(id)).build();
    }
}
