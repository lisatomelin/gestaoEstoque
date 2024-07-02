package Principal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.entidade.Produto;

public class ArquivoUtils {

	private static final String NOME_ARQUIVO_TXT = "produto.txt";
	private static final String NOME_ARQUIVO_BINARIO = "produto.binario";

	public static void salvarTXT(Produto produto) {
		FileWriter fileWriter = null;
		try {
			File file = new File(NOME_ARQUIVO_TXT);
			fileWriter = new FileWriter(file, true);
			String valorParaSalvar = produto.getNome() + "##" + produto.getQtdEstoque() + "\n";
			fileWriter.write(valorParaSalvar);
			JOptionPane.showMessageDialog(null, "O Produto foi cadastrado com sucesso!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao salvar o produto!");
		} finally {
			try {
				if (fileWriter != null) {
					fileWriter.close();
				}
			} catch (IOException e2) {
				JOptionPane.showMessageDialog(null, "Erro ao fechar o arquivo após salvar o produto!");

			}
		}
	}

	public static List<Produto> carregarTXT() {
		List<Produto> produtos = new ArrayList<Produto>();
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			File file = new File(NOME_ARQUIVO_TXT);
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			String linha = bufferedReader.readLine();
			while (linha != null) {
				Produto produto = extrairObjetoDoArquivo(linha);
				produtos.add(produto);
				linha = bufferedReader.readLine();
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao carregar o arquivo!");
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (fileReader != null) {
					fileReader.close();
				}
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, "Erro ao fechar o arquivo!");
			}
		}

		return produtos;
	}

	private static Produto extrairObjetoDoArquivo(String linha) {
		Produto produto = new Produto();
		String[] split = linha.split("##");
		produto.setNome(split[0]);
		produto.setQtdEstoque(Integer.parseInt(split[1]));
		return produto;
	}

	public static void salvarBinario(Produto produto) {
		OutputStream outputStream = null;
		ObjectOutputStream objectStream = null;
		try {
			File file = new File(NOME_ARQUIVO_BINARIO);
			outputStream = new FileOutputStream(file);
			objectStream = new ObjectOutputStream(outputStream);
			objectStream.writeObject(produto);
		} catch (IOException e) {
			JOptionPane.showConfirmDialog(null, "Erro ao salvar o veículo em arquivo binário!");
		} finally {
			try {
				if (objectStream != null) {
					objectStream.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (Exception e2) {
				JOptionPane.showConfirmDialog(null, "Erro ao fechar os arquivos!");
			}
		}

	}

	public static Produto carregarBinario() {
			Produto produto = new Produto();
			InputStream inputStream = null;
			ObjectInputStream objectStream = null;
			try {
				File file = new File(NOME_ARQUIVO_BINARIO);
				inputStream = new FileInputStream(file);
				objectStream = new ObjectInputStream(inputStream);
				produto = (Produto) objectStream.readObject();
			} catch (IOException e) {
				JOptionPane.showConfirmDialog(null, "erro ao carregar o veículo do arquivo binário!");
			} catch (ClassNotFoundException cnfe) {
				JOptionPane.showConfirmDialog(null, "Não foi possível converter o valor do arquivo para binário!");
			} finally {
				try {
					if (objectStream != null) {
						objectStream.close();
					}
					if (inputStream != null) {
						inputStream.close();
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro ao fechar os arquivos!");
					
				}
			}
			
			return produto;
		
	}

}
