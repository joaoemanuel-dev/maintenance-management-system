package com.joao.empresa.builders;

import com.joao.empresa.model.Manutencao;

import java.time.LocalDate;

public class ManutencaoBuilder {

    public static Manutencao umaManutencao() {
        return new Manutencao(
                Manutencao.TipoManutencao.PREVENTIVA,
                "Manutenção de teste",
                LocalDate.of(2026, 1, 15),
                EquipamentoBuilder.umEquipamento(),
                TecnicoBuilder.umTecnico()
        );
    }
}
