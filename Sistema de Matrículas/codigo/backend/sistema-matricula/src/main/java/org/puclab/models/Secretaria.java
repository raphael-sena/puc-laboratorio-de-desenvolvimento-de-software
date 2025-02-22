package org.puclab.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Secretaria extends Usuario {

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "secretaria_id")
    public Set<Usuario> usuarios;

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
