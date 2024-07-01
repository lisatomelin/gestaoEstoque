package controller.impl;

import java.util.List;

import controller.api.ProdutoController;
import model.dao.api.ProdutoDAO;
import model.entidade.Produto;
import model.exception.ProdutoInexistenteException;
import model.service.api.ProdutoService;
import model.service.impl.ProdutoServiceImpl;

public class ProdutoControllerImpl implements ProdutoController {
	
	private ProdutoDAO produtoDAO;
	
	private ProdutoService produtoService;
	
	public ProdutoControllerImpl() {
		this.produtoService = new ProdutoServiceImpl();
	}

	@Override
	public void salvar(Produto produto) {
		this.produtoService.salvar(produto);
		
	}

	@Override
	public void excluir(int id) throws ProdutoInexistenteException {
		this.produtoService.excluir(id);
		
	}

	@Override
	public List<Produto> listarTodos() {
		return this.produtoService.listarTodos();
	}

	@Override
	public Produto buscarPorId(int id) {
		return this.produtoService.buscarPorId(id);
	}
	
	  @Override
	    public List<Produto> listarProdutosComPoucoEstoque() {
		  List<Produto> produtos = produtoService.listarProdutosComPoucoEstoque();
		    if (produtos.isEmpty()) {
		        return null; // Ou você pode retornar uma lista vazia: return new ArrayList<>();
		    }
		    return produtos;
	    }

}
