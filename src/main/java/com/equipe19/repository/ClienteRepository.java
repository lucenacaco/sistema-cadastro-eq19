package com.equipe19.repository;

import com.equipe19.config.ConexaoBanco;
import com.equipe19.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepository {

    public void salvar(Cliente cliente) {

        String sql = "INSERT INTO cliente (nome, cpf, telefone, endereco) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoBanco.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEndereco());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                cliente.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar cliente no banco", e);
        }
    }

    public List<Cliente> listar() {

        String sql = "SELECT * FROM cliente";
        List<Cliente> lista = new ArrayList<>();

        try (Connection conn = ConexaoBanco.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Cliente c = new Cliente();
                c.setId(rs.getLong("id"));
                c.setNome(rs.getString("nome"));
                c.setCpf(rs.getString("cpf"));
                c.setTelefone(rs.getString("telefone"));
                c.setEndereco(rs.getString("endereco"));

                lista.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar clientes", e);
        }

        return lista;
    }

    public Cliente buscarPorId(Long id) {

        String sql = "SELECT * FROM cliente WHERE id = ?";

        try (Connection conn = ConexaoBanco.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Cliente c = new Cliente();
                c.setId(rs.getLong("id"));
                c.setNome(rs.getString("nome"));
                c.setCpf(rs.getString("cpf"));
                c.setTelefone(rs.getString("telefone"));
                c.setEndereco(rs.getString("endereco"));

                return c;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cliente", e);
        }

        return null;
    }

    public void excluir(Long id) {

        String sql = "DELETE FROM cliente WHERE id = ?";

        try (Connection conn = ConexaoBanco.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir cliente", e);
        }
    }

    public void atualizar(Cliente cliente) {

        String sql = "UPDATE cliente SET nome = ?, cpf = ?, telefone = ?, endereco = ? WHERE id = ?";

        try (Connection conn = ConexaoBanco.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEndereco());
            stmt.setLong(5, cliente.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar cliente", e);
        }
    }
}