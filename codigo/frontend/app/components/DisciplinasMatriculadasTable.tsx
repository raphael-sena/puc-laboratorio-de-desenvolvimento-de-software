"use client";

import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Button } from "@/components/ui/button";
// Importe seus tipos
import { MatriculaDTO } from "../../interfaces/AlunoResponseDTO";
import { FaTrash } from "react-icons/fa6";

interface DisciplinasMatriculadasTableProps {
  matriculas: MatriculaDTO[];
  onDesmatricular: (matriculaId: number, disciplinaId: number) => Promise<void>;
}

export default function DisciplinasMatriculadasTable({
  matriculas,
  onDesmatricular,
}: DisciplinasMatriculadasTableProps) {
  // Se não tiver matrículas, exibe um texto
  if (!matriculas || matriculas.length === 0) {
    return <p>Nenhuma matrícula registrada.</p>;
  }

  return (
    <div className="p-4 rounded-xl ring w-full">
      <h2 className="text-xl mb-2 font-semibold">Disciplinas Já Matriculadas</h2>

      <Table className="min-w-[700px]">
        <TableCaption>Liste de disciplinas em que você está matriculado.</TableCaption>
        <TableHeader>
          <TableRow>
            <TableHead>Matrícula ID</TableHead>
            <TableHead>Disciplina</TableHead>
            <TableHead>Tipo</TableHead>
            <TableHead>Data da Matrícula</TableHead>
            <TableHead>Status</TableHead>
            <TableHead className="text-right">Ações</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {/* Para cada matrícula, renderizamos cada disciplina em uma linha */}
          {matriculas.flatMap((matricula) =>
            matricula.disciplinas.map((disciplina) => (
              <TableRow key={`${matricula.id}-${disciplina.id}`}>
                <TableCell>{matricula.id}</TableCell>
                <TableCell>{disciplina.nome}</TableCell>
                <TableCell>{disciplina.tipo}</TableCell>
                <TableCell>{matricula.dataMatricula}</TableCell>
                <TableCell>{matricula.statusMatricula}</TableCell>
                <TableCell className="text-right">
                  <Button
                      className="hover:cursor-pointer"
                      title="Excluir"
                      variant="destructive"
                    onClick={() => onDesmatricular(matricula.id, disciplina.id!)}
                  >
                    <FaTrash />
                  </Button>
                </TableCell>
              </TableRow>
            ))
          )}
        </TableBody>
      </Table>
    </div>
  );
}
