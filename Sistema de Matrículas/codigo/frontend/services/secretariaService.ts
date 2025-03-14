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