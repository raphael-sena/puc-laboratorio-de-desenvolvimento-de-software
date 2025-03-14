// /services/professorService.ts

import axios from "axios";

export interface AlunoDTO {
  id: number;
  nome: string;
}

export interface DisciplinaProfessorDTO {
  disciplinaId: number;
  disciplinaNome: string;
  alunos: AlunoDTO[];
}

export async function getDisciplinasProfessor(professorId: number) {
  const response = await axios.get<DisciplinaProfessorDTO[]>(
    `http://localhost:8080/professores/${professorId}/disciplinas`
  );
  return response.data;
}
