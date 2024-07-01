package model.dao.api;

import java.util.List;

import model.entidade.Produto;

public interface DAO<T> {
	
	public void salvar(T t);
	
	public void excluir(int id);
	
	public List<T> listarTodos();
	
	List<Produto> listarProdutosComPoucoEstoque();

}
