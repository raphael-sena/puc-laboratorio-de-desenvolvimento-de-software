package org.puclab.controllers;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.puclab.exceptions.BusinessException;
import org.puclab.models.Matricula;
import org.puclab.models.dtos.MatriculaRequestDTO;
import org.puclab.services.MatriculaService;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Path("/matriculas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MatriculaController {

    private static final Logger LOGGER = Logger.getLogger(MatriculaController.class.getName());

    @Inject
    MatriculaService matriculaService;

    @POST
    @Transactional
    public Response matricularAluno(MatriculaRequestDTO request) {
        try {
            // Validações básicas
            if (request.alunoId == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("erro", "ID do aluno é obrigatório"))
                        .build();
            }

            if (request.disciplinasObrigatorias == null || request.disciplinasObrigatorias.size() > 4) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("erro", "Deve informar no máximo 4 disciplinas obrigatórias"))
                        .build();
            }

            if (request.disciplinasOptativas == null || request.disciplinasOptativas.size() > 2) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("erro", "Deve informar no máximo 2 disciplinas optativas"))
                        .build();
            }

            // Realiza a matrícula
            var matricula = matriculaService.matricularAluno(
                    request.alunoId,
                    request.disciplinasObrigatorias,
                    request.disciplinasOptativas
            );

            return Response.status(Response.Status.CREATED)
                    .entity(Map.of(
                            "id", matricula.getId(),
                            "dataMatricula", matricula.getDataMatricula(),
                            "quantidadeObrigatorias", matricula.getDisciplinasObrigatorias().size(),
                            "quantidadeOptativas", matricula.getDisciplinasOptativas().size(),
                            "mensagem", "Matrícula realizada com sucesso"
                    ))
                    .build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("erro", e.getMessage()))
                    .build();
        } catch (Exception e) {
            LOGGER.severe("Erro ao processar matrícula: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("erro", "Erro interno ao processar a matrícula"))
                    .build();
        }
    }

    @DELETE
    @Path("/{matriculaId}/disciplina/{disciplinaId}")
    @Transactional
    public Response desmatricularAluno(
            @PathParam("matriculaId") Long matriculaId,
            @PathParam("disciplinaId") Long disciplinaId
    ) {
        try {
            Matricula matricula = matriculaService.desmatricularAlunoEmDisciplina(matriculaId, disciplinaId);

            // Monta um JSON de resposta
            return Response.ok(
                    Map.of(
                            "id", matricula.getId(),
                            "status", matricula.getStatusMatricula(),
                            "quantidadeObrigatorias", matricula.getDisciplinasObrigatorias().size(),
                            "quantidadeOptativas", matricula.getDisciplinasOptativas().size(),
                            "mensagem", "Disciplina removida com sucesso"
                    )
            ).build();

        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("erro", e.getMessage()))
                    .build();
        } catch (Exception e) {
            LOGGER.severe("Erro ao processar desmatrícula: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("erro", "Erro interno ao processar a desmatrícula"))
                    .build();
        }
    }

}