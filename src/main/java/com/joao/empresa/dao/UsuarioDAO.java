package com.joao.empresa.dao;

import com.joao.empresa.model.*;
import com.joao.empresa.database.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // Primeiro eu salvo a superclasse usuário no banco de dados
    public void salvar(Usuario usuario) {

        // código sql que vai receber os parâmetros com PreparedStatement
        String sql = "INSERT INTO usuario (nome, email, senha, tipo_usuario) VALUES (?, ?, ?, ?)";

        // tudo aqui será fechado automaticamente
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // esse objeto que coloca o valor no ? do sql // salva as chaves primárias geradas no banco

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getTipo().name()); // enum convertido para String

            stmt.executeUpdate(); // executa o insert no banco

            ResultSet rs = stmt.getGeneratedKeys(); // recupero as chaves primárias

            if (rs.next()) {
                int idGerado = rs.getInt(1); // pega o id gerado e joga na especialização
                salvarDadosEspecificos(usuario, idGerado);
            }

            System.out.println("Usuário salvo com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar usuário", e);
        }
    }

    // Depois eu salvo as subclasses com seus dados específicos no banco de dados,
    // com chaves primárias fazendo referência ao id do usuario pai
    private void salvarDadosEspecificos(Usuario usuario, int idGerado) {

        if (usuario instanceof Tecnico tecnico) { // se usuario for instância de tecnico
            String sql = "INSERT INTO tecnico (usuario_id, especialidade) VALUES (?, ?)";

            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, idGerado);
                stmt.setString(2, tecnico.getEspecialidade());

                stmt.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException("Erro ao salvar técnico", e);
            }
        }

        if (usuario instanceof Gestor gestor) {
            String sql = "INSERT INTO gestor (usuario_id, area_responsavel) VALUES (?, ?)";

            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, idGerado);
                stmt.setString(2, gestor.getAreaResponsavel());

                stmt.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException("Erro ao salvar gestor", e);
            }
        }

        if (usuario instanceof Administrador administrador) {
            String sql = "INSERT INTO administrador (usuario_id, nivel_acesso, departamento) VALUES (?, ?, ?)";

            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, idGerado);
                stmt.setString(2, administrador.getNivelAcesso().name());
                stmt.setString(3, administrador.getDepartamento());

                stmt.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException("Erro ao salvar administrador", e);
            }
        }
    }

    // primeiro busca na tabela usuario, depois que achar busca na tabela específica com o mesmo id
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
            // a ideia é criar o objeto com os dados que eu busquei na tabela
            if (rs.next()) {

                String nome = rs.getString("nome"); // pego o valor da coluna e salva aqui
                String email = rs.getString("email");
                String senha = rs.getString("senha");

                // Converte String do banco → enum
                Usuario.TipoUsuario tipo =
                        Usuario.TipoUsuario.valueOf(
                                rs.getString("tipo_usuario")
                        );

                // aí com base no tipo de usuário, eu passo os atributos que já tem de usuário
                // e ele cria o objeto específico já na função. Pq o id é o mesmo
                switch (tipo) {
                    case TECNICO:
                        return buscarTecnico(id, nome, email, senha);

                    case GESTOR:
                        return buscarGestor(id, nome, email, senha);

                    case ADMINISTRADOR:
                        return buscarAdministrador(id, nome, email, senha);

                    default:
                        throw new RuntimeException("Tipo de usuário inválido");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário", e);
        }
        return null;
    }

    private Tecnico buscarTecnico(int id, String nome, String email, String senha) {

        String sql = "SELECT * FROM tecnico WHERE usuario_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) { // o primeiro que retornar na consulta (só terá um)

                String especialidade = rs.getString("especialidade");

                return new Tecnico(id, nome, email, senha, especialidade);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar técnico", e);
        }

        return null;
    }

    private Gestor buscarGestor(int id, String nome, String email, String senha) {

        String sql = "SELECT * FROM gestor WHERE usuario_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                String areaResponsavel = rs.getString("area_responsavel");

                return new Gestor(id, nome, email, senha, areaResponsavel);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar gestor", e);
        }

        return null;
    }

    private Administrador buscarAdministrador(int id, String nome, String email, String senha) {

        String sql = "SELECT * FROM administrador WHERE usuario_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                String departamento = rs.getString("departamento");

                return new Administrador(id, nome, email, senha, departamento);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar administrador", e);
        }

        return null;
    }

    // ao pé da letra, na verdade, eu não "retorno usuario", eu retorno tecnico, adm e gestor
    public List<Usuario> listar() {

        String sql = "SELECT * FROM usuario"; // o que seria executado no workbench, retorna todos os usuarios

        // eu vou buscar todos os dados no banco, instanciar vários objeto e colocá-los nesse lista para retornar
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql); // vai guardando os pedaços de SQL
             ResultSet rs = stmt.executeQuery()) { // resultado da consulta pq ja consulta de uma vez para listar

            // Enquanto existir próxima linha no resultado
            while (rs.next()) {

                int id = rs.getInt("id"); // o id já foi gerado, já está lá no banco
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String senha = rs.getString("senha");

                Usuario.TipoUsuario tipo =
                        Usuario.TipoUsuario.valueOf(rs.getString("tipo_usuario"));

                // vou instanciar de acordo com cada tipo e joga tudo na lista
                // eu uso usuario só para pegar os atributos gerais, crio objeto com o específico
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
            stmt.setString(4, usuario.getTipo().name()); // pego o nome do enum

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

    public void deletar(int id){

        String sql = "DELETE FROM usuario WHERE id = ?";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();

            System.out.println("Usuário deletado!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar usuário", e);
        }
    }

}
