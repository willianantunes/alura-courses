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

	<form:form action="${s:mvcUrl('PC#gravar').build()}" method="POST" 
		enctype="multipart/form-data" commandName="produto">
        <div>
            <label>Título</label><br />
            <form:errors path="titulo" /><br />
            <form:input path="titulo"/>
        </div>
        <div>
            <label>Descrição</label><br />
            <form:errors path="descricao" /><br />
            <form:textarea path="descricao" rows="10" cols="20" />
        </div>
        <div>
            <label>Páginas</label><br />
            <form:errors path="paginas" /><br />
            <form:input path="paginas"/>
        </div>     
	        <div>
	        	<label>Data de lançamento</label><br />
	        	<form:errors path="dataLancamento" /><br />
	        	<form:input path="dataLancamento" />
	        </div>  
	    <c:forEach items="${tipos}" var="tipoPreco" varStatus="status">
	        <div>
	            <label>${tipoPreco}</label>
	            <form:input path="precos[${status.index}].valor" />
	            <form:input type="hidden" path="precos[${status.index}].tipo" value="${tipoPreco}" />
	        </div>
	    </c:forEach>  
	    <div>
	    	<label>Sumário</label>
	    	<input name="sumario" type="file" />
	    </div>  
        <button type="submit">Cadastrar</button>	        
    </form:form>
    
</body>
</html>