package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Principal.ArquivoUtils;
import controller.api.ProdutoController;
import controller.impl.ProdutoControllerImpl;
import model.entidade.Produto;
import model.exception.ProdutoInexistenteException;


public class CadastroProduto extends JFrame {

	private JPanel panel;
	private JTextField textFieldId;
	private JTextField textFieldNome;
	private JTextField textFieldQtdEstoque;
	private DefaultListModel<Produto> listProdutoModel;
	private JList listProdutos;

	private ProdutoController produtoController;

	public CadastroProduto() {
		setTitle("Cadastro de produtos");
		setLayout(new FlowLayout());

		produtoController = new ProdutoControllerImpl();

		panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.setPreferredSize(new Dimension(500, 800));
		add(panel);

		criarTextFieldId("Código");
		criarTextFieldNome("Nome");
		criarTextFieldQtdEstoque("Quantidade de Estoque");

		criarBotao("Salvar", new ButtonSalvarHandler());
		criarBotao("Excluir", new ButtonExcluirHandler());
		criarBotao("Buscar Por Id", new ButtonBuscarPorIdHandler());
		criarBotao("Listar Todos", new ButtonListarHandler());
		criarBotao("Salvar em TXT", new ButtonSalvarArquivoTextoHandler());
		criarBotao("Salvar em Binário", new ButtonSalvarArquivoBinarioHandler());
		criarBotao("Listar TXT", new ButtonCarregarArquivoTextoHandler());
		criarBotao("Listar Binário", new ButtonCarregarArquivoBinarioHandler());

		criarList();

		setSize(500, 800);
		setPreferredSize(new Dimension(500, 800));
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	private void criarLabel(String texto) {
		JLabel label = new JLabel(texto);
		label.setPreferredSize(new Dimension(400, 40));
		panel.add(label);
	}

	private void criarTextFieldId(String label) {
		this.criarLabel(label);
		textFieldId = new JTextField();
		textFieldId.setPreferredSize(new Dimension(400, 40));
		panel.add(textFieldId);
	}

	private void criarTextFieldNome(String label) {
		this.criarLabel(label);
		textFieldNome = new JTextField();
		textFieldNome.setPreferredSize(new Dimension(400, 40));
		panel.add(textFieldNome);
	}

	private void criarTextFieldQtdEstoque(String label) {
		this.criarLabel(label);
		textFieldQtdEstoque = new JTextField();
		textFieldQtdEstoque.setPreferredSize(new Dimension(400, 40));
		panel.add(textFieldQtdEstoque);
	}

	private void criarBotao(String label, ActionListener handler) {
		JButton button = new JButton(label);
		button.addActionListener(handler);
		button.setPreferredSize(new Dimension(200, 40));
		panel.add(button);

	}

	private Produto criarObjetoProduto() {
		Produto produto = new Produto();
		if (!textFieldId.getText().isEmpty()) {
			produto.setIdProduto(Integer.parseInt(textFieldId.getText()));
		}
		produto.setNome(textFieldNome.getText());
		produto.setQtdEstoque(Integer.parseInt(textFieldQtdEstoque.getText()));
		return produto;
	}

	private void criarList() {
		listProdutoModel = new DefaultListModel<Produto>();
		listProdutos = new JList<Produto>(listProdutoModel);
		listProdutos.setPreferredSize(new Dimension(400, 300));
		JScrollPane scrollPane = new JScrollPane(listProdutos);
		scrollPane.setPreferredSize(new Dimension(400, 300));
		panel.add(scrollPane);
	}

	private void limparCampos() {
		textFieldId.setText("");
		textFieldNome.setText("");
		textFieldQtdEstoque.setText("");
	}

	private class ButtonSalvarHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Produto produto = criarObjetoProduto();
			produtoController.salvar(produto);
			limparCampos();
			JOptionPane.showMessageDialog(null, "Produto Cadastrado com sucesso");

		}

	}

	private class ButtonSalvarArquivoTextoHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Produto produto = criarObjetoProduto();
			ArquivoUtils.salvarTXT(produto);
			JOptionPane.showMessageDialog(null, "Produto salvo no arquivo TXT!");
		}
	}

	private class ButtonSalvarArquivoBinarioHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Produto produto = criarObjetoProduto();
			ArquivoUtils.salvarBinario(produto);
			JOptionPane.showMessageDialog(null, "Produto salvo no arquivo Binário!");
		}
	}
	
	private class ButtonCarregarArquivoTextoHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			listProdutoModel.clear();
			List<Produto> produtos = ArquivoUtils.carregarTXT();
			listProdutoModel.addAll(produtos);
		}
	}

	private class ButtonCarregarArquivoBinarioHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			listProdutoModel.clear();
			Produto produto = ArquivoUtils.carregarBinario();
			listProdutoModel.addElement(produto);
		}
	}

	private class ButtonExcluirHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				produtoController.excluir(Integer.parseInt(textFieldId.getText()));
				limparCampos();
				JOptionPane.showMessageDialog(null, "Produto removido com sucesso!");
			} catch (ProdutoInexistenteException exception) {
				JOptionPane.showMessageDialog(null, exception.getMessage());
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "É obrigatório informar um valor numérico no id para exclusão");
			}

		}

	}

	private class ButtonListarHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Listagem();

		}

	}

	private class ButtonBuscarPorIdHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Produto produto = produtoController.buscarPorId(Integer.parseInt(textFieldId.getText()));
			textFieldId.setText(String.valueOf(produto.getIdProduto()));
			textFieldNome.setText(produto.getNome());
			textFieldQtdEstoque.setText(String.valueOf(produto.getQtdEstoque()));

		}

	}

}
