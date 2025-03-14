"use client";

import { Button } from "@/components/ui/button";
import {
  getTotalAlunos,
  getTotalProfessores,
  getTotalSecretarias,
  getTotalUsuarios,
} from "@/services/usuarioService";
import { use, useEffect, useState } from "react";
import Disciplina from "./Disciplina";

export default function CursosComponent() {
  const [alunos, setAlunos] = useState(0);
  const [professores, setProfessores] = useState(0);
  const [secretarias, setSecretarias] = useState(0);
  const [usuarios, setUsuarios] = useState(0);

  useEffect(() => {
    async function fetchAlunos() {
      try {
        const data = await getTotalAlunos();
        console.log("Alunos: ", data);
        setAlunos(data);
      } catch (error) {
        console.error("Erro ao buscar alunos: ", error);
      }
    }

    async function fetchProfessores() {
      try {
        const data = await getTotalProfessores();
        console.log("Professores: ", data);
        setProfessores(data);
      } catch (error) {
        console.error("Erro ao buscar professores: ", error);
      }
    }

    async function fetchSecretarias() {
      try {
        const data = await getTotalSecretarias();
        console.log("Secretarias: ", data);
        setSecretarias(data);
      } catch (error) {
        console.error("Erro ao buscar secretarias: ", error);
      }
    }

    async function fetchUsuariosTotal() {
      try {
        const data = await getTotalUsuarios();
        console.log("Usuários: ", data);
        setUsuarios(data);
      } catch (error) {
        console.error("Erro ao buscar usuários: ", error);
      }
    }

    fetchAlunos();
    fetchProfessores();
    fetchSecretarias();
    fetchUsuariosTotal();
  }, []);

  return (
    <div className="grid grid-cols-5 grid-rows-8 gap-4 *:ring *:rounded-lg">
      <Button className="hover:cursor-pointer">Alunos</Button>
      <Button className="p-0 col-start-1 row-start-2 hover:cursor-pointer">
        Professores
      </Button>
      <Button className="p-0 col-start-1 row-start-3 hover:cursor-pointer">
        Secretarias
      </Button>
      <Button className="p-0 col-start-1 row-start-4 hover:cursor-pointer">
        Disciplinas
      </Button>
      <div className="col-start-1 row-start-5 px-4 flex items-center justify-center">
        Total Alunos: {alunos}{" "}
      </div>
      <div className="col-start-1 row-start-6 px-4 flex items-center justify-center">
        Total Professores: {professores}{" "}
      </div>
      <div className="col-start-1 row-start-7 px-4 flex items-center justify-center">
        Total Secreataria: {secretarias}{" "}
      </div>
      <div className="col-start-1 row-start-8 px-4 flex items-center justify-center">
        Total Usuários: {usuarios}{" "}
      </div>
      <div className="col-span-2 row-span-8 col-start-2 row-start-1 p-4">
        <h1 className="text-lg font-semibold mb-2">Disciplinas</h1>
        <Disciplina />
      </div>
      <div className="col-span-2 row-span-8 col-start-4 row-start-1 p-4">
        <h1 className="text-lg font-semibold">Cursos</h1>
      </div>
    </div>
  );
}
