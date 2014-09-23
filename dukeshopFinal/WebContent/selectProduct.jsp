<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>상품 상세 정보</title>
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
                <h4>[상품 상세 정보]</h4>
                <table id="productdetails">
                    <tr>
                        <td rowspan="5"><img src="images/${requestScope.selectedProduct.photoDir}"></td>
                        <td id="producttitle" colspan="2">${requestScope.selectedProduct.productName}</td>
                    </tr>
                    <tr>
                        <td>제조사</td>
                        <td>${requestScope.selectedProduct.company}</td>
                    </tr>
                    <tr>
                        <td>일반가격</td>
                        <td id="price1">${requestScope.selectedProduct.price1}원</td>
                    </tr>
                    <tr>
                        <td>판매가격</td>
                        <td id="price2">⇒&nbsp;${requestScope.selectedProduct.price2}원</td>
                    </tr>
                    <tr>
                        <td>카드할부여부</td>
                        <td>
                            <c:choose>
                                <c:when test="${requestScope.selectedProduct.installment eq '1'}">
                                    가능
                                </c:when>
                                <c:otherwise>
                                    불가능
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3" id="details">
                            <table>
                                <tr>
                                    <td>제품설명</td>
                                </tr>
                                <tr>
                                    <td><textarea rows="4" cols="40">${requestScope.selectedProduct.detail}</textarea></td>
                                </tr>
                                <tr>
                                    <td id="purchase">
                                        <p>
                                            구매하시려면 주문갯수를 입력하시고<br>
                                            장바구니넣기 버튼을 눌러주세요
                                        </p>
                                        <form name="ProductDetail" action="product" method="GET">
                                            <input type="hidden" name="action" value="putOne-basket">
                                            <input type="hidden" name="productID" value="${requestScope.selectedProduct.productID}">
                                                주문갯수&nbsp;&nbsp;<input type="text" name="quantity" size="2" value="1"><br><br>
                                            <input type="submit" value="장바구니넣기">&nbsp;
                                            <input type="button" value="상품보기" onclick="location='product?action=select-all'">
                                        </form>
                                    </td>
                                </tr>
                            </table>
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
