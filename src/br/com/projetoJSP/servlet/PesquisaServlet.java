package br.com.projetoJSP.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.projetoJSP.DAO.UsuarioDAO;
import br.com.projetoJSP.bean.UsuarioBean;

@WebServlet("/pesquisaServlet")
public class PesquisaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private UsuarioDAO usuarioDAO = new UsuarioDAO();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String descricaoPesquisa = request.getParameter("descricaoConsulta");

		if (descricaoPesquisa != null && !descricaoPesquisa.trim().isEmpty()) {
			listarTodosUsuarios(request, response, usuarioDAO.listarPorNome(descricaoPesquisa));
			return;
		}
		listarTodosUsuarios(request, response, usuarioDAO.listarTodos());
	}

	private void listarTodosUsuarios(HttpServletRequest request, HttpServletResponse response,
			List<UsuarioBean> usuarios) throws ServletException, IOException {

		RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
		request.setAttribute("usuarios", usuarios);
		view.forward(request, response);
	}

}
