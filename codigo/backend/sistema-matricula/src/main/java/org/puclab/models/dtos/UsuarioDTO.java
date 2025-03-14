package org.puclab.models.dtos;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.puclab.models.Usuario;

@RegisterForReflection
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioDTO {

    public Long id;
    public String nome;
    public String senha;
    public String tipo;

    public UsuarioDTO(PanacheEntityBase usuario) {
        Usuario u = (Usuario) usuario;
        this.id = u.id;
        this.nome = u.nome;
        this.senha = u.senha;
        this.tipo = u.tipo;
    }

}
