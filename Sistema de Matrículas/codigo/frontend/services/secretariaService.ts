import api from "../utils/axiosInstance";

export interface Disciplina {
    nome: string;
  }

interface PeriodoMatricula {
    dataInicio: number;
    dataFim: number;
}

export async function getPeriodoMatricula() {
    const response = await fetch('http://localhost:8080/secretaria/periodo-matricula');
    return response.json();
}

export const gerarPeriodoMatricula = async (periodoMatricula: PeriodoMatricula): Promise<PeriodoMatricula> => {
    const response = await api.post<PeriodoMatricula>('/secretaria/periodo-matricula', periodoMatricula);
    return response.data;
}

export const createDisciplina = async (userId: number, disciplina: Disciplina): Promise<Disciplina> => {
    const response = await api.post<Disciplina>(`/disciplinas/secretaria/${userId}`, disciplina);
    return response.data;

} 


export interface Curso {
    id: number;
    nome: string;
    creditos: number;
  }

  export interface CursoCreate {
    nome: string;
    creditos: number;
    }
  
  /**
   * Busca a lista de cursos com paginação (ou não).
   */
  export async function getCursos(page = 0, pageSize = 10): Promise<Curso[]> {
    const response = await api.get<Curso[]>(`/curso?page=${page}&pageSize=${pageSize}`);
    return response.data;
  }

  export async function createCurso(curso: CursoCreate): Promise<Curso> {
    const response = await api.post<Curso>("/curso", curso);
    return response.data;
  }