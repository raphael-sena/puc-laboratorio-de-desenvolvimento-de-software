package org.puclab.services;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "configuracao_sistema")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ConfiguracaoSistema extends PanacheEntity {

    @Column(unique = true, nullable = false)
    public String chave;

    @Column(columnDefinition = "TEXT")
    public String valor;
}