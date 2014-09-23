<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
<%
String expFullName = exception.getClass().getName();
String expName = expFullName.substring(expFullName.lastIndexOf(".") + 1);
String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
%> 
--%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${pageContext.exception.message}</title>
<link rel="stylesheet" href="css/dukeshop.css">
</head>
<body>

	<div class="main">
		<p class="exceptionname">${pageContext.exception.message}</p>
		<p>
			${pageContext.exception}<br> <br> This is the request URI:
			<br>
			<code><${requestScopeerrorData.requestURI}</code>
			<br>
		</p>

	</div>
</body>
</html>
