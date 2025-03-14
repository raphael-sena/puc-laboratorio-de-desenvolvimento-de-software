"use client";

import { useEffect, useState } from "react";
import MatriculaComponent from "../components/MatriculaComponent";
import { getPeriodoMatricula } from "../../services/secretariaService";
import { AlunoResponseDTO } from "../../interfaces/AlunoResponseDTO";
import Alert from "../components/Alert";
import axios from "axios";

// Importe o componente da tabela
import DisciplinasMatriculadasTable from "../components/DisciplinasMatriculadasTable";
// Importe a função de desmatricular
import { desmatricularDisciplina } from "../../services/matriculaService";

export default function Aluno() {
  interface Periodo {
    dataInicio: string;
    dataFim: string;
  }

  const [alunoId, setAlunoId] = useState<number | null>(null);
  const [periodo, setPeriodo] = useState<Periodo | null>(null);
  const [aluno, setAluno] = useState<AlunoResponseDTO | null>(null);

  async function fetchAluno(alunoId: number) {
    const response = await axios.get(`http://localhost:8080/usuario/${alunoId}`);
    return response.data;
  }

  // Lê o token e define o alunoId
  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      const parsedId = parseInt(token, 10);
      if (!isNaN(parsedId)) {
        setAlunoId(parsedId);
      }
    }
  }, []);

  // Busca dados do aluno quando 'alunoId' mudar
  useEffect(() => {
    async function loadAluno() {
      if (alunoId !== null) {
        try {
          const alunoData = await fetchAluno(alunoId);
          setAluno(alunoData);
        } catch (error) {
          console.error("Erro ao buscar dados do aluno:", error);
        }
      }
    }
    loadAluno();
  }, [alunoId]);

  // Buscar período de matrícula
  useEffect(() => {
    async function fetchPeriodo() {
      try {
        const data = await getPeriodoMatricula();
        setPeriodo(data);
      } catch (error) {
        console.error("Erro ao buscar período de matrícula:", error);
      }
    }
    fetchPeriodo();
  }, []);

  // Callback para remover disciplina
  async function handleDesmatricular(matriculaId: number, disciplinaId: number) {
    try {
      const confirmar = confirm("Tem certeza que deseja desmatricular desta disciplina?");
      if (!confirmar) return;

      await desmatricularDisciplina(matriculaId, disciplinaId);

      alert("Disciplina removida com sucesso!");
      // Recarrega os dados do aluno para atualizar a tela
      if (alunoId) {
        const alunoData = await fetchAluno(alunoId);
        setAluno(alunoData);
      }
    } catch (error) {
      console.error("Erro ao desmatricular:", error);
      alert("Não foi possível remover a disciplina.");
    }
  }

  return (
    <div className="grid grid-rows-[20px_1fr_20px] items-center justify-items-center min-h-screen p-8 gap-16 sm:p-20 font-[family-name:var(--font-geist-sans)]">
      <main className="flex flex-col row-start-2 items-center sm:items-start gap-4">
        <h1 className="text-3xl mb-4 font-bold text-center">Aluno</h1>

        {/* Dados do Aluno */}
        <h2 className="text-xl mb-2 font-semibold">Dados do Aluno</h2>
        {aluno ? (
          <div>
            <p>Nome: {aluno.nome}</p>
          </div>
        ) : (
          <p>Carregando dados do aluno...</p>
        )}

        {/* Tabela Disciplinas Matriculadas */}
        {aluno && aluno.matriculas && (
          <DisciplinasMatriculadasTable
            matriculas={aluno.matriculas}
            onDesmatricular={handleDesmatricular}
          />
        )}

        {/* Alerta informando limite de disciplinas */}
        <Alert
          message="Você só pode se matricular em 4 disciplinas obrigatórias e 2 optativas."
          variant="warning"
        />

        {/* Período de Matrícula */}
        <div className="flex gap-4 w-full mb-2">
          <div className="flex flex-col gap-4 w-full">
            <div className="p-4 rounded-xl ring w-full">
              {periodo ? (
                <div>
                  <h2 className="text-xl mb-2 font-semibold">
                    Período de Matrícula
                  </h2>
                  <p>
                    Data Início:{" "}
                    {new Date(periodo.dataInicio).toLocaleDateString("pt-BR")}
                  </p>
                  <p>
                    Data Fim:{" "}
                    {new Date(periodo.dataFim).toLocaleDateString("pt-BR")}
                  </p>
                  <p>
                    Dias Restantes:{" "}
                    {Math.ceil(
                      (new Date(periodo.dataFim).getTime() -
                        new Date().getTime()) / 86400000
                    )}
                  </p>
                </div>
              ) : (
                <p>Carregando período de matrícula...</p>
              )}
            </div>
          </div>
        </div>

        {/* Componente de Matrícula */}
        {alunoId ? (
          <MatriculaComponent alunoId={alunoId} />
        ) : (
          <p>Carregando dados do aluno...</p>
        )}
      </main>
    </div>
  );
}
