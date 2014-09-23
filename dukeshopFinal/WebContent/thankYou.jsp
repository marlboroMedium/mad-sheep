<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="casestudy.business.domain.Member"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>등록(변경) 성공</title>
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
                <h4>[회원정보 등록(수정)결과]</h4>
                <table>
                    <tr>
                        <td class="label">회원 ID : </td>
                        <td>${requestScope.member.memberID}</td>
                    </tr>
                    <tr>
                        <td class="label">회원이름 : </td>
                        <td>${requestScope.member.name} 님이 등록(변경)되었습니다.</td>
                    </tr>
                </table>
            </div>
            <!-- END of main content -->
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
