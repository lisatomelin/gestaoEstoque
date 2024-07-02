package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Principal.ArquivoUtils;
import controller.api.ProdutoController;
import controller.impl.ProdutoControllerImpl;
import model.entidade.Produto;

public class Listagem extends JFrame {

	private JPanel panel;
	private JTable tableProdutos;	
	private DefaultTableModel tableModel;
	
	private ProdutoController produtoController;

	public Listagem() {
		setTitle("Listagem de Produtos");
		setLayout(new FlowLayout());

		produtoController = new ProdutoControllerImpl();

		this.panel = new JPanel();
		this.panel.setLayout(new FlowLayout());
		this.panel.setPreferredSize(new Dimension(600, 400));
		add(this.panel);

		criarBotao("Voltar", new ButtonVoltarHandler());
		criarBotao("Listar Produtos com Menos de 10", new ButtonProdutosComPoucoEstoqueHandler());
		
		criarTabela();

		List<Produto> produtosCarregados = produtoController.listarTodos();
		carregarDadosNaTabela(produtosCarregados);

		setSize(600, 400);
		setPreferredSize(new Dimension(600, 400));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void criarBotao(String label, ActionListener handler) {
		JButton button = new JButton(label);
		button.addActionListener(handler);
		button.setPreferredSize(new Dimension(300, 40));
		this.panel.add(button);
	}

	private void criarTabela() {
		String[] colunas = { "ID", "Nome", "Quantidade" };
		tableModel = new DefaultTableModel(colunas, 0);
		tableProdutos = new JTable(tableModel);
		tableProdutos.setPreferredSize(new Dimension(500, 300));
		JScrollPane scrollPane = new JScrollPane(tableProdutos);
		scrollPane.setPreferredSize(new Dimension(500, 300));
		panel.add(scrollPane);
	}

	private void carregarDadosNaTabela(List<Produto> produtos) {
		for (Produto produto : produtos) {
			Object[] rowData = { produto.getIdProduto(), produto.getNome(), produto.getQtdEstoque() };
			tableModel.addRow(rowData);
		}
	}

	
	private class ButtonVoltarHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
		}
	}


	private class ButtonProdutosComPoucoEstoqueHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			List<Produto> produtosComPoucoEstoque = produtoController.listarProdutosComPoucoEstoque();
			if (produtosComPoucoEstoque == null || produtosComPoucoEstoque.isEmpty()) {
				JOptionPane.showMessageDialog(Listagem.this, "Não há produtos com menos de 10 unidades em estoque.",
						"Aviso", JOptionPane.INFORMATION_MESSAGE);
			} else {
				exibirTabelaProdutosComPoucoEstoque(produtosComPoucoEstoque);
			}
		}
	}

	private void exibirTabelaProdutosComPoucoEstoque(List<Produto> produtos) {
		JFrame frameProdutosComPoucoEstoque = new JFrame("Produtos com Menos de 10 Itens em Estoque");
		frameProdutosComPoucoEstoque.setLayout(new FlowLayout());

		DefaultTableModel tableModelProdutosComPoucoEstoque = new DefaultTableModel(
				new String[] { "ID", "Nome", "Quantidade" }, 0);
		JTable tableProdutosComPoucoEstoque = new JTable(tableModelProdutosComPoucoEstoque);

		for (Produto produto : produtos) {
			Object[] rowData = { produto.getIdProduto(), produto.getNome(), produto.getQtdEstoque() };
			tableModelProdutosComPoucoEstoque.addRow(rowData);
		}

		JScrollPane scrollPane = new JScrollPane(tableProdutosComPoucoEstoque);
		scrollPane.setPreferredSize(new Dimension(500, 300));
		frameProdutosComPoucoEstoque.add(scrollPane);

		frameProdutosComPoucoEstoque.setSize(600, 400);
		frameProdutosComPoucoEstoque.setLocationRelativeTo(null);
		frameProdutosComPoucoEstoque.setVisible(true);
	}

}
