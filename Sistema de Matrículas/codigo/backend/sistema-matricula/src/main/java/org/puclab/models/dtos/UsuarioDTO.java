package org.puclab.models.dtos;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
        this.nome = usuario.getClass().getSimpleName();
        this.senha = usuario.getClass().getSimpleName();
        this.tipo = usuario.getClass().getSimpleName();
    }
}
