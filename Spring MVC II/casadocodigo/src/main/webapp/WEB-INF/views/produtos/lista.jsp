<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Livros de java, Android, Iphone, PHP, Ruby e muito mais - Casa do código</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

</head>
<body>

<nav class="navbar navbar-inverse">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="${s:mvcUrl('HC#index').build()}">Casa do Código</a>
    </div>
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li><a href="${s:mvcUrl('PC#listar').build()}">Lista de Produtos</a></li>
        <li><a href="${s:mvcUrl('PC#form').build()}">Cadastro de Produtos</a></li>
    </ul>
	<ul class="nav navbar-nav navbar-right">
	  <li>
	    <a href="#">
	        <security:authentication property="principal" var="usuario"/>
	        Usuário: ${usuario.username}
	    </a>
	  </li>
	</ul>    
    </div><!-- /.navbar-collapse -->
  </div>
</nav>

<div class="container">
	<h1>Lista de produtos</h1>
	
	<c:if test="${not empty produtoCadastrado}">
		<h2 style="color: red">Produto com o título ${produtoCadastrado.titulo} cadastrado com sucesso!</h2>
	</c:if>	
	
	<c:if test="${not empty sucesso}">
		<h2 style="color: red">${sucesso}!</h2>
	</c:if>		
	
	<c:if test="${not empty falha}">
		<h2 style="color: red">${falha}!</h2>
	</c:if>		
	
	<table class="table table-bordered table-striped table-hover">
		<tr>
			<th>Id</th>
			<th>Título</th>
			<th>Descrição</th>
			<th>Páginas</th>
			<th>Data lançamento</th>
			<th>Caminho sumário</th>
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
	
</div>	
</body>
</html>