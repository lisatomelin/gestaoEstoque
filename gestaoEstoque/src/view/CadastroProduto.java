package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.api.ProdutoController;
import controller.impl.ProdutoControllerImpl;
import model.entidade.Produto;
import model.exception.ProdutoInexistenteException;

public class CadastroProduto extends JFrame {

	private JPanel panel;
	private JTextField textFieldId;
	private JTextField textFieldNome;
	private JTextField textFieldQtdEstoque;

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
		button.setPreferredSize(new Dimension(100, 40));
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
