package org.puclab.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.internal.build.AllowNonPortable;

@AllowNonPortable
@NoArgsConstructor
@Getter
@Setter
public class LoginDTO {

    private Long id;
    private String senha;
}
