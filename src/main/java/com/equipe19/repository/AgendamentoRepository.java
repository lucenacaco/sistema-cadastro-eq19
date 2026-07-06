package com.equipe19.repository;

import com.equipe19.config.ConexaoBanco;
import com.equipe19.model.Agendamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoRepository {

    public void salvar(Agendamento agendamento) {

        String sql = "INSERT INTO agendamento (cliente_id, servico_id, data, hora, status, observacao) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBanco.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, agendamento.getClienteId());
            stmt.setLong(2, agendamento.getServicoId());
            stmt.setDate(3, Date.valueOf(agendamento.getData()));
            stmt.setTime(4, Time.valueOf(agendamento.getHora()));
            stmt.setString(5, agendamento.getStatus());
            stmt.setString(6, agendamento.getObservacao());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                agendamento.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar agendamento", e);
        }
    }

    public List<Agendamento> listar() {

        String sql = "SELECT * FROM agendamento";
        List<Agendamento> lista = new ArrayList<>();

        try (Connection conn = ConexaoBanco.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Agendamento agendamento = new Agendamento();

                agendamento.setId(rs.getLong("id"));
                agendamento.setClienteId(rs.getLong("cliente_id"));
                agendamento.setServicoId(rs.getLong("servico_id"));
                agendamento.setData(rs.getDate("data").toLocalDate());
                agendamento.setHora(rs.getTime("hora").toLocalTime());
                agendamento.setStatus(rs.getString("status"));
                agendamento.setObservacao(rs.getString("observacao"));

                lista.add(agendamento);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar agendamentos", e);
        }

        return lista;
    }

    public Agendamento buscarPorId(Long id) {

        String sql = "SELECT * FROM agendamento WHERE id = ?";

        try (Connection conn = ConexaoBanco.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Agendamento agendamento = new Agendamento();

                agendamento.setId(rs.getLong("id"));
                agendamento.setClienteId(rs.getLong("cliente_id"));
                agendamento.setServicoId(rs.getLong("servico_id"));
                agendamento.setData(rs.getDate("data").toLocalDate());
                agendamento.setHora(rs.getTime("hora").toLocalTime());
                agendamento.setStatus(rs.getString("status"));
                agendamento.setObservacao(rs.getString("observacao"));

                return agendamento;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar agendamento", e);
        }

        return null;
    }

    public void atualizar(Agendamento agendamento) {

        String sql = "UPDATE agendamento SET cliente_id = ?, servico_id = ?, data = ?, hora = ?, status = ?, observacao = ? WHERE id = ?";

        try (Connection conn = ConexaoBanco.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, agendamento.getClienteId());
            stmt.setLong(2, agendamento.getServicoId());
            stmt.setDate(3, Date.valueOf(agendamento.getData()));
            stmt.setTime(4, Time.valueOf(agendamento.getHora()));
            stmt.setString(5, agendamento.getStatus());
            stmt.setString(6, agendamento.getObservacao());
            stmt.setLong(7, agendamento.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar agendamento", e);
        }
    }

    public void excluir(Long id) {

        String sql = "DELETE FROM agendamento WHERE id = ?";

        try (Connection conn = ConexaoBanco.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir agendamento", e);
        }
    }
}