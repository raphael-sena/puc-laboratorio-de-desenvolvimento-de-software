package org.puclab.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.puclab.models.dtos.DisciplinaProfessorDTO;
import org.puclab.services.ProfessorService;

import java.util.List;

@Path("/professores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfessorController {

    @Inject
    ProfessorService professorService;

    @GET
    @Path("/{professorId}/disciplinas")
    public Response getDisciplinasEAlunos(@PathParam("professorId") Long professorId) {
        try {
            List<DisciplinaProfessorDTO> result = professorService.findDisciplinasEAlunos(professorId);
            return Response.ok(result).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar disciplinas do professor.")
                    .build();
        }
    }
}
