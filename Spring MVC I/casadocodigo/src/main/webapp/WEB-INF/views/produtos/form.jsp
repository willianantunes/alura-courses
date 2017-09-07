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
</head>
<body>

	<form:form action="${s:mvcUrl('PC#gravar').build()}" method="POST" commandName="produto">
        <div>
            <label>Título</label><br />
            <form:errors path="titulo" /><br />
            <input type="text" name="titulo" />
        </div>
        <div>
            <label>Descrição</label><br />
            <form:errors path="descricao" /><br />
            <textarea rows="10" cols="20" name="descricao"></textarea>
        </div>
        <div>
            <label>Páginas</label><br />
            <form:errors path="paginas" /><br />
            <input type="number" name="paginas" />
        </div>     
        <div>
        	<label>Data de lançamento</label>
        	<input type="text" name="dataLancamento" />
        	<form:errors path="dataLancamento" /><br />
        </div>  
	    <c:forEach items="${tipos}" var="tipoPreco" varStatus="status">
	        <div>
	            <label>${tipoPreco}</label>
	            <input type="text" name="precos[${status.index}].valor" />
	            <input type="hidden" name="precos[${status.index}].tipo" value="${tipoPreco}" />
	        </div>
	    </c:forEach>    
        <button type="submit">Cadastrar</button>	        
    </form:form>
    
</body>
</html>