package beans;

public class BeanJspLogin {

	private String login;

	private String senha;
	
	public boolean validaLoginSenha(String login, String senha) {
		if (login.equals("admin") && senha.equals("admin")) {
			return true;
		} else {
			return false;
		}
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
