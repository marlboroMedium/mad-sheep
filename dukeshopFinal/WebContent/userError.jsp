<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>오류 발생</title>
    <link rel="stylesheet" href="css/dukeshop.css">
</head>
<body>
    <div class="tableContainer">
        <div class="tableRow">		
            <c:import url="/WEB-INF/incl/banner.jsp" />
        </div>
            
        <div class="tableRow">
            <c:import url="/WEB-INF/incl/side-bar.jsp" />
            <!-- START of main content-->
            <div class="main">
                <h4 id="error">입력 정보 오류</h4>
                <p>아래와 같은 문제가 있습니다.</p>
                <%-- Reports any errors (if any) --%>
                <c:if test="${not empty requestScope.errorMsgs}">
                <ul>
                <c:forEach items="${requestScope.errorMsgs}" var="message">
                    <li>${message}</li>
                </c:forEach>
                </ul>
                </c:if>
                <p>다시 시도해 주세요.</p>
            </div>                
            <!-- END of main content-->
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
