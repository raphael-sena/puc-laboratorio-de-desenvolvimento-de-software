package org.puclab.controllers;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.puclab.models.dtos.PeriodoMatriculaDTO;
import org.puclab.services.SecretariaService;

import java.time.LocalDate;
import java.util.Map;

@Path("/secretaria")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SecretariaController {

    private final SecretariaService secretariaService;

    public SecretariaController(SecretariaService secretariaService) {
        this.secretariaService = secretariaService;
    }

    @POST
    @Transactional
    @Path("/periodo-matricula")
    public Response gerarPeriodoMatricula(PeriodoMatriculaDTO dto) {
        try {
            LocalDate[] periodo = secretariaService.gerarPeriodoMatricula(dto.dataInicio, dto.dataFim);
            return Response.ok().entity(Map.of(
                    "dataInicio", periodo[0],
                    "dataFim", periodo[1],
                    "mensagem", "Período de matrícula configurado com sucesso"
            )).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("erro", e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/periodo-matricula")
    public Response obterPeriodoMatricula() {
        LocalDate[] periodo = secretariaService.obterPeriodoMatricula();
        if (periodo == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("mensagem", "Período de matrícula não configurado"))
                    .build();
        }

        return Response.ok().entity(Map.of(
                "dataInicio", periodo[0],
                "dataFim", periodo[1]
        )).build();
    }
}
