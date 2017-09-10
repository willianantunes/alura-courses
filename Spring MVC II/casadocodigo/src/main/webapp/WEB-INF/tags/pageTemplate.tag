<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="titulo" required="true" %>
<%@ attribute name="bodyClass" required="false" %>

<c:url value="/" var="contextPath" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
    <title>${titulo} - Casa do Código</title>

	<link href="${contextPath}resources/css/cssbase-min.css" rel="stylesheet" type="text/css" media="all" />
	<link href='http://fonts.googleapis.com/css?family=Droid+Sans:400,700' rel='stylesheet'/>
	<link href="${contextPath}resources/css/fonts.css" rel="stylesheet" type="text/css" media="all" />
	<link href="${contextPath}resources/css/fontello-ie7.css" rel="stylesheet" type="text/css" media="all" />
	<link href="${contextPath}resources/css/fontello-embedded.css" rel="stylesheet" type="text/css" media="all" />
	<link href="${contextPath}resources/css/fontello.css" rel="stylesheet" type="text/css" media="all" />
	<link href="${contextPath}resources/css/style.css" rel="stylesheet" type="text/css" media="all" />
	<link href="${contextPath}resources/css/layout-colors.css" rel="stylesheet" type="text/css" media="all" />
	<link href="${contextPath}resources/css/responsive-style.css" rel="stylesheet" type="text/css" media="all" />
	<link href="${contextPath}resources/css/guia-do-programador-style.css" rel="stylesheet" type="text/css"  media="all"  />
	<link href="${contextPath}resources/css/produtos.css" rel="stylesheet" type="text/css"  media="all"  />    
	<link href="${contextPath}resources/css/book-collection.css" rel="stylesheet" type="text/css"  media="all"  />
	<link href="${contextPath}resources/css/checkout-style.css" rel="stylesheet" type="text/css"  media="all"  />    
</head>
<body class="${bodyClass}">

	<%@ include file="/WEB-INF/views/cabecalho.jsp" %>
	
	<jsp:doBody />
	
	<%@ include file="/WEB-INF/views/rodape.jsp" %>

</body>
</html>
