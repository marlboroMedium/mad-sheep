<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>	
<head>
    <meta charset="UTF-8">
    <title>Purchase Product Page</title>
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
                <h4>[상품 수령 정보]</h4>            
                <p id="reciptlabel">
                    수령인 정보 (상품을 받으실 분의 주소와 연락처를 기재해 주세요.)
                </p>                                
                <form action="product?action=insert-basketPurchaser" method="POST">
                    <table id="receiptinfo">
                        <tr>
                            <td class="label">수령장소 : </td>
                            <td>
                                <input type="radio" name="place" value="1" checked>자택&nbsp;
                                <input type="radio" name="place" value="2">직장
                            </td>
                        </tr>
                        <tr>
                            <td class="label">회원ID : </td>
                            <td> ${loginMember.memberID}</td>
                        </tr>
                        <tr>
                            <td class="label">수령인 : </td>
                            <td> <input type="text" name="name" size="20" value="${loginMember.name}"></td>
                        </tr>
                        <tr>
                            <td class="label">우편번호 : </td>
                            <td> <input type="text" name="zipcode" size="20" value="${loginMember.zipcode}"></td>
                        </tr>
                        <tr>
                            <td class="label">Email : </td>
                            <td> <input type="text" name="email" size="20" value="${loginMember.email}"></td>
                        </tr>
                        <tr>
                            <td class="label">연락처 : </td>
                            <td> <input type="text" name="tel" size="20" value="${loginMember.tel}"></td>
                        </tr>
                        <tr>
                            <td class="label">주소 : </td>
                            <td> <input type="text" name="address" size="40" value="${loginMember.address}"></td>
                        </tr>
                        <tr>
                            <td class="label">결제방법 : </td>
                            <td>
                                <input type="radio" name="paytype" value="remit" checked >온라인입금&nbsp;
                                <input type="radio" name="paytype" value="card">카드결제
                            </td>
                        </tr>
                        <tr>
                            <td class="label">카드회사 : </td>
                            <td> <input type="text" name="cardtype" size="40"></td>
                        </tr>
                        <tr>
                            <td class="label">카드번호 : </td>
                            <td> <input type="text" name="cardnumber" size="40"></td>
                        </tr>
                        <tr>
                            <td colspan="2"></td>
                        </tr>
                        <tr>
                            <td colspan="2" id="buttons">
                                <input type="button" value="쇼핑계속" onclick="location='product?action=select-all'">
                                <input type="submit" value="확 인"> 
                                <input type="button" value="장바구니 보기" onclick="location='basketProduct.jsp'">
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <!-- end of main content-->
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