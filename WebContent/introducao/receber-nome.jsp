<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Receber Nome</h1>
	
	<%= request.getParameter("paramforward") %>
	
	<%@ page isErrorPage="true" %>
	
	<%= exception %>
	
	<%= "Nome recebido: " + request.getParameter("nome") %>
	
	<%! int cont = 2; %>
	<br>
	<%= cont %>
	<br>
	<%= request.getContextPath() %>
	<br>
	<%= request.getContentType() %>
	<br>
	<%= request.getAuthType() %>
	<br>
	<%= request.getLocalAddr() %>
	<br>
	<%= request.getLocalName() %>
	<br>
	<%= request.getLocalPort() %>
	<br>
	<%= request.getProtocol() %>
	<br>
	<%= request.getRemoteHost() %>
	<br>
	<%= request.getRemoteUser() %>
	<br>
	<%= request.getRequestedSessionId() %>
	
	<br>
	<%--<% response.sendRedirect("http://www.uol.com.br"); --%>
	
	<%= session.getAttribute("curso") + "página 1" %>
	
	<%@ include file="pagina-include.jsp" %>
</body>
</html>