<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, casestudy.business.domain.Product"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>상품 리스트</title>
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
                <h4>[상품 리스트 정보]</h4>
                <table id="allproductlist">
                    <tr>
                        <th class="productno">번호</th>
                        <th class="productname">제품명</th>
                        <th class="productprice">판매가격</th>
                        <th class="productimage">제품사진</th>
                        <th class="productcompany">제조사</th>
                    </tr>
                    
                    <c:forEach items="${requestScope.productList}" var="product" varStatus="loopStatus">
                    <tr>
                        <td class="productno">${loopStatus.count}</td>
                        <td class="productname">
                            <a href="product?action=select&productID=${product.productID}">${product.productName}</a>
                        </td>
                        <td class="productprice">${product.price2}</td>
                        <td class="productimage">
                            <a href="product?action=select&productID=${product.productID}">
                                <img src="images/${product.photoDir}">
                            </a>
                        </td>
                        <td class="productcompany">${product.company}</td>
                    </tr>
                    </c:forEach>
                </table>
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
