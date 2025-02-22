package org.puclab.models;

import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_secretaria")
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Secretaria extends Usuario {
    private Set<Usuario> usuarios;

    // TODO
    public Set<Aluno> getAlunos() {
        return new HashSet<>();
    }

    // TODO
    public Set<Aluno> getProfessores() {
        return new HashSet<>();
    }

    // TODO
    public void editarUsuario(Usuario usuario) {
    }

//    // TODO
//    public Curriculo gerarCurriculo(Set<Disciplina> disciplinas) {
//        return new Curriculo();
//    }

    // TODO
    public void criarUsuario(Usuario usuario) {
    }
}
