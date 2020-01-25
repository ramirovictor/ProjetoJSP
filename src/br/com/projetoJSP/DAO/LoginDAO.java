package br.com.projetoJSP.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.projetoJSP.connection.SingleConnection;

public class LoginDAO {
	
	private Connection connection;
	
	public LoginDAO() {
		connection = SingleConnection.getConnection();
	}
	
	public boolean validarLogin(String login, String senha) throws Exception {		
		String sql = "SELECT * FROM usuario WHERE login = '" + login + "' AND senha = '" + senha + "'";
		try (PreparedStatement statement = connection.prepareStatement(sql);) {
			try (ResultSet result = statement.executeQuery()) {
				return result.next() ? Boolean.TRUE : Boolean.FALSE;
			}			
		}			
	}
	
	/*public boolean validarLogin(String login, String senha) throws Exception {		
		String sql = "SELECT * FROM usuario WHERE login = '" + login + "' AND senha = '" + senha + "'";				
		return connection.prepareStatement(sql).executeQuery().next() ? true : false;
	}*/
	
	/*public boolean validarLogin(String login, String senha) throws Exception { 
		
		String sql = "SELECT * FROM usuario WHERE login = '" + login + "' AND senha = '" + senha + "'";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet result = statement.executeQuery();
		
		if (result.next()) {
			return true;
		} else {
			return false;
		}			
	}*/
}
