package br.com.projetoJSP.servlet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;

import br.com.projetoJSP.DAO.UsuarioDAO;
import br.com.projetoJSP.bean.UsuarioBean;

@WebServlet("/salvarUsuario")
@MultipartConfig
public class UsuarioServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;    
	
	private UsuarioDAO usuarioDAO = new UsuarioDAO();		
  
    public UsuarioServlet() { }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String acao = request.getParameter("acao");
		String user = request.getParameter("user");
		
		redirecionarUsuario(acao, user, request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
		String acao = request.getParameter("acao");
		
		if (acao != null && acao.equalsIgnoreCase("reset")) {
			listarTodosUsuarios(request, response);				
		} else {		
			String id = request.getParameter("id");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String nome = request.getParameter("nome");
			String cep = request.getParameter("cep");	
			String rua = request.getParameter("rua");	
			String bairro = request.getParameter("bairro");	
			String cidade = request.getParameter("cidade");	
			String estado = request.getParameter("estado");	
			String ibge = request.getParameter("ibge");	
			String ativo = request.getParameter("ativo");
			String sexo = request.getParameter("sexo");
			String perfil = request.getParameter("perfil");
			
			UsuarioBean usuario = criarUsuario(id, login, senha, nome, 
				cep, rua, bairro, cidade, estado, ibge, verificarStatus(ativo), 
				sexo, perfil);
			
			validarCamposFormulario(login, senha, nome, request, response, usuario);	
			
			boolean loginExiste = false;
			boolean senhaExiste = false;
			
			loginExiste = validarLoginInsert(id, login);
			senhaExiste = validarSenhaInsert(id, senha);
			
			request.setAttribute("msg", msg(loginExiste, senhaExiste));
			request.setAttribute("user", usuario);			
			
			if (id == null || id.isEmpty() 
					&& usuarioDAO.validarLoginInsert(login) 
					&& usuarioDAO.validarSenhaInsert(senha)) {
				
				enviarImagem(request, usuario);
				enviarCurriculo(request, usuario);
				
				usuarioDAO.salvar(usuario);
				
				request.setAttribute("user", null);
				request.setAttribute("msgSalvarAtualizar", "Usuário cadastrado com sucesso!");
			} else if (id != null && !id.isEmpty()) {
				
				loginExiste = validarLoginUpdate(id, login);
				senhaExiste = validarSenhaUpdate(id, senha);
				
				boolean atualizou = false;
				
				if (!loginExiste && !senhaExiste) {
					atualizou = true;
					enviarImagem(request, usuario);
					enviarCurriculo(request, usuario);
					usuarioDAO.atualizar(usuario);	
					request.setAttribute("msgSalvarAtualizar", "Usuário atualizado com sucesso!");
				}
				
				if (!atualizou) { 
					request.setAttribute("msg", msg(loginExiste, senhaExiste));
					request.setAttribute("user", usuario);
				} else {
					request.setAttribute("user", null);
				}
			}				
			listarTodosUsuarios(request, response);
		}	
	}
	
	private Boolean verificarStatus(String ativo) {
		if (ativo != null && ativo.equalsIgnoreCase("on"))
			return true;
		return false;
	}
	
	private boolean validarLoginInsert(String id, String login) {
		if (id == null || id.isEmpty() && !usuarioDAO.validarLoginInsert(login)) {
			return true;
		} 	
		return false;
	}
	
	private boolean validarSenhaInsert(String id, String senha) {	
		if (id == null || id.isEmpty() && !usuarioDAO.validarSenhaInsert(senha)) {
			return true;								
		}
		return false;
	}
	
	private boolean validarLoginUpdate(String id, String login) {
		if (!usuarioDAO.validarLoginUpdate(login, id)) {
			return true;
		} 
		return false;	
	}
	
	private boolean validarSenhaUpdate(String id, String senha) {	
		if (!usuarioDAO.validarSenhaUpdate(senha, id)) {
			return true;
		}
		return false;
	}
	
	private UsuarioBean criarUsuario(String id, String login, String senha, String nome,
			String cep, String rua, String bairro, String cidade, String estado, String ibge, 
			Boolean status, String sexo, String perfil) {
		UsuarioBean usuario = new UsuarioBean();
		usuario.setId(!id.isEmpty() ? Long.parseLong(id) : null);
		usuario.setLogin(login);
		usuario.setSenha(senha);
		usuario.setNome(nome);
		usuario.setCep(cep);
		usuario.setRua(rua);
		usuario.setBairro(bairro);
		usuario.setCidade(cidade);
		usuario.setEstado(estado);
		usuario.setIbge(ibge);		
		usuario.setAtivo(status);
		usuario.setSexo(sexo);
		usuario.setPerfil(perfil);
		return usuario;
	}
	
	private String msg(boolean login, boolean senha) {
		
		String msg = "";
		
		if (login && !senha) {
			msg = "O login informado já existe na base de dados para outro usuário!";
		}
		
		if (!login && senha) {
			msg = "A senha informada já existe na base de dados para outro usuário!";
		}
		
		if (login && senha) {
			msg = "A senha e login informados já existem na base de dados para outro usuário!";
		}		
		return msg;
	}
	
	private void redirecionarUsuario(String acao, String user, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {			
		
		if (acao != null) {
			
			if (acao.equalsIgnoreCase("deletar")) {
				deletarUsuario(user, request, response);
			} 
			
			if (acao.equalsIgnoreCase("editar")) {
				editarUsuario(user, request, response);
			} 
			
			if (acao.equalsIgnoreCase("listarTodos")) {
				listarTodosUsuarios(request, response);
			} 
			
			if (acao.equalsIgnoreCase("download")) {
				fazerDownload(user, request, response);
			}
		}
		
		listarTodosUsuarios(request, response);
	}
	
	private void fazerDownload(String user, HttpServletRequest request, HttpServletResponse response) {
		
		UsuarioBean usuario = usuarioDAO.consultarPorId(user);
		
		if (usuario != null) {
			
			String contentType = "";
			byte[] fileBytes = null;
			String tipo = request.getParameter("tipo");
			
			if (tipo.equalsIgnoreCase("foto")) {
				contentType = usuario.getContentType();					
				fileBytes = Base64.decodeBase64(usuario.getFotoBase64());
			}
			
			if (tipo.equalsIgnoreCase("curriculo")) {
				contentType = usuario.getContentTypeCurriculo();						
				fileBytes = Base64.decodeBase64(usuario.getCurriculoBase64());
			}
			
			response.setHeader("Content-Disposition", "attachment;filename=arquivo."
			   + contentType.split("\\/")[1]);						
			
			InputStream is = new ByteArrayInputStream(fileBytes);			
			int read = 0;
			byte[] bytes = new byte[1024];
			
			try {					
				OutputStream os = response.getOutputStream();			
				
				while ((read = is.read(bytes)) != -1) {
					os.write(bytes, 0, read);
				}
				
				os.flush();
				os.close();
				
			} catch (IOException e) {				
				e.printStackTrace();
			}
		}		
	}

	private void editarUsuario(String user, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {		
		UsuarioBean usuario = usuarioDAO.consultarPorId(user);			
		RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
		request.setAttribute("user", usuario);
		request.setAttribute("usuarios", usuarioDAO.listarTodos());
		view.forward(request, response);		
	}
	
	private void listarTodosUsuarios(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {		
		RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
		request.setAttribute("usuarios", usuarioDAO.listarTodos());
		view.forward(request, response);		
	}
	
	private void deletarUsuario(String user, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {		
		if (usuarioDAO.deletar(user)) {
			RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
			request.setAttribute("usuarios", usuarioDAO.listarTodos());
			view.forward(request, response);
			return;
		}		
		request.setAttribute("msg", "Existe telefones cadastrados para o usuário " + 
			usuarioDAO.consultarPorId(user).getNome());
		return;		
	}
	
	private void redirecionarUsuarioValidarCamposFormulario(UsuarioBean usuario, String campo, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {		
		RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
		request.setAttribute("usuarios", usuarioDAO.listarTodos());
		request.setAttribute("msg", declararMensagemParaUsuario(campo));
		request.setAttribute("user", usuario);
		view.forward(request, response);	
		return;
	}
	
	private void validarCamposFormulario(String login, String senha, String nome,
			HttpServletRequest request, HttpServletResponse response, UsuarioBean usuario) 
			throws ServletException, IOException {		
		if(login == null || login.isEmpty()) {
			redirecionarUsuarioValidarCamposFormulario(usuario, "login", request, response);			
		} else if(senha == null || senha.isEmpty()) {
			redirecionarUsuarioValidarCamposFormulario(usuario, "senha", request, response);
		} else if(nome == null || nome.isEmpty()) {
			redirecionarUsuarioValidarCamposFormulario(usuario, "nome", request, response);
		} 
	}
	
	private String declararMensagemParaUsuario(String campo) {
		return "O campo " + campo.toUpperCase() + " é de preenchimento o obrigatório!";
	}
	
	private String criarMiniaturaImagem(String fotoBase64) {
		
		String miniaturaBase64 = "";
		
		try {
			byte[] imageByteDecode = Base64.decodeBase64(fotoBase64);
			
			BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageByteDecode));
			
			int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
			
			BufferedImage resizedImage = new BufferedImage(100, 100, type);
			
			Graphics2D g = resizedImage.createGraphics();
			
			g.drawImage(bufferedImage, 0, 0, 100, 100, null);
			
			g.dispose();
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();			
			ImageIO.write(resizedImage, "png", baos);			
			
			miniaturaBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(baos.toByteArray());			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return miniaturaBase64;		
	}
	
	private void enviarImagem(HttpServletRequest request, UsuarioBean usuario) {
		try {
			if (ServletFileUpload.isMultipartContent(request)) {	
				
				Part imagemFoto = request.getPart("foto");					
				
				if (imagemFoto != null && imagemFoto.getInputStream().available() > 0) {					
					
					String fotoBase64 = Base64.encodeBase64String(
						converteStremParaByte(imagemFoto.getInputStream()));
					
					usuario.setFotoBase64(fotoBase64);
					usuario.setContentType(imagemFoto.getContentType());
					usuario.setFotoBase64Miniatura(criarMiniaturaImagem(fotoBase64));					
				} else {
					usuario.setAtualizarImagem(Boolean.FALSE);					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void enviarCurriculo(HttpServletRequest request, UsuarioBean usuario) {
		try {
			if (ServletFileUpload.isMultipartContent(request)) {	
				
				Part curriculo = request.getPart("curriculo");						
				
				if (curriculo != null && curriculo.getInputStream().available() > 0) {
					String curriculoBase64 = Base64.encodeBase64String(
							converteStremParaByte(curriculo.getInputStream()));
					
					usuario.setCurriculoBase64(curriculoBase64);
					usuario.setContentTypeCurriculo(curriculo.getContentType());
				} else {
					usuario.setAtualizarCurriculo(Boolean.FALSE);									
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Converte a entrada de fluxo de dados da imagem para byte[]
	private byte[] converteStremParaByte(InputStream imagem) throws IOException  {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();		
		int reads = imagem.read();
		while (reads != -1) {
			baos.write(reads);
			reads = imagem.read();
		}
		return baos.toByteArray();
	}

}
