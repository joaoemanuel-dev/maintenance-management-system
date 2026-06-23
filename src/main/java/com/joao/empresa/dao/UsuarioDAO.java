package com.joao.empresa.dao;

import com.joao.empresa.model.Usuario;
import com.joao.empresa.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioDAO {

    public void salvar(Usuario usuario) { // método que vai salvar um usuário no banco

        String sql = "INSERT INTO usuario (nome, email, senha, tipo_usuario) VALUES (?, ?, ?, ?)";

        // tudo aqui será fechado automaticamente
        try (Connection conn = ConnectionFactory.getConnection(); // cria a conexão com o bd
             PreparedStatement stmt = conn.prepareStatement(sql)) { // serve para passar os valores por parâmetro para o SQL

            // esse objeto vai guardando os parâmetros e as informações, depois executa tudo organizado no SQL
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getTipoUsuario().name()); // enum convertido para String

            stmt.executeUpdate(); // executa o insert no banco

            System.out.println("Usuário salvo com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar usuário", e);
        }
    }
}
