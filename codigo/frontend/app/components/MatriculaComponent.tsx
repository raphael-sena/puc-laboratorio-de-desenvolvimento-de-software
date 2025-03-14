"use client";

import React, { useState, useEffect } from "react";
import axios from "axios";

async function getDisciplinasObrigatorias() {
  const response = await axios.get("http://localhost:8080/disciplinas/obrigatorias");
  return response.data; 
}

async function getDisciplinasOptativas() {
  const response = await axios.get("http://localhost:8080/disciplinas/optativas");
  return response.data;
}

async function doMatricula(data: {
  alunoId: number;
  disciplinasObrigatorias: number[];
  disciplinasOptativas: number[];
}) {
  const response = await axios.post("http://localhost:8080/matriculas", data);
  return response.data;
}

interface Disciplina {
  id: number;
  nome: string;
  tipo: string;
}

interface MatriculaComponentProps {
  alunoId: number;
}

export default function MatriculaComponent({ alunoId }: MatriculaComponentProps) {
  const [disciplinasObrigatorias, setDisciplinasObrigatorias] = useState<Disciplina[]>([]);
  const [disciplinasOptativas, setDisciplinasOptativas] = useState<Disciplina[]>([]);

  const [selectedObrigatorias, setSelectedObrigatorias] = useState<number[]>([]);
  const [selectedOptativas, setSelectedOptativas] = useState<number[]>([]);

  useEffect(() => {
    async function fetchDisciplinas() {
      try {
        const obrigatorias = await getDisciplinasObrigatorias();
        const optativas = await getDisciplinasOptativas();

        setDisciplinasObrigatorias(obrigatorias);
        setDisciplinasOptativas(optativas);
      } catch (error) {
        console.error("Erro ao buscar disciplinas:", error);
      }
    }
    fetchDisciplinas();
  }, []);

  function handleObrigatoriaChange(disciplinaId: number) {
    setSelectedObrigatorias((prevSelected) =>
      prevSelected.includes(disciplinaId)
        ? prevSelected.filter((id) => id !== disciplinaId)
        : [...prevSelected, disciplinaId]
    );
  }

  function handleOptativaChange(disciplinaId: number) {
    setSelectedOptativas((prevSelected) =>
      prevSelected.includes(disciplinaId)
        ? prevSelected.filter((id) => id !== disciplinaId)
        : [...prevSelected, disciplinaId]
    );
  }

  async function handleMatricular() {
    try {
      const requestDTO = {
        alunoId,
        disciplinasObrigatorias: selectedObrigatorias,
        disciplinasOptativas: selectedOptativas,
      };

      await doMatricula(requestDTO);
      alert("Matrícula realizada com sucesso!");

      setSelectedObrigatorias([]);
      setSelectedOptativas([]);
    } catch (error) {
      console.error("Erro ao realizar matrícula:", error);
      alert("Erro ao realizar matrícula. Tente novamente.");
    }
  }

  return (
    <div className="p-4 rounded-xl ring w-full mt-4">
      <h2 className="text-xl mb-4 font-semibold">Matrícula em Disciplinas</h2>

      {/* Organiza as duas colunas (obrigatórias / optativas) lado a lado */}
      <div className="flex flex-col sm:flex-row gap-4">
        {/* Coluna das obrigatórias */}
        <div className="flex-1 p-4 border rounded">
          <h3 className="text-lg font-medium mb-2">Disciplinas Obrigatórias</h3>
          {disciplinasObrigatorias.length === 0 ? (
            <p>Carregando ou não existem disciplinas obrigatórias...</p>
          ) : (
            disciplinasObrigatorias.map((disciplina) => (
              <div key={disciplina.id}>
                <label>
                  <input
                    type="checkbox"
                    checked={selectedObrigatorias.includes(disciplina.id)}
                    onChange={() => handleObrigatoriaChange(disciplina.id)}
                  />
                  <span className="ml-2">{disciplina.nome}</span>
                </label>
              </div>
            ))
          )}
        </div>

        {/* Coluna das optativas */}
        <div className="flex-1 p-4 border rounded">
          <h3 className="text-lg font-medium mb-2">Disciplinas Optativas</h3>
          {disciplinasOptativas.length === 0 ? (
            <p>Carregando ou não existem disciplinas optativas...</p>
          ) : (
            disciplinasOptativas.map((disciplina) => (
              <div key={disciplina.id}>
                <label>
                  <input
                    type="checkbox"
                    checked={selectedOptativas.includes(disciplina.id)}
                    onChange={() => handleOptativaChange(disciplina.id)}
                  />
                  <span className="ml-2">{disciplina.nome}</span>
                </label>
              </div>
            ))
          )}
        </div>
      </div>

      <button
        onClick={handleMatricular}
        className="bg-blue-500 text-white py-2 px-4 rounded mt-4"
      >
        Confirmar Matrícula
      </button>
    </div>
  );
}
