package br.com.projetoJSP.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.projetoJSP.DAO.ProdutoDAO;
import br.com.projetoJSP.bean.ProdutoBean;

@WebServlet("/salvarProduto")
public class ProdutoServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;    
	
	private ProdutoDAO produtoDAO = new ProdutoDAO();	
  
    public ProdutoServlet() { }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String acao = request.getParameter("acao");
		String prod = request.getParameter("prod");
		
		redirecionarUsuario(acao, prod, request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
		String acao = request.getParameter("acao");
		
		if (acao != null && acao.equalsIgnoreCase("reset")) {
			listarTodosProdutos(request, response);		
		} else {			
			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String quantidade = request.getParameter("quantidade");
			String valor = request.getParameter("valor");
			String categoria = request.getParameter("categoria");
			
			ProdutoBean produto = criarProduto(id, nome, quantidade, valor, categoria);
			
			validarCamposFormulario(nome, quantidade, valor, request, response, produto);			
			
			boolean nomeExiste = false;			
			
			nomeExiste = validarNomeInsert(id, nome);			
			
			request.setAttribute("msg", msg(nomeExiste, produto.getNome()));
			request.setAttribute("prod", produto);			
			
			if (id == null || id.isEmpty() && produtoDAO.validarNomeInsert(nome)) {
				produtoDAO.salvar(produto);
				request.setAttribute("prod", null);
			} else if (id != null && !id.isEmpty()) {
				
				nomeExiste = validarNomeUpdate(id, nome);	
				
				boolean atualizou = false;
				
				if (!nomeExiste) {
					atualizou = true;
					produtoDAO.atualizar(produto);						
				}
				
				if (!atualizou) { 
					request.setAttribute("msg", msg(nomeExiste, produto.getNome()));
					request.setAttribute("prod", produto);
				} else {
					request.setAttribute("prod", null);
				}
			}	
			
			listarTodosProdutos(request, response);
		}	
	}
	
	private boolean validarNomeInsert(String id, String nome) {
		if(id == null || id.isEmpty() && !produtoDAO.validarNomeInsert(nome)) {
			return true;
		} 
		return false;
	}
	
	private boolean validarNomeUpdate(String id, String nome) {
		if (!produtoDAO.validarNomeUpdate(nome, id)) {
			return true;
		} 
		return false;
	}
	
	private ProdutoBean criarProduto(String id, String nome, String quantidade,
			String valor, String categoria) {
		ProdutoBean produto = new ProdutoBean();
		produto.setId(!id.isEmpty() ? Long.parseLong(id) : null);
		produto.setNome(nome);
		if((quantidade != null && !quantidade.isEmpty()) && (valor != null && !valor.isEmpty())) {
			String valorParse = valor.replace("R$", "").replaceAll("\\.", "").replaceAll("\\,", ".");
			produto.setQuantidade(Double.parseDouble(quantidade));
			produto.setValor(Double.parseDouble(valorParse));
		}
		produto.setCategoria(Long.parseLong(categoria));
		return produto;
	}
	
	private String msg(boolean nome, String nomeProduto) {
		
		String msg = "";
		
		if (nome) {
			msg = "Já existe um produto cadastrado com o nome " + nomeProduto + " !";
		}	
			
		return msg;
	}
	
	private void redirecionarUsuario(String acao, String prod, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {		
		
		if (acao != null) {
			
			if (acao.equalsIgnoreCase("deletar")) {
				deletarProduto(prod, request, response);
			} 
			
			if (acao.equalsIgnoreCase("editar")) {
				editarProduto(prod, request, response);
			} 
			
			if (acao.equalsIgnoreCase("listarTodos")) {
				listarTodosProdutos(request, response);
			}			
		}
		listarTodosProdutos(request, response);
	}
	
	private void editarProduto(String prod, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {		
		ProdutoBean produto = produtoDAO.consultar(prod);			
		RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");
		request.setAttribute("prod", produto);
		request.setAttribute("produtos", produtoDAO.listar());
		request.setAttribute("categorias", produtoDAO.listarCategorias());
		view.forward(request, response);		
	}
	
	private void listarTodosProdutos(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {		
		RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");
		request.setAttribute("produtos", produtoDAO.listar());
		request.setAttribute("categorias", produtoDAO.listarCategorias());
		view.forward(request, response);	
	}
	
	private void deletarProduto(String prod, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {		
		produtoDAO.deletar(prod);				
		RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");
		request.setAttribute("produtos", produtoDAO.listar());
		request.setAttribute("categorias", produtoDAO.listarCategorias());
		view.forward(request, response);		
	}
	
	private void validarCamposFormulario(String nome, String quantidade, String valor, 
			HttpServletRequest request, HttpServletResponse response, ProdutoBean produto) 
			throws ServletException, IOException {		
		if(nome == null || nome.isEmpty()) {
			redirecionarUsuarioValidarCamposFormulario(produto, "nome", request, response);			
		} else if(quantidade == null || quantidade.isEmpty()) {
			redirecionarUsuarioValidarCamposFormulario(produto, "quantidade", request, response);
		} else if(valor == null || valor.isEmpty()) {
			redirecionarUsuarioValidarCamposFormulario(produto, "valor", request, response);
		} 
	}
	
	private void redirecionarUsuarioValidarCamposFormulario(ProdutoBean produto, String campo, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {		
		RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");
		request.setAttribute("produtos", produtoDAO.listar());
		request.setAttribute("categorias", produtoDAO.listarCategorias());
		request.setAttribute("msg", declararMensagemParaUsuario(campo));
		request.setAttribute("prod", produto);
		view.forward(request, response);	
		return;
	}
	
	private String declararMensagemParaUsuario(String campo) {
		return "O campo " + campo.toUpperCase() + " é de preenchimento o obrigatório!";
	}

}
