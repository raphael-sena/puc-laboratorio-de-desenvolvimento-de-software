"use client";

import { useEffect, useState } from "react";
import {
  getDisciplinasProfessor,
  DisciplinaProfessorDTO,
} from "@/services/professorService";

// Importações do shadcn/ui
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";

export default function Professor() {
  const [professorId, setProfessorId] = useState<number | null>(null);
  const [disciplinas, setDisciplinas] = useState<DisciplinaProfessorDTO[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  // Exemplo: lê o professorId do localStorage
  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      const parsedId = parseInt(token, 10);
      if (!isNaN(parsedId)) {
        setProfessorId(parsedId);
      }
    }
  }, []);

  // Busca as disciplinas sempre que professorId mudar
  useEffect(() => {
    async function fetchData() {
      if (professorId !== null) {
        try {
          setLoading(true);
          const data = await getDisciplinasProfessor(professorId);
          setDisciplinas(data);
        } catch (err) {
          setError("Erro ao buscar disciplinas do professor.");
          console.error(err);
        } finally {
          setLoading(false);
        }
      }
    }
    fetchData();
  }, [professorId]);

  if (loading) return <p>Carregando...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div className="grid grid-rows-[20px_1fr_20px] items-center justify-items-center min-h-screen p-8 gap-16 sm:p-20">
      <main className="flex flex-col row-start-2 items-center sm:items-start gap-4 w-full">
        <h1 className="text-3xl mb-4 font-bold text-center">Professor</h1>

        <div className="p-4 rounded-xl ring w-full">
          <h2 className="text-xl mb-4 font-semibold">Minhas Disciplinas</h2>

          <Table className="min-w-[700px]">
            <TableCaption>
              Disciplinas do professor e seus respectivos alunos matriculados.
            </TableCaption>
            <TableHeader>
              <TableRow>
                <TableHead className="w-[200px]">Disciplina</TableHead>
                <TableHead>Alunos Matriculados</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {disciplinas.length === 0 ? (
                // Se não houver disciplinas
                <TableRow>
                  <TableCell colSpan={2} className="text-center">
                    Nenhuma disciplina encontrada.
                  </TableCell>
                </TableRow>
              ) : (
                disciplinas.map((disc) => (
                  <TableRow key={disc.disciplinaId}>
                    <TableCell>
                      {disc.disciplinaNome} (ID: {disc.disciplinaId})
                    </TableCell>
                    <TableCell>
                      {disc.alunos && disc.alunos.length > 0 ? (
                        <ul className="list-disc pl-5">
                          {disc.alunos.map((aluno) => (
                            <li key={aluno.id}>
                              {aluno.nome} (ID: {aluno.id})
                            </li>
                          ))}
                        </ul>
                      ) : (
                        <p>Nenhum aluno matriculado</p>
                      )}
                    </TableCell>
                  </TableRow>
                ))
              )}
            </TableBody>
          </Table>
        </div>
      </main>
    </div>
  );
}
