//package models;
//
//import java.util.HashSet;
//import java.util.Set;
//
//public class Professor extends Usuario {
//
//    private Set<Disciplina> disciplinas;
//
//    public Professor(Set<Disciplina> disciplinas) {
//        this.disciplinas = disciplinas;
//    }
//
//    public Professor(Long id, String nome, String senha, Set<Disciplina> disciplinas) {
//        super(id, nome, senha);
//        this.disciplinas = disciplinas;
//    }
//
//    public Set<Disciplina> getDisciplinas() {
//        return disciplinas;
//    }
//
//    public void setDisciplinas(Set<Disciplina> disciplinas) {
//        this.disciplinas = disciplinas;
//    }
//
//    @Override
//    public String toString() {
//        return "Professor{" +
//                "disciplinas=" + disciplinas +
//                '}';
//    }
//
//    public Set<Aluno> obterAlunosDisciplina(Disciplina disciplina) {
//        return new HashSet<>();
//    }
//}
