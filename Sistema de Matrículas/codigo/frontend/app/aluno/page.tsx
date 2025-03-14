"use client";

import { useEffect, useState } from "react";
import MatriculaComponent from "../components/MatriculaComponent";
import { getPeriodoMatricula } from "../../services/secretariaService";
import { AlunoResponseDTO } from "../../interfaces/AlunoResponseDTO";
import Alert from "../components/Alert";
import axios from "axios";

export default function Aluno() {
    interface Periodo {
        dataInicio: string;
        dataFim: string;
    }

    const [alunoId, setAlunoId] = useState<number | null>(null);
    const [periodo, setPeriodo] = useState<Periodo | null>(null);
    const [aluno, setAluno] = useState<AlunoResponseDTO | null>(null);

    async function fetchAluno(alunoId: number) {
        // Ajuste a rota para o endpoint que retorna o AlunoResponseDTO
        const response = await axios.get(`http://localhost:8080/usuario/${alunoId}`);
        return response.data;
    }

    // Lê o token e define o alunoId no state
    useEffect(() => {
        const token = localStorage.getItem("token");
        if (token) {
            const parsedId = parseInt(token, 10);
            if (!isNaN(parsedId)) {
                setAlunoId(parsedId);
            }
        }
    }, []);

    // Sempre que 'alunoId' mudar, busca dados do aluno
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
                console.log("Período da matrícula:", data);
            } catch (error) {
                console.error("Erro ao buscar período de matrícula:", error);
            }
        }
        fetchPeriodo();
    }, []);

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

                {/* Disciplinas Já Matriculadas */}
                {aluno && aluno.matriculas && (
                    <div className="p-4 rounded-xl ring w-full">
                        <h2 className="text-xl mb-2 font-semibold">
                            Disciplinas Já Matriculadas
                        </h2>
                        {aluno.matriculas.length > 0 ? (
                            aluno.matriculas.map((matricula) => (
                                <div key={matricula.id} className="mb-4">
                                    <p className="font-semibold">
                                        Matrícula ID: {matricula.id}
                                    </p>
                                    <p>Data da Matrícula: {matricula.dataMatricula}</p>
                                    <p>Status: {matricula.statusMatricula}</p>
                                    <p className="mt-1">Disciplinas:</p>
                                    {matricula.disciplinas && matricula.disciplinas.length > 0 ? (
                                        <>
                                            {/* Filtra as obrigatórias */}
                                            {matricula.disciplinas
                                                .filter((disciplina) => disciplina.tipo === "OBRIGATORIA")
                                                .map((disciplina) => (
                                                    <div key={disciplina.id}>
                                                        • {disciplina.nome} ({disciplina.tipo})
                                                    </div>
                                                ))}

                                            {/* Filtra as optativas */}
                                            {matricula.disciplinas
                                                .filter((disciplina) => disciplina.tipo === "OPTATIVA")
                                                .map((disciplina) => (
                                                    <div key={disciplina.id}>
                                                        • {disciplina.nome} ({disciplina.tipo})
                                                    </div>
                                                ))}
                                        </>
                                    ) : (
                                        <p>Nenhuma disciplina nessa matrícula.</p>
                                    )}

                                </div>
                            ))
                        ) : (
                            <p>Nenhuma matrícula registrada.</p>
                        )}
                    </div>
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

                {/* Se houver alunoId, renderiza o componente de matrícula */}
                {alunoId ? (
                    <MatriculaComponent alunoId={alunoId} />
                ) : (
                    <p>Carregando dados do aluno...</p>
                )}
            </main>
        </div>
    );
}
