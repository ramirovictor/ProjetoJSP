<jsp:useBean id="calcula" class="beans.BeanJsp" type="beans.BeanJsp" scope="page"/>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Acesso liberado</title>
	</head>
	<body>
	<a href="index.jsp"><img alt="Sair" title="Sair" src="resources/img/exit.png" width="30px" height="30px"></a>
		<div style="padding-top: 10%;">		
			<h1 style="text-align: center;">Bem-vindo ao Sistema!</h1>
			<div>
				<table>
					<tr>
						<td>
							<a href="salvarUsuario?acao=listarTodos">
								<img src="resources/img/user.png" alt="Cadastrar Usuário" title="Cadastrar Usuário" width="100px" height="100px">
							</a>
						</td>
						<td>
							<a href="salvarProduto?acao=listarTodos">
								<img src="resources/img/prod.png" alt="Cadastrar Produto" title="Cadastrar Produto" width="100px" height="100px">
							</a>
						</td>
					</tr>
					<tr>
						<td>Cad. Usuários</td>
						<td>Cad. Produtos</td>
					</tr>
				</table>
				</div>			
		</div>	
	</body>
</html>