package model.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Conexao {
	
	private static Conexao conexaoSingleton;
	
	private Connection connection;
	
	private Conexao() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			this.connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/gestaoEstoque", "root", "secret");
		} catch (SQLException sqlE) {
			JOptionPane.showInputDialog(null, "Erro ao conectar com o banco de dados");
			sqlE.printStackTrace();
		} catch (ClassNotFoundException classE) {
			JOptionPane.showMessageDialog(null, "Não foi possível carregar a classe de conexão!");
		}
		
	}
	
	public static Conexao getInstance() {
		if (conexaoSingleton == null) {
			conexaoSingleton = new Conexao();
		}
		return conexaoSingleton;
	}

	public Connection getConnection() {
		return connection;
	}
	
	
}
