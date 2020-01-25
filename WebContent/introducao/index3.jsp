<jsp:useBean id="calcula" class="beans.BeanJsp" type="beans.BeanJsp" scope="page"/>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="myprefix" uri="../WEB-INF/testetag.tld" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	
	
	<form action="cabecalho.jsp" method="post">
	
		
		<input type="text" id="nome" name="nome" />
		<br>
		<input type="text" id="ano" name="ano"  />
		<br>
		<input type="text" id="sexo" name="sexo"  />
		<br>
		<input type="submit" value="testar" />		
	</form>
	
	<% session.setAttribute("user", "javsdfsdfsdfdsa"); %>
	<a href="cabecalho.jsp">Testeeeee link session</a>
	
	<%--<jsp:include page="cabecalho.jsp" />  --%>
	
	<%= calcula.calcular(50) %>

	<%-- <jsp:forward page="receber-nome.jsp">
		<jsp:param value="curso jspppp" name="paramforward"/>
	</jsp:forward>--%>
	
	<h1>Bem-vindo!</h1>
	<%= "sucesso"%>
	
	<form action="receber-nome.jsp">
		<input type="text" id="nome" name="nome"/>
		<input type="submit" value="Enviar"/>
	</form>
	
	<% session.setAttribute("curso", "curso de jsp"); %>
	
	<%! int cont = 2; 
		
		public int retornar(int num) {
			return num * 3;
		}		
		
		int f = 5;
	%>
	
	
	
	<%= cont %>
	<br/>
	
	<%= retornar(8) %>
	<br>
	<%= application.getInitParameter("estado")%>
	
	<%@ page import="java.util.Date" %>
	
	<%= "data de hoje" + new Date() %>
	
	<%@ page info="Página do curso jsp" %>
	
	<%@ page errorPage="receber-nome.jsp" %>
	
	<%= 100/2 %>
	
	<%@ include file="pagina-include.jsp" %>
	
	<myprefix:minhatag/>
	
	<jsp:include page="rodape.jsp" />
	
	
</body>
</html>