import axios from "axios";

export async function desmatricularDisciplina(matriculaId: number, disciplinaId: number) {
  return axios.delete(`http://localhost:8080/matriculas/${matriculaId}/disciplina/${disciplinaId}`);
}
