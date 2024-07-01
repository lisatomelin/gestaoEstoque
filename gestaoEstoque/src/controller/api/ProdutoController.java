package controller.api;

import java.util.List;

import model.entidade.Produto;
import model.exception.ProdutoInexistenteException;

public interface ProdutoController {
	
	public void salvar(Produto produto);
	
	public void excluir(int id) throws ProdutoInexistenteException;
	
	public List<Produto> listarTodos();
	
	List<Produto> listarProdutosComPoucoEstoque();
	
	public Produto buscarPorId(int id);

}
