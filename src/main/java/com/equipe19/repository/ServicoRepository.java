package com.equipe19.repository;

import com.equipe19.config.ConexaoBanco;
import com.equipe19.model.Servico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoRepository {

    public void salvar(Servico servico) {

        String sql = "INSERT INTO servico (nome, valor, duracao) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoBanco.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, servico.getNome());
            stmt.setBigDecimal(2, servico.getValor());
            stmt.setInt(3, servico.getDuracao());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                servico.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar serviço", e);
        }
    }

    public List<Servico> listar() {

        String sql = "SELECT * FROM servico";
        List<Servico> lista = new ArrayList<>();

        try (Connection conn = ConexaoBanco.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Servico servico = new Servico();

                servico.setId(rs.getLong("id"));
                servico.setNome(rs.getString("nome"));
                servico.setValor(rs.getBigDecimal("valor"));
                servico.setDuracao(rs.getInt("duracao"));

                lista.add(servico);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar serviços", e);
        }

        return lista;
    }

    public Servico buscarPorId(Long id) {

        String sql = "SELECT * FROM servico WHERE id = ?";

        try (Connection conn = ConexaoBanco.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Servico servico = new Servico();

                servico.setId(rs.getLong("id"));
                servico.setNome(rs.getString("nome"));
                servico.setValor(rs.getBigDecimal("valor"));
                servico.setDuracao(rs.getInt("duracao"));

                return servico;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar serviço", e);
        }

        return null;
    }

    public void atualizar(Servico servico) {

        String sql = "UPDATE servico SET nome = ?, valor = ?, duracao = ? WHERE id = ?";

        try (Connection conn = ConexaoBanco.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, servico.getNome());
            stmt.setBigDecimal(2, servico.getValor());
            stmt.setInt(3, servico.getDuracao());
            stmt.setLong(4, servico.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar serviço", e);
        }
    }

    public void excluir(Long id) {

        String sql = "DELETE FROM servico WHERE id = ?";

        try (Connection conn = ConexaoBanco.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir serviço", e);
        }
    }
}