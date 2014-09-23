<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="casestudy.business.domain.Member" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="sidebar">
    <c:if test="${empty sessionScope.loginMember}">
        <c:import url="/WEB-INF/incl/login.jsp" />
    </c:if>
    <c:if test="${not empty sessionScope.loginMember}">     
        <c:import url="/WEB-INF/incl/logout.jsp" />        
    </c:if>
    
    <table id="sidebartable">
        <tr>
            <td id="menulabel">Menu</td>
        </tr>    
        <tr>
            <td><hr></td>
        </tr>
        <tr>
            <td><a href="product?action=select-all">상품보기</a></td>
        </tr>
        <c:if test="${empty sessionScope.loginMember}">
        <tr>
            <td><a href="registerMember.jsp">회원가입</a></td>
        </tr>
        </c:if>
        <c:if test="${not empty sessionScope.loginMember}">
        <tr>
            <td><a href="member?action=select">회원정보변경</a></td>
        </tr>
        </c:if>    
    </table>
</div>
