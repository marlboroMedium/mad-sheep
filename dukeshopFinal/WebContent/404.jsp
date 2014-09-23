<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>	
<head>
	<meta charset="UTF-8">
	<title>404 - Not Found</title>
    <link rel="stylesheet" href="<c:url value="/css/dukeshop.css" />">
</head>
<body>
    <div class="tableContainer">
        <div class="tableRow">		
            <c:import url="/WEB-INF/incl/banner.jsp" />
        </div>
        
        <div class="tableRow">
            <c:import url="/WEB-INF/incl/side-bar.jsp" />            
            <div class="main">
                <p class="notfound"><img src="<c:url value="/images/surfing.gif" />"></p>
                <p class="notfound">요청하신 페이지를 찾을 수 없습니다.</p>
            </div>
        </div>

        <div class="tableRow">
            <div class="tableCell">
            </div>
            <div class="tableCell">
                <c:import url="/WEB-INF/incl/copyright.jsp" />
            </div>
        </div>
    </div>
</body>
</html>
