package com.joao.empresa.dao;

import com.joao.empresa.database.ConnectionFactory;
import com.joao.empresa.model.Equipamento;
import com.joao.empresa.model.Manutencao;
import com.joao.empresa.model.Tecnico;

import java.sql.*;


public class ManutencaoDAO {

    private final EquipamentoDAO equipamentoDAO = new EquipamentoDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

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

    public Manutencao buscarPorId(int id){

        String sql = "SELECT * FROM manutencao WHERE id_manutencao = ?";

        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()){

                // à cada tupla (uma manutencao) do banco de dados eu chamo a função. Só vai ter uma pq é id
                if(rs.next()){
                    return construirManutencao(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar manutenção", e);
        }

        return null;
    }

    // transforma uma linha do banco em um objeto Manutencao
    private Manutencao construirManutencao(ResultSet rs) throws SQLException {

        int id = rs.getInt("id_manutencao");

        Manutencao.TipoManutencao tipo =
                Manutencao.TipoManutencao.valueOf(
                        rs.getString("tipo_manutencao")
                ); // o JDBC retorna o valor do ENUM como uma String

        String descricao = rs.getString("descricao");
        double custo = rs.getDouble("custo");
        Date dataInicio = rs.getDate("data_inicio");
        Date dataFim = rs.getDate("data_fim");

        Manutencao.Status status =
                Manutencao.Status.valueOf(
                        rs.getString("status")
                );

        int equipamentoId = rs.getInt("fk_idequipamento");

        int tecnicoId = rs.getInt("fk_idtecnico");

        Equipamento equipamento = equipamentoDAO.buscarPorId(equipamentoId);

        Tecnico tecnicoResponsavel = (Tecnico) usuarioDAO.buscarPorId(tecnicoId);

        Manutencao manutencao = new Manutencao(
                id,
                tipo,
                descricao,
                custo,
                dataInicio.toLocalDate(),
                dataFim.toLocalDate(),
                status,
                equipamento,
                tecnicoResponsavel
        );

        return manutencao;
    }



}
