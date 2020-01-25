package br.com.projetoJSP.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.projetoJSP.DAO.TelefoneDAO;
import br.com.projetoJSP.DAO.UsuarioDAO;
import br.com.projetoJSP.bean.TelefoneBean;
import br.com.projetoJSP.bean.UsuarioBean;

@WebServlet("/salvarTelefone")
public class TelefoneServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;      
	
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	
	private TelefoneDAO telefoneDAO = new TelefoneDAO();
    
    public TelefoneServlet() { }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String user = request.getParameter("user");	
		String acao = request.getParameter("acao");		
		
		try {
			UsuarioBean usuario = acao.equalsIgnoreCase("deletarFone") 
				? (UsuarioBean) request.getSession().getAttribute("userEscolhido") 
				: usuarioDAO.consultarPorId(user);
			redirecionarUsuario(acao, usuario, request, response);	
		} catch (NullPointerException e) {
			listarTodosUsuarios(request, response);
		}	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String numero = request.getParameter("numero");
		String tipo = request.getParameter("tipo");	
		UsuarioBean usuario = (UsuarioBean) request.getSession().getAttribute("userEscolhido");		
		String acao = request.getParameter("acao");
		
		if (acao != null && acao.equalsIgnoreCase("voltar")) {
			listarTodosUsuarios(request, response);
			return;
		}
		
		if (numero == null || numero.isEmpty()) {
			request.setAttribute("msg", "O campo telefone é de preenchimento obrigatório!"); 
			listarTodosTelefonesUsuario(usuario, request, response);
			return;
		}
		
		TelefoneBean telefone = criarTelefone(numero, tipo, usuario.getId());
		
		telefoneDAO.salvar(telefone);
		request.setAttribute("msgSucesso", "Telefone registrado com sucesso!!!"); 		
		
		listarTodosTelefonesUsuario(usuario, request, response);					
	}
	
	private void listarTodosUsuarios(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {		
		RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
		request.setAttribute("usuarios", usuarioDAO.listarTodos());
		view.forward(request, response);		
	}
	
	private TelefoneBean criarTelefone(String numero, String tipo, Long usuario) {
		TelefoneBean telefone = new TelefoneBean();		
		telefone.setNumero(numero);
		telefone.setTipo(tipo);
		telefone.setUsuario(usuario);
		return telefone;
	}
	
	private void redirecionarUsuario(String acao, UsuarioBean usuario, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {	
		
		if (acao != null) {
			
			if (acao.equalsIgnoreCase("addFone")) {
				listarTodosTelefonesUsuario(usuario, request, response);
			} 
			
			if (acao.equalsIgnoreCase("deletarFone")) {
				deletarTelefone(acao, usuario, request, response);
			} 
		}
		
		listarTodosTelefonesUsuario(usuario, request, response);			
	}
	
	private void deletarTelefone(String acao, UsuarioBean usuario, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {	
		String foneId = request.getParameter("foneId");
		telefoneDAO.deletar(foneId);
		request.setAttribute("msgSucesso", "Telefone excluído com sucesso!"); 
		listarTodosTelefonesUsuario(usuario, request, response);
	}	
	
	private void listarTodosTelefonesUsuario(UsuarioBean usuario, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {		
		RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");
		request.getSession().setAttribute("userEscolhido", usuario);	
		request.setAttribute("userEscolhido", usuario);
		request.setAttribute("telefones", telefoneDAO.listarTelefonesUsuario(usuario.getId()));
		view.forward(request, response);		
	}	

}
