<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Livros de java, Android, Iphone, PHP, Ruby e muito mais - Casa do código</title>
</head>
<body>
	<h1>Lista de produtos</h1>
	
	<c:if test="${not empty produtoCadastrado}">
		<h2 style="color: red">Produto com o título ${produtoCadastrado.titulo} cadastrado com sucesso!</h2>
	</c:if>	
	
	<table>
		<tr>
			<td>Id</td>
			<td>Título</td>
			<td>Descrição</td>
			<td>Páginas</td>
			<td>Data lançamento</td>
			<td>Caminho sumário</td>
		</tr>		
		<c:forEach items="${produtos}" var="produto">
			<tr>
				<td><a href="${s:mvcUrl('PC#detalhe').arg(0, produto.id).build()}">${produto.id}</a></td>
				<td>${produto.titulo}</td>
				<td>${produto.descricao}</td>
				<td>${produto.paginas}</td>
				<td>${produto.dataLancamento}</td>
				<td>${produto.sumarioPath}</td>
			</tr>
		</c:forEach>		
	</table>
	
	<hr />
	
	<p>Cliquei <a href="<c:url value="/produtos/form" />">aqui</a> para cadastrar novos.</p>
	
	<hr />
	
	<p>Cliquei <a href="<c:url value="/limpa" />">aqui</a> para limpar a base.</p>
</body>
</html>