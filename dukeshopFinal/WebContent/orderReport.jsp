<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>	
<head>
	<meta charset="UTF-8">
    <title>Order Report Page</title>
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
                <h4>[주문 정보]</h4>            
                <table id="orderinfo">
                    <tr>
                        <th class="orderno">주문번호</th>
                        <th class="productname">제품명</th>
                        <th class="ordercount">주문수량</th>
                        <th class="orderprice">판매가격(단위:원)</th>
                        <th class="orderpricesum">합계(단위:원)</th>
                    </tr>

                <c:forEach items="${theOrderCollection}" var="theOrder">                    
                    <tr>                
                        <td class="orderno">${theOrder.orderNum}</td>
                        <td class="productname">${theOrder.productName}</td>
                        <td class="ordercount">${theOrder.quantity}</td>
                        <td class="orderprice">${theOrder.price}</td>
                        <td class="orderpricesum">${theOrder.price * theOrder.quantity}</td>
                    </tr>
                </c:forEach>
                </table>
                                            
                <h4>[배송 정보]</h4> 
                <table id="shippinginfo">                            
                    <tr>
                        <th class="recipient">수령인</th>
                        <th class="receiptaddr">배송지 주소</th>
                        <th class="receiptemail">Email</th>
                        <th class="receiptphone">전화번호</th>
                        <th class="receipttype">지불형태</th>
                    </tr>                                
                    <tr>
                        <td class="recipient">${theOrderCollection[0].name}</td>
                        <td class="receiptaddr">${theOrderCollection[0].address}</td>
                        <td class="receiptemail">${theOrderCollection[0].email}</td>
                        <td class="receiptphone">${theOrderCollection[0].tel}</td>
                        <td class="receipttype">${theOrderCollection[0].paytype}</td>
                    </tr>
                </table>
                <h4>주문이 완료되었습니다.</h4>
                <form action="index.jsp" method="POST" id="ordercomplete"> 
                    <input type="submit" value="확 인">
                </form>
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