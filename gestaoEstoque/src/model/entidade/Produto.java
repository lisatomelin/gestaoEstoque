package model.entidade;

import java.io.Serializable;

public class Produto implements Serializable {
	
	private int idProduto;
	
	private String nome;
	
	private int qtdEstoque;
	
	public Produto() {}

	public int getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getQtdEstoque() {
		return qtdEstoque;
	}

	public void setQtdEstoque(int qtdEstoque) {
		this.qtdEstoque = qtdEstoque;
	}
	
	@Override
	public String toString() {
		return this.idProduto + " - " + this.nome + " - " + this.qtdEstoque;
		
	}
	
	

}
