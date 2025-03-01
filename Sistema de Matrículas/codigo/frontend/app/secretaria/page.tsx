"use client";

import { getPeriodoMatricula } from "../../services/secretariaService";
import { useEffect, useState } from "react";
import CadastroPessoasComponent from "../components/CadastroPessoasComponent";
import GerenciarPessoasComponent from "../components/GerenciarPessoasComponent";

export default function Secretaria() {
  interface Periodo {
    dataInicio: string;
    dataFim: string;
  }

  const [periodo, setPeriodo] = useState<Periodo | null>(null);

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
      <main className="flex flex-col row-start-2 items-center sm:items-start">
        <h1 className="text-3xl mb-8 font-bold text-center ">Secretaria</h1>

        <div className="flex gap-4 w-full">
          <div className="flex flex-col gap-4 w-full">
            <div className="p-4 rounded-xl ring w-full">
              <div>
                {periodo ? (
                  <div>
                    <h1 className="text-xl mb-2 font-semibold ">
                      Período de Matrícula
                    </h1>
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
                          new Date().getTime()) /
                          86400000
                      )}{" "}
                    </p>
                  </div>
                ) : (
                  <p>Carregando...</p>
                )}
              </div>
            </div>

            <div className="p-4 rounded-xl ring w-full">
              <h1 className="text-xl mb-2 font-semibold">Cadastrar Pessoas</h1>
              {/* Formulário para cadastro */}
              <CadastroPessoasComponent />
            </div>
          </div>

          <div className="p-4 rounded-xl ring w-full">
            {/* Gerenciamento de Pessoas */}
            <GerenciarPessoasComponent />
          </div>
        </div>
      </main>
    </div>
  );
}
