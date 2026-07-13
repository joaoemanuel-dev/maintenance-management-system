package com.joao.empresa.dao;

import com.joao.empresa.database.ConnectionFactory;
import com.joao.empresa.model.Equipamento;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EquipamentoDAO {

    public void salvar(Equipamento equipamento){

        String sql = "INSERT INTO equipamento (nome, codigo_patrimonio, data_aquisicao) values (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, equipamento.getNome());
            stmt.setString(2, equipamento.getCodigoPatrimonio());
            stmt.setDate(3, Date.valueOf(equipamento.getDataAquisicao()));

            stmt.executeUpdate();

            System.out.println("Equipamento salvo com sucesso");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar equipamento", e);
        }

    }


}
