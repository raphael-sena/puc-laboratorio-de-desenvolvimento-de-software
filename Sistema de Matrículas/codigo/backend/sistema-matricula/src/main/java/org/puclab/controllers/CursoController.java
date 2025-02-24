package org.puclab.controllers;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.puclab.models.dtos.CursoDTO;
import org.puclab.services.CursoService;

@Path("/curso")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @POST
    @Transactional
    public Response criarCurso(CursoDTO cursoDTO) {
        return Response.ok().entity(cursoService.criarCurso(cursoDTO)).build();
    }

    @GET
    public Response findAll(@QueryParam("page") @DefaultValue("0") Integer page,
                            @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
        var cursos = cursoService.findAll(page, pageSize);
        return Response.ok().entity(cursos).build();
    }
}
