<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="bannerTitle" value="Welcome to Duke's Store" />

<div class="tableCell logo">
    <a href="index.jsp"><img src="images/duke.gif" alt="Duke's Mall Logo"></a>
</div>

<div class="tableCell banner">
    <p>${bannerTitle}</p>
    <div>
       <a href="product?action=select-all">상품보기</a> |

       <c:if test="${empty sessionScope.loginMember}">
       <a href="registerMember.jsp">회원가입</a>
       </c:if>

       <c:if test="${not empty sessionScope.loginMember}">
       <a href="basketProduct.jsp">장바구니</a>
       </c:if>
    </div>
</div>
