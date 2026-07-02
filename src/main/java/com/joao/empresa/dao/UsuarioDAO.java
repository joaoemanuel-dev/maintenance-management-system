package com.joao.empresa.dao;

import com.joao.empresa.model.*;
import com.joao.empresa.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public void salvar(Usuario usuario) { // método que vai salvar um usuário no banco

        // código sql que vai receber os parâmetros com PreparedStatement
        String sql = "INSERT INTO usuario (nome, email, senha, tipo_usuario) VALUES (?, ?, ?, ?)";

        // tudo aqui será fechado automaticamente
        try (Connection conn = ConnectionFactory.getConnection(); // cria a conexão com o bd
             PreparedStatement stmt = conn.prepareStatement(sql)) { // esse objeto que coloca o valor no “?” SQL.

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

    public Usuario buscarPorId(int id) {

        // isso é como que eu pesquisaria no MySQL e o id vem do parâmetro
        String sql = "SELECT * FROM usuario WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) { // esse objeto que coloca o valor no sql

            // Substitui o ? pelo id
            stmt.setInt(1, id);

            // Executa SELECT
            ResultSet rs = stmt.executeQuery();

            // Se encontrou resultado (o primeiro é o cabeçalho, vem se tem tupla depois)
            if (rs.next()) {

                String nome = rs.getString("nome"); // pego o valor da coluna e salva aqui
                String email = rs.getString("email");
                String senha = rs.getString("senha");
                String especialidade = rs.getString("especialidade");
                String areaResponavel = rs.getString("area_responsavel");

                // Converte String do banco → enum
                Usuario.TipoUsuario tipo =
                        Usuario.TipoUsuario.valueOf(rs.getString("tipo_usuario"));

                switch (tipo) { // Aqui eu crio o objeto com base nos valores que busquei no banco
                    case ADMINISTRADOR:
                        return new Administrador(id, nome, email, senha, "TI");
                    case TECNICO:
                        return new Tecnico(id, nome, email, senha, especialidade);
                    case GESTOR:
                        return new Gestor(id, nome, email, senha, areaResponavel);
                    default:
                        throw new RuntimeException("Tipo de usuário inválido");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário", e);
        }
        return null;
    }

    public List<Usuario> listar() {

        String sql = "SELECT * FROM usuario"; // o que seria executado no workbench

        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql); // vai guardando os pedaços de SQL
             ResultSet rs = stmt.executeQuery()) { // resultado da consulta pq ja consulta de uma vez para listar

            // Enquanto existir próxima linha no resultado
            while (rs.next()) {

                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String senha = rs.getString("senha");

                Usuario.TipoUsuario tipo =
                        Usuario.TipoUsuario.valueOf(rs.getString("tipo_usuario"));

                // vou instanciar os objetos 'Usuario' de acordo com cada tipo e joga tudo na lista
                Usuario usuario;

                switch (tipo) {
                    case ADMINISTRADOR:
                        usuario = new Administrador(id, nome, email, senha, "TI");
                        break;
                    case TECNICO:
                        String especialidade = rs.getString("especialidade");
                        usuario = new Tecnico(id, nome, email, senha, especialidade);
                        break;
                    case GESTOR:
                        String areaResponsavel = rs.getString("area_responsavel");
                        usuario = new Gestor(id, nome, email, senha, areaResponsavel);
                        break;
                    default:
                        throw new RuntimeException("Tipo inválido");
                }

                // adiciona na lista
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuários", e);
        }

        return usuarios;
    }

    public void atualizar(Usuario usuario) {

        String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ?, tipo_usuario = ?, especialidade = ? WHERE id = ?";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getTipoUsuario().name()); // pego o nome do enum

            if (usuario instanceof Tecnico) { // usuario é uma instância de Tecnico, eu coloco a especialidade
                stmt.setString(5, ((Tecnico) usuario).getEspecialidade());
            } else {
                stmt.setString(5, null);
            }

            stmt.setInt(6, usuario.getId());

            stmt.executeUpdate(); // preencho os parâmetros do SQL e vai pro banco

            System.out.println("Usuário atualizado!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário", e);
        }
    }

}
