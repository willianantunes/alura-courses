<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>      

<c:url value="/produtos" var="myAction" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Livros de java, Android, Iphone, PHP, Ruby e muito mais - Casa do código</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

    <style type="text/css">
        body{
            padding: 0px 0px 60px 0px;
        }
    </style>

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
    </div><!-- /.navbar-collapse -->
  </div>
</nav>

<div class="container">
	<form:form action="${s:mvcUrl('PC#gravar').build()}" method="POST" 
		enctype="multipart/form-data" commandName="produto">
        <div class="form-group">
            <label>Título</label><br />
            <form:errors path="titulo" /><br />
            <form:input path="titulo" cssClass="form-control"/>
        </div>
        <div class="form-group">
            <label>Descrição</label><br />
            <form:errors path="descricao" /><br />
            <form:textarea path="descricao" cssClass="form-control" />
        </div>
        <div class="form-group">
            <label>Páginas</label><br />
            <form:errors path="paginas" /><br />
            <form:input path="paginas" cssClass="form-control" />
        </div>     
        <div class="form-group">
        	<label>Data de lançamento</label><br />
        	<form:errors path="dataLancamento" /><br />
        	<form:input path="dataLancamento" cssClass="form-control" />
        </div>  
	    <c:forEach items="${tipos}" var="tipoPreco" varStatus="status">
	        <div class="form-group">
	            <label>${tipoPreco}</label>
	            <form:input path="precos[${status.index}].valor" cssClass="form-control" />
	            <form:input type="hidden" path="precos[${status.index}].tipo" value="${tipoPreco}" />
	        </div>
	    </c:forEach>  
	    <div class="form-group">
	    	<label>Sumário</label>
	    	<input name="sumario" type="file" class="form-control" />
	    </div>  
        <button type="submit" class="btn btn-primary">Cadastrar</button>	        
    </form:form>
</div>    
</body>
</html>