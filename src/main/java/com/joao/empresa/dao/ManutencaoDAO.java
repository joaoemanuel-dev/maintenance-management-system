package com.joao.empresa.dao;

import com.joao.empresa.model.Manutencao;

public class ManutencaoDAO {

    public void salvar(Manutencao manutencao){

        String sql = """
                INSERT INTO manutencao
                (tipo_manutencao, data_inicio, data_fim, descricao, custo,
                 status, fk_idequipamento, fk_idtecnico)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

    }



}
