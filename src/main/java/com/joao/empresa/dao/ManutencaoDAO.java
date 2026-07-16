package com.joao.empresa.dao;

import com.joao.empresa.database.ConnectionFactory;
import com.joao.empresa.model.Manutencao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ManutencaoDAO {

    public void salvar(Manutencao manutencao){

        String sql = """
                INSERT INTO manutencao
                (tipo_manutencao, data_inicio, data_fim, descricao, custo,
                 status, fk_idequipamento, fk_idtecnico)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, manutencao.getTipoManutencao().name());
            stmt.setDate(2, Date.valueOf(manutencao.getDataInicio()));
            stmt.setDate(3, Date.valueOf(manutencao.getDataFim()));
            stmt.setString(4, manutencao.getDescricao());
            stmt.setDouble(5, manutencao.getCusto());
            stmt.setString(6, manutencao.getStatus().name());
            stmt.setInt(7, manutencao.getEquipamento().getId());
            stmt.setInt(8, manutencao.getTecnicoResponsavel().getId());

            stmt.executeUpdate();

            System.out.println("Manutenção salva com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar manutenção", e);
        }

    }



}
