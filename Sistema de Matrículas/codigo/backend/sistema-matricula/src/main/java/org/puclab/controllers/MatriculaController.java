package org.puclab.controllers;


import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.puclab.models.Matricula;
import org.puclab.services.MatriculaService;

import java.util.List;

@Path("/matricula")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MatriculaController {

    private final MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    @POST
    @Path("matricular/aluno/{alunoId}/disciplinas")
    @Transactional
    public Response matricularAlunoEmDisciplinas(@PathParam("alunoId") long alunoId, List<Long> disciplinaIds) {
        try {
            Matricula matricula = matriculaService.matricularAlunoEmDisciplinas(alunoId, disciplinaIds);
            return Response.ok(matricula).build();
        } catch (RuntimeException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }

    @POST
    @Path("desmatricular/aluno/{alunoId}/disciplinas")
    @Transactional
    public Response desmatricularAlunoEmDisciplinas(@PathParam("alunoId") long alunoId, List<Long> disciplinaIds) {
        try {
            Matricula matricula = matriculaService.desmatricularAlunoEmDisciplinas(alunoId, disciplinaIds);
            return Response.ok(matricula).build();
        } catch (RuntimeException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }
}
