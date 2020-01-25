package br.com.projetoJSP.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.projetoJSP.bean.CategoriaBean;
import br.com.projetoJSP.bean.ProdutoBean;
import br.com.projetoJSP.connection.SingleConnection;

public class ProdutoDAO {
	
	private Connection connection;
	
	public ProdutoDAO() {
		connection = SingleConnection.getConnection();
	}
	
	public void salvar(ProdutoBean produto) {
		try {
			String sql = "INSERT INTO produto (nome, valor, quantidade, categoria_id) VALUES (?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, produto.getNome());
			statement.setDouble(2, produto.getValor());
			statement.setDouble(3, produto.getQuantidade());
			statement.setLong(4, produto.getCategoria());
			statement.execute();
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {				
				e1.printStackTrace();
			}			
		}
	}
	
	public List<ProdutoBean> listar() {
		List<ProdutoBean> produtos = new ArrayList<>();
		try {			
			String sql = "SELECT * FROM produto";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				ProdutoBean produto = new ProdutoBean();
				produto.setId(result.getLong("id"));
				produto.setNome(result.getString("nome"));
				produto.setValor(result.getDouble("valor"));
				produto.setQuantidade(result.getDouble("quantidade"));
				produto.setCategoria(result.getLong("categoria_id"));
				produtos.add(produto);			
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return produtos;
	}
	
	public List<CategoriaBean> listarCategorias() {
		List<CategoriaBean> categorias = new ArrayList<>();
		try {
			String sql = "SELECT * FROM categoria";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				CategoriaBean categoria = new CategoriaBean();
				categoria.setId(result.getLong("id"));
				categoria.setNome(result.getString("nome"));				
				categorias.add(categoria);			
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categorias;
	}
	
	public void deletar(String id) {
		try {
			String sql = "DELETE FROM produto WHERE id = '" + id + "'";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public ProdutoBean consultar(String id) {
		ProdutoBean produto = null;
		try {
			String sql = "SELECT * FROM produto WHERE id = '" + id + "'";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			if (result.next()) {				
				produto = new ProdutoBean();
				produto.setId(result.getLong("id"));
				produto.setNome(result.getString("nome"));
				produto.setValor(result.getDouble("valor"));
				produto.setQuantidade(result.getDouble("quantidade"));
				produto.setCategoria(result.getLong("categoria_id"));
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return produto;
	}	
	
	public boolean validarNomeInsert(String nome) {
		try {
			String sql = "SELECT COUNT(1) AS qtde FROM produto WHERE nome = '" + nome + "'";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			if (result.next()) {				
				return result.getInt("qtde") <= 0;
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean validarNomeUpdate(String nome, String id) {
		try {
			String sql = "SELECT COUNT(1) AS qtde FROM produto WHERE nome = '" + nome + "' AND id <> '" + id + "'";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			if (result.next()) {				
				return result.getInt("qtde") <= 0;
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void atualizar(ProdutoBean produto) {
		try {
			String sql = "UPDATE produto SET nome = ?, valor = ?, quantidade = ?, categoria_id = ? WHERE id = " + produto.getId();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, produto.getNome());
			statement.setDouble(2, produto.getValor());
			statement.setDouble(3, produto.getQuantidade());
			statement.setLong(4, produto.getCategoria());
			statement.executeUpdate();
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}		
	}
	
}
