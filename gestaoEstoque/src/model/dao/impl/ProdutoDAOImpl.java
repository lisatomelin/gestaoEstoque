package model.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dao.api.ProdutoDAO;
import model.entidade.Produto;

public class ProdutoDAOImpl implements ProdutoDAO {

	private static final String INSERT = "INSERT INTO produto (nome, qtdEstoque) values (?, ?)";
	private static final String UPDATE = "UPDATE produto SET qtdEstoque = ? WHERE idproduto = ?";
	private static final String EXCLUIR = "DELETE FROM  produto WHERE idproduto = ?";
	private static final String LISTAR_TODOS = "SELECT * FROM  produto";
	private static final String LISTAR_PRODUTOS_COM_POUCO_ESTOQUE = "SELECT * FROM Produto WHERE qtdEstoque < 10";
	private static final String BUSCAR_POR_ID = "SELECT * FROM produto WHERE idproduto = ?";

	@Override
	public void salvar(Produto produto) {
		if (produto != null && produto.getIdProduto() == 0) {
			this.salvarProduto(produto);
		} else {
			this.alterarProduto(produto);
		}

	}

	private void alterarProduto(Produto produto) {
		try (PreparedStatement ps = Conexao.getInstance().getConnection().prepareStatement(UPDATE)) {
			ps.setInt(1, produto.getQtdEstoque());
			ps.setInt(2, produto.getIdProduto());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void salvarProduto(Produto produto) {
		try (PreparedStatement ps = Conexao.getInstance().getConnection().prepareStatement(INSERT)) {
			ps.setString(1, produto.getNome());
			ps.setInt(2, produto.getQtdEstoque());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void excluir(int id) {
		try (PreparedStatement ps = Conexao.getInstance().getConnection().prepareStatement(EXCLUIR)) {
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Produto> listarTodos() {
		List<Produto> produtos = new ArrayList<Produto>();
		try (PreparedStatement ps = Conexao.getInstance().getConnection().prepareStatement(LISTAR_TODOS);
				ResultSet resultSet = ps.executeQuery();) {
			while (resultSet.next()) {
				Produto produto = new Produto();
				produto.setIdProduto(resultSet.getInt("idProduto"));
				produto.setNome(resultSet.getString("nome"));
				produto.setQtdEstoque(resultSet.getInt("qtdEstoque"));
				produtos.add(produto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return produtos;

	}
	
	 @Override
	    public List<Produto> listarProdutosComPoucoEstoque() {
	        List<Produto> produtos = new ArrayList<>();
	        try (PreparedStatement ps = Conexao.getInstance().getConnection().prepareStatement(LISTAR_PRODUTOS_COM_POUCO_ESTOQUE);
	             ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                Produto produto = new Produto();
	                produto.setIdProduto(rs.getInt("idProduto"));
	                produto.setNome(rs.getString("nome"));
	                produto.setQtdEstoque(rs.getInt("qtdEstoque"));
	                produtos.add(produto);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return produtos;
	    }

	@Override
	public Produto buscarPorId(int id) {
		Produto produto = null;
		try (PreparedStatement ps = Conexao.getInstance().getConnection().prepareStatement(BUSCAR_POR_ID);

		) {
			ps.setInt(1, id);

			try (ResultSet resultSet = ps.executeQuery();) {
				while (resultSet.next()) {
					produto = new Produto();
					produto.setIdProduto(resultSet.getInt("idProduto"));
					produto.setNome(resultSet.getString("nome"));
					produto.setQtdEstoque(resultSet.getInt("qtdEstoque"));

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return produto;

	}

}
