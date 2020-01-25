package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanJspLogin;

@WebServlet("/LoginServletIntroducao")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println(request.getParameter("login"));
		System.out.println(request.getParameter("senha"));

		BeanJspLogin bean = new BeanJspLogin();

		String login = request.getParameter("login");
		String senha = request.getParameter("senha");

		if (bean.validaLoginSenha(login, senha)) {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("acessoliberado.jsp");
			requestDispatcher.forward(request, response);
		} else {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("acessonegado.jsp");
			requestDispatcher.forward(request, response);
		}

	}

}
