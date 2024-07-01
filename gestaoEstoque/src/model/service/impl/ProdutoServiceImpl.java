package model.service.impl;

import java.util.List;

import model.dao.api.ProdutoDAO;
import model.dao.impl.ProdutoDAOImpl;
import model.entidade.Produto;
import model.exception.ProdutoInexistenteException;
import model.service.api.ProdutoService;

public class ProdutoServiceImpl implements ProdutoService {
	
	private ProdutoDAO produtoDAO;
	

	public ProdutoServiceImpl() {
		
		this.produtoDAO = new ProdutoDAOImpl();
	}

	@Override
	public void salvar(Produto produto) {
		this.produtoDAO.salvar(produto);
		
	}

	@Override
	public void excluir(int id) {
		Produto produtoParaExclusao = this.buscarPorId(id);
		if (produtoParaExclusao == null) {
			throw new ProdutoInexistenteException("Não foi possível obter o produto que você deseja remover!");
		}
		this.produtoDAO.excluir(id);
		
	}

	@Override
	public List<Produto> listarTodos() {
		return this.produtoDAO.listarTodos();
	}

	@Override
	public Produto buscarPorId(int id) {
		return this.produtoDAO.buscarPorId(id);
	}
	
	@Override
	public List<Produto> listarProdutosComPoucoEstoque() {
		return this.produtoDAO.listarProdutosComPoucoEstoque();
	}


}
