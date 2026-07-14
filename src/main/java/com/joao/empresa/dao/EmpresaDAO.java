package com.joao.empresa.dao;

import com.joao.empresa.database.ConnectionFactory;
import com.joao.empresa.model.Empresa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmpresaDAO {

    public void salvar(Empresa empresa) {

        String sql = "INSERT INTO empresa (nome, cnpj, endereco, segmento, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, empresa.getNome());
            stmt.setString(2, empresa.getCnpj());
            stmt.setString(3, empresa.getEndereco());
            stmt.setString(4, empresa.getSegmento());
            stmt.setString(5, empresa.getStatus().name());

            stmt.executeUpdate();

            System.out.println("Empresa salva com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar empresa", e);
        }
    }



}
