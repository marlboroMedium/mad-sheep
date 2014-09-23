<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원 가입</title>
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
                <h4>[회원 가입]</h4>
                <form action="member?action=register" method="POST">
                    <table class="registertable">
                        <tr>
                            <td class="label">회원ID :</td>
                            <td><input type="text" name="memberID" size="20" maxlength="15"></td>
                        </tr>
                        <tr>
                            <td class="label">비밀번호 :</td>
                            <td><input type="password" name="password" size="20" maxlength="10"></td>
                        </tr>
                        <tr>
                            <td class="label">이름 :</td>
                            <td><input type="text" name="name" size="20" maxlength="20"></td>
                        </tr>
                        <tr>
                            <td class="label">이메일 :</td>
                            <td><input type="text" name="email" size="30" maxlength="60"></td>
                        </tr>
                        <tr>
                            <td class="label">전화번호 :</td>
                            <td><input type="text" name="tel" size="20" maxlength="20"></td>
                        </tr>
                        <tr>
                            <td class="label">우편번호 :</td>
                            <td><input type="text" name="zipcode" size="15" maxlength="7"></td>
                        </tr>
                        <tr>
                            <td class="label">주소 :</td>
                            <td><input type="text" name="address" size="50" maxlength="70"></td>
                        </tr>
                        <tr>
                            <td colspan="2">
                        </tr>
                        <tr>
                            <td></td>
                            <td><input type="submit" value="회원가입"> <input type="reset" value="취소"></td>
                        </tr>
                    </table>
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