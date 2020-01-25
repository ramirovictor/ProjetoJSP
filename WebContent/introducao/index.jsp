<jsp:useBean id="calcula" class="beans.BeanJsp" type="beans.BeanJsp" scope="page"/>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib prefix="myprefix" uri="../WEB-INF/testetag.tld" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>	

	<c:out value="${'bem-vindo ao JSTL'}" />
	
	<%--<c:import var="data" url="https://www.google.com.br" /> --%>
	<c:set var="data" scope="page" value="${500 * 6 }"/>
	<c:remove var="data"/>
	<c:out value="${data}" />
	
	<c:catch var="erro">
		<% int var = 100 / 2; %>
		<%=100/0 %>
	</c:catch>
	
	<c:if test="${erro != null}">
		${erro.message}
	</c:if>
	
	<c:set var="numero" value="${100/2}"/>
	<c:choose>
		<c:when test="${numero >= 50}">
			<c:out value="${'Maior ou igual a 50'}" />
		</c:when>
		<c:when test="${numero < 50}">
			<c:out value="${'Menor que 50'}" />
		</c:when>		
		<c:otherwise>
			<c:out value="${'Não encontrou valor correto'}" />
		</c:otherwise>
	</c:choose>
	
	<c:forEach var="n" begin="1" end="${numero}">
		Item : ${n}
		<br>
	</c:forEach>
	
	<c:forTokens items="Tadeu-Palermo" delims="-" var="nome">
		Nome : <c:out value="${nome}" />
		<br>
	</c:forTokens>
	
	<c:url value="/acessoLiberado.jsp" var="acesso">
		<c:param name="para1" value="111"></c:param>
		<c:param name="para2" value="222"></c:param>
	</c:url>	
	
	${acesso}
	
	<c:if test="${numero >= 50 }">
		<c:redirect url="acessoliberado.jsp" />
	</c:if>
	
	<c:if test="${numero < 50 }">
		<c:redirect url="http://www.javaavancado.com" />
	</c:if>

	<p/>
	<p/>
	<p/>
	<p/>
	
	<form action="LoginServlet" method="post">	
		Login:
		<input type="text" id="login" name="login" />
		
		<br>
		Senha:
		<input type="text" id="senha" name="senha"  />
		
		<br>
		
		<input type="submit" value="Logar" />		
	</form>
	
	
</body>
</html>