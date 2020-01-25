<jsp:useBean id="calcula" class="beans.BeanJsp" type="beans.BeanJsp" scope="page"/>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:setProperty property="*" name="calcula"/>
		<h3>CABEÇALHO</h3>
	<jsp:getProperty property="nome" name="calcula"/>
	<br>
	<jsp:getProperty property="ano" name="calcula"/>
	<br>
	<jsp:getProperty property="sexo" name="calcula"/>	
	
	Nome: ${param.nome}
	<br>
	Ano: ${param.ano}
	<br>
	Sexo: ${param.sexo}
	<br>	
	
	${sessionScope.user}
	<br>
	
</body>
</html>