// AlunoResponseDTO.ts

/**
 * Caso seu backend retorne a data em formato string (ISO), você pode manter como string.
 * Se você parsear para Date no front, ajuste para 'Date' como tipo do campo.
 */
export interface DisciplinaDTO {
    id: number;
    nome: string;
    tipo: string; // "OBRIGATORIA" | "OPTATIVA" etc.
  }
  
  /**
   * Se você souber as possíveis strings para StatusMatricula,
   * pode substituir "string" por uma union type
   * (ex: type StatusMatricula = "ATIVA" | "INATIVA" | ... )
   * ou até definir um 'enum' em TypeScript.
   */
  export type StatusMatricula = string;
  
  export interface MatriculaDTO {
    id: number;
    dataMatricula: string; // ou Date, se você fizer parsing
    alunoId: number;
    disciplinas: DisciplinaDTO[];
    statusMatricula: StatusMatricula;
  }
  
  export interface AlunoResponseDTO {
    id: number;
    nome: string;
    matriculas: MatriculaDTO[];
  }
  