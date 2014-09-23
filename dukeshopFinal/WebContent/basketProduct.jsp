<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>	
<head>
	<meta charset="UTF-8">
    <title>Basket Product Page</title>
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
                <h4>[장바구니 내용]</h4>
                    <table id="baskettable">
                        <tr>
                            <th class="productname">상품명</th>
                            <th class="count">주문갯수</th>
                            <th class="price">가격(단위:원)</th>
                            <th class="delete"></th>
                        </tr>                            

                <c:if test="${not empty basketCart}">      
                    <c:forEach items="${basketCart.items}" var="basket">	                            
                        <c:set var="totalPrice" value="${totalPrice + basket.totalPrice}" />
                        <tr>
                            <td class="productname"><a href="product?action=select&productID=${basket.productID}">${basket.productName}</a></td>
                            <td class="count">${basket.quantity}</td>
                            <td class="price">${basket.totalPrice}</td>
                            <td class="delete"><a href="product?action=emptyOne-basket&productID=${basket.productID}">삭제</a></td>
                        </tr>	                            
                    </c:forEach>
                        <tr>
                            <td colspan="4"></td>
                        </tr>                    
                        <tr>
                            <td></td>
                            <td class="sum">합계</td>
                            <td class="sum">${totalPrice}</td>
                            <td></td>
                        </tr>
                </c:if>

                        <tr>
                            <td colspan="4"></td>
                        </tr>
                        <tr>
                            <td colspan="4">
                                <form>
                                    <input type="button" value="쇼핑계속" onclick="location='product?action=select-all'"> 
                                <c:if test="${basketCart.numberOfItems > 0}">   
                                    <input type="button" value="구  매" onclick="location='product?action=select-memberPurchaser'"> 
                                    <input type="button" value="장바구니 비우기" onclick="location='product?action=emptyAll-basket'">
                                </c:if>
                                </form>
                            </td>
                        </tr>
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