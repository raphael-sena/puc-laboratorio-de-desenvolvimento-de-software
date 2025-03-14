"use client";

import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";

// Se você tiver endpoints para totals de usuários:
import {
  getTotalAlunos,
  getTotalProfessores,
  getTotalSecretarias,
  getTotalUsuarios,
} from "@/services/usuarioService";

import Disciplina from "./Disciplina";

// Importações do shadcn/ui para Formulário e Tabela
import {
  Table,
  TableHeader,
  TableRow,
  TableHead,
  TableBody,
  TableCell,
  TableCaption,
} from "@/components/ui/table";

import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";

import { Input } from "@/components/ui/input";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";

// Importa seu service de cursos
import { Curso, getCursos, createCurso } from "@/services/secretariaService";

// Definimos um schema para validar o formulário de curso
const formSchema = z.object({
  nome: z.string().min(1, "Nome é obrigatório"),
  creditos: z
    .number({ invalid_type_error: "Créditos devem ser um número" })
    .min(1, "Mínimo de 1 crédito"),
});

type FormData = z.infer<typeof formSchema>;

export default function CursosComponent() {
  // Estados para contadores
  const [alunos, setAlunos] = useState(0);
  const [professores, setProfessores] = useState(0);
  const [secretarias, setSecretarias] = useState(0);
  const [usuarios, setUsuarios] = useState(0);

  // Estado para lista de cursos
  const [cursos, setCursos] = useState<Curso[]>([]);

  // useForm com zod para criar cursos
  const form = useForm<FormData>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      nome: "",
      creditos: 0,
    },
  });

  // Função para buscar cursos do backend
  async function fetchCursos() {
    try {
      const data = await getCursos();
      setCursos(data);
    } catch (error) {
      console.error("Erro ao buscar cursos:", error);
    }
  }

  // Ao enviar o formulário, cria o curso
  async function onSubmit(values: FormData) {
    try {
      // Chama o serviço para criar
      const novoCurso = await createCurso({
        nome: values.nome,
        creditos: values.creditos,
      });
      alert(`Curso criado com sucesso! ID = ${novoCurso.id}`);

      // Recarrega a lista de cursos
      fetchCursos();

      // Reseta o formulário
      form.reset();
    } catch (error) {
      console.error("Erro ao criar curso:", error);
      alert("Falha ao criar o curso.");
    }
  }

  // Buscar contadores de usuários
  useEffect(() => {
    async function fetchAlunos() {
      try {
        const data = await getTotalAlunos();
        setAlunos(data);
      } catch (error) {
        console.error("Erro ao buscar alunos: ", error);
      }
    }

    async function fetchProfessores() {
      try {
        const data = await getTotalProfessores();
        setProfessores(data);
      } catch (error) {
        console.error("Erro ao buscar professores: ", error);
      }
    }

    async function fetchSecretarias() {
      try {
        const data = await getTotalSecretarias();
        setSecretarias(data);
      } catch (error) {
        console.error("Erro ao buscar secretarias: ", error);
      }
    }

    async function fetchUsuariosTotal() {
      try {
        const data = await getTotalUsuarios();
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

  // Buscar lista de cursos assim que o componente montar
  useEffect(() => {
    fetchCursos();
  }, []);

  return (
    <div className="grid grid-cols-5 grid-rows-8 gap-4 *:ring *:rounded-lg">
      {/* Botões de navegação ou outras seções */}
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

      {/* Contadores de usuários */}
      <div className="col-start-1 row-start-5 px-4 flex items-center justify-center">
        Total Alunos: {alunos}
      </div>
      <div className="col-start-1 row-start-6 px-4 flex items-center justify-center">
        Total Professores: {professores}
      </div>
      <div className="col-start-1 row-start-7 px-4 flex items-center justify-center">
        Total Secretarias: {secretarias}
      </div>
      <div className="col-start-1 row-start-8 px-4 flex items-center justify-center">
        Total Usuários: {usuarios}
      </div>

      {/* Coluna de Disciplinas */}
      <div className="col-span-2 row-span-8 col-start-2 row-start-1 p-4">
        <h1 className="text-lg font-semibold mb-2">Disciplinas</h1>
        <Disciplina />
      </div>

      {/* Coluna de Cursos */}
      <div className="col-span-2 row-span-8 col-start-4 row-start-1 p-4">
        <h1 className="text-lg font-semibold">Cursos</h1>

        {/* Formulário para criar curso */}
        <div className="mt-4">
          <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
              {/* Campo Nome */}
              <FormField
                control={form.control}
                name="nome"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Nome do Curso</FormLabel>
                    <FormControl>
                      <Input placeholder="Ex: Matemática" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />

              {/* Campo Créditos */}
              <FormField
                control={form.control}
                name="creditos"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Créditos</FormLabel>
                    <FormControl>
                      <Input
                        type="number"
                        placeholder="Ex: 60"
                        {...field}
                        // Se "field.value" for string, converta a number
                        onChange={(e) => field.onChange(+e.target.value)}
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <Button type="submit" className="mt-2">
                Criar Curso
              </Button>
            </form>
          </Form>
        </div>

        {/* Tabela de cursos */}
        <div className="mt-8">
          <h2 className="text-md font-semibold mb-2">Lista de Cursos</h2>
          <Table>
            <TableCaption>Relação de todos os cursos cadastrados</TableCaption>
            <TableHeader>
              <TableRow>
                <TableHead className="w-[80px]">ID</TableHead>
                <TableHead>Nome</TableHead>
                <TableHead>Créditos</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {cursos.length === 0 ? (
                <TableRow>
                  <TableCell colSpan={3} className="text-center">
                    Nenhum curso cadastrado
                  </TableCell>
                </TableRow>
              ) : (
                cursos.map((curso) => (
                  <TableRow key={curso.id}>
                    <TableCell>{curso.id}</TableCell>
                    <TableCell>{curso.nome}</TableCell>
                    <TableCell>{curso.creditos}</TableCell>
                  </TableRow>
                ))
              )}
            </TableBody>
          </Table>
        </div>
      </div>
    </div>
  );
}
