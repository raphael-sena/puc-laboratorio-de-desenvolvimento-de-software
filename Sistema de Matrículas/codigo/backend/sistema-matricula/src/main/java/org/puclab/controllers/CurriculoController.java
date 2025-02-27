package org.puclab.controllers;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.puclab.models.dtos.CurriculoDTO;
import org.puclab.services.CurriculoService;

@Path("/curriculo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CurriculoController {

    private final CurriculoService curriculoService;

    public CurriculoController(CurriculoService curriculoService) {
        this.curriculoService = curriculoService;
    }

    @GET
    public Response findAll(@QueryParam("page") @DefaultValue("0") Integer page,
                            @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
        var curriculos = curriculoService.findAll(page, pageSize);
        return Response.ok().entity(curriculos).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok().entity(curriculoService.findById(id)).build();
    }

    @POST
    @Transactional
    public Response criarCurriculo(CurriculoDTO curriculoDTO) {
        return Response.ok().entity(curriculoService.criarCurriculo(curriculoDTO)).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Response atualizarCurriculo(CurriculoDTO curriculoDTO, @PathParam("id") Long id) {
        return Response.ok().entity(curriculoService.atualizarCurriculo(curriculoDTO, id)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletarCurso(@PathParam("id") Long id) {
        curriculoService.deletarCurriculo(id);
        return Response.noContent().build();
    }
}
