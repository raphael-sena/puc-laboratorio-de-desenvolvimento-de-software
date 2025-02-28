import api from "../utils/axiosInstance";

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