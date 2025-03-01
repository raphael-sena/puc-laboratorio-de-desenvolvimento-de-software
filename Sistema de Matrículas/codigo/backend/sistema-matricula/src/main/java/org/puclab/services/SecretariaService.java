package org.puclab.services;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.puclab.models.PeriodoMatricula;
import org.puclab.models.Usuario;
import org.puclab.models.dtos.UsuarioDTO;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class SecretariaService implements PeriodoMatricula {

    @Override
    @Transactional
    public LocalDate[] gerarPeriodoMatricula(LocalDate dataInicio, LocalDate dataFim) {
        if (dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("A data de início não pode ser depois da data de fim.");
        }

        ConfiguracaoSistema config = ConfiguracaoSistema.find("chave", "PERIODO_MATRICULA").firstResult();
        if (config == null) {
            config = new ConfiguracaoSistema();
            config.chave = "PERIODO_MATRICULA";
        }

        config.valor = dataInicio.toString() + ";" + dataFim.toString();

        config.persistAndFlush();

        return new LocalDate[]{dataInicio, dataFim};
    }

    @Override
    public LocalDate[] obterPeriodoMatricula() {
        ConfiguracaoSistema config = ConfiguracaoSistema.find("chave", "PERIODO_MATRICULA").firstResult();
        if (config == null || config.valor == null) {
            throw new RuntimeException("Período de matrícula não configurado");
        }

        String[] datas = config.valor.split(";");
        return new LocalDate[]{
                LocalDate.parse(datas[0]),
                LocalDate.parse(datas[1])
        };
    }
}
