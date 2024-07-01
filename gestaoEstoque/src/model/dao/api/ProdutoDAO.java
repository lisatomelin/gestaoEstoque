package model.dao.api;

import model.entidade.Produto;

public interface ProdutoDAO extends DAO<Produto> {
	
	public Produto buscarPorId(int id);

}
