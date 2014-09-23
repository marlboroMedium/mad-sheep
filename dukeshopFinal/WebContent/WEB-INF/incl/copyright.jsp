<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar" %>

<%-- get today's year --%>
<%
Calendar today = Calendar.getInstance();
int year = today.get(Calendar.YEAR);
%>
<hr id="copyrightline">
<p id="copyright">
    &copy; Duke's Store, 2000-<%= year %>
</p>
