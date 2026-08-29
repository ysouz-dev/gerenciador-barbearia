package com.ysouz.gerenciadorbarbearia.repository;

import com.ysouz.gerenciadorbarbearia.dto.AtendimentoDTO;
import com.ysouz.gerenciadorbarbearia.exception.DatabaseException;
import com.ysouz.gerenciadorbarbearia.model.Atendimento;
import com.ysouz.gerenciadorbarbearia.connection.Conexao;
import com.ysouz.gerenciadorbarbearia.enums.*;
import com.ysouz.gerenciadorbarbearia.model.Cliente;
import com.ysouz.gerenciadorbarbearia.exception.AtendimentoNaoEncontradoException;
import com.ysouz.gerenciadorbarbearia.util.ConexaoUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * Repositório responsável pelos registros dos atendimentos do sistema.
 */
public class AtendimentoRepository {

    /**
     * Registra um novo atendimento no sistema, vinculando o cliente
     * a uma lista de serviços realizados. Também incrementa 1 ao total de atendimentos
     * do cliente vinculado ao atendimento.
     *
     * @param atendimento atendimento a ser registrado
     * @throws DatabaseException se ocorrer um erro ao acessar o banco de dados ou ao realizar rollback,
     * ou ao tentar fechar conexão com o banco de dados
     */
    public void salvar(Atendimento atendimento) {
        String query = "INSERT INTO atendimentos(cliente_cpf, valor) VALUES (?, ?) ";

        String queryTotalAtendimento = "UPDATE clientes SET total_atendimentos = total_atendimentos + 1 WHERE cpf = ?";

        Connection conexao = null;
        try {
            conexao = Conexao.getConexao();
            conexao.setAutoCommit(false);

            try (PreparedStatement statement = conexao.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement statementTotalAtendimento = conexao.prepareStatement(queryTotalAtendimento)) {

                statement.setString(1, atendimento.getPessoa().getCPF());
                statement.setBigDecimal(2, atendimento.getTotal());
                statement.executeUpdate();

                statementTotalAtendimento.setString(1, atendimento.getPessoa().getCPF());
                statementTotalAtendimento.executeUpdate();

                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {

                        String query2 = "INSERT INTO atendimentos_servicos(id_atendimento, id_servico) VALUES (?, ?)";

                        // id do atendimento que esta sendo salvo
                        int idAtendimento = rs.getInt(1);

                        try (PreparedStatement statement2 = conexao.prepareStatement(query2)) {
                            for (Servico servico : atendimento.getServicosRealizados()) {
                                statement2.setInt(1, idAtendimento);
                                statement2.setInt(2, List.of(Servico.values()).indexOf(servico) + 1);
                                statement2.addBatch();
                            }
                            statement2.executeBatch();
                        }
                    }
                }
                conexao.commit();
            }

        } catch (Exception e) {
            ConexaoUtil.rollback(conexao, "Erro ao realizar rollback");

            throw new DatabaseException("Erro ao salvar atendimento", e);

        } finally {
            ConexaoUtil.fechar(conexao, "Erro ao fechar conexão com banco de dados: ");
        }
    }

    /**
     * Remove do sistema o atendimento referente ao ID informado.
     * <p>
     * Também remove os registros das relações entre o atendimento e o serviço realizado
     * e decrementa do total de atendimentos do cliente vinculado ao atendimento.
     *
     * @param id ID do atendimento a ser removido
     * @throws DatabaseException se ocorrer um erro ao acessar o banco de dados ou ao realizar rollback,
     * ou ao tentar fechar conexão com o banco de dados
     */
    public void remover(Integer id) {
        String query = "DELETE FROM atendimentos WHERE id = ?";

        String query2 = "DELETE FROM atendimentos_servicos WHERE id_atendimento = ?";

        String query3 = "UPDATE clientes " +
                        "SET total_atendimentos = total_atendimentos - 1 " +
                        "WHERE cpf = (SELECT cliente_cpf from atendimentos WHERE id = ?)";

        Connection conexao = null;
        try {
            conexao = Conexao.getConexao();
            conexao.setAutoCommit(false);
            try (PreparedStatement statement = conexao.prepareStatement(query);
                 PreparedStatement statement2 = conexao.prepareStatement(query2);
                 PreparedStatement statement3 = conexao.prepareStatement(query3)) {

                statement3.setInt(1, id);
                statement3.executeUpdate();

                statement2.setInt(1, id);
                statement2.executeUpdate();

                statement.setInt(1, id);
                statement.executeUpdate();
            }
            conexao.commit();

        } catch (Exception e) {
            ConexaoUtil.rollback(conexao, "Erro ao realizar rollback");

            throw new DatabaseException("Erro ao remover atendimento.", e);

        } finally {
            ConexaoUtil.fechar(conexao, "Erro ao fechar conexão com banco de dados: ");
        }
    }

    /**
     * Busca o atendimento referente ao ID informado.
     * <p>
     * A consulta utiliza JOIN para trazer os dados do cliente
     * em uma query unica, evitando múltiplas consultas ao banco.
     *
     * @param id ID do atendimento
     * @return o atendimento encontrado referente ao ID informado
     * @throws AtendimentoNaoEncontradoException se nenhum atendimento for encontrado com o ID informado
     * @throws DatabaseException se ocorrer um erro ao acessar o banco de dados
     */
    public Atendimento buscaPorId(Integer id) {
        String query = "SELECT cl.* FROM atendimentos as ate " +
                    "JOIN clientes as cl " +
                    "ON cl.cpf = ate.cliente_cpf " +
                    "WHERE ate.id = ?";

        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    String cpf = rs.getString("cpf");
                    String nome = rs.getString("nome");
                    int idade = LocalDate.now().getYear() - rs.getInt("nascimento");
                    Sexo sexo = Sexo.toSexo(rs.getString("sexo"));
                    return new Atendimento(id, new Cliente(nome, idade, cpf, sexo));

                } else {
                    throw new AtendimentoNaoEncontradoException("Nenhum atendimento encontrado com esse ID");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar atendimento.", e);
        }
    }

    /**
     * Verifica se no sistema contém o atendimento referente ao ID informado.
     *
     * @param id ID do atendimento
     * @return true se o atendimento for encontrado, false caso o contrário
     * @throws DatabaseException se ocorrer um erro ao acessar o banco de dados
     */
    public boolean containsAtendimento(Integer id) {
        String query = "SELECT 1 FROM atendimentos WHERE id = ?";
        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao verificar se atendimento está no banco.", e);
        }
    }

    /**
     * Lista todos os atendimentos registrados no sistema.
     * <p>
     * A consulta utiliza JOIN para trazer dados da relação entre
     * a tabela de atendimentos, clientes e serviços numa só
     * query, evitando múltiplas consultas ao banco.
     *
     * @return uma lista de todos os atendimentos registrados; Uma lista vazia se nenhum atendimento estiver registrado
     * @throws DatabaseException se ocorrer um erro ao acessar o banco de dados
     */
    public List<AtendimentoDTO> listaDeAtendimento() {
        String query = "SELECT ate.id, cl.nome, sv.nome as servico, ate.valor FROM atendimentos_servicos as ates " +
                        "JOIN atendimentos as ate " +
                        "ON ate.id = ates.id_atendimento " +
                        "JOIN servicos as sv " +
                        "ON sv.id = ates.id_servico " +
                        "JOIN clientes as cl " +
                        "ON cl.cpf = ate.cliente_cpf";

        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet rs = statement.executeQuery()) {

            Map<Integer, AtendimentoDTO> lista = new HashMap<>();
            while (rs.next()) {
                int id = rs.getInt("id");

                if (!lista.containsKey(id)) {
                    String nome = rs.getString("nome");
                    List<String> servico = new ArrayList<>();
                    BigDecimal total = rs.getBigDecimal("valor");

                    AtendimentoDTO atendimento = new AtendimentoDTO(id, nome, servico, total);
                    lista.put(id, atendimento);
                }
                lista.get(id).getServicos().add(rs.getString("servico"));
            }
            return new ArrayList<>(lista.values());

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao retornar lista de atendimentos.", e);
        }
    }
}
