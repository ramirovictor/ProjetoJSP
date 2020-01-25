package br.com.projetoJSP.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.projetoJSP.bean.TelefoneBean;
import br.com.projetoJSP.connection.SingleConnection;

public class TelefoneDAO {
	
	private Connection connection;
	
	public TelefoneDAO() {
		connection = SingleConnection.getConnection();
	}
	
	public void salvar(TelefoneBean telefone) {
		try {
			String sql = "INSERT INTO telefone (numero, tipo, usuario_id) VALUES (?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, telefone.getNumero());
			statement.setString(2, telefone.getTipo());
			statement.setLong(3, telefone.getUsuario());
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
	
	public List<TelefoneBean> listar() {
		List<TelefoneBean> telefones = new ArrayList<>();
		try {			
			String sql = "SELECT * FROM telefone";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				TelefoneBean telefone = new TelefoneBean();
				telefone.setId(result.getLong("id"));
				telefone.setNumero(result.getString("numero"));
				telefone.setTipo(result.getString("tipo"));
				telefone.setUsuario(result.getLong("usuario_id"));
				telefones.add(telefone);			
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return telefones;
	}
	
	public List<TelefoneBean> listarTelefonesUsuario(Long usuario) {
		List<TelefoneBean> telefones = new ArrayList<>();
		try {			
			String sql = "SELECT * FROM telefone WHERE usuario_id = " + usuario;
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			while(result.next()) {
				TelefoneBean telefone = new TelefoneBean();
				telefone.setId(result.getLong("id"));
				telefone.setNumero(result.getString("numero"));
				telefone.setTipo(result.getString("tipo"));
				telefone.setUsuario(result.getLong("usuario_id"));
				telefones.add(telefone);			
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return telefones;
	}
	
	public void deletar(String id) {
		try {
			String sql = "DELETE FROM telefone WHERE id = '" + id + "'";
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
	
	public TelefoneBean consultar(String id) {
		TelefoneBean telefone = null;
		try {
			String sql = "SELECT * FROM telefone WHERE id = '" + id + "'";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();
			if (result.next()) {				
				telefone = new TelefoneBean();
				telefone.setId(result.getLong("id"));
				telefone.setNumero(result.getString("numero"));
				telefone.setTipo(result.getString("tipo"));
				telefone.setUsuario(result.getLong("usuario_id"));
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return telefone;
	}	
	
	public void atualizar(TelefoneBean telefone) {
		try {
			String sql = "UPDATE telefone SET numero = ?, tipo = ?, usuario_id = ? WHERE id = " + telefone.getId();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, telefone.getNumero());
			statement.setString(2, telefone.getTipo());
			statement.setLong(3, telefone.getUsuario());
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
