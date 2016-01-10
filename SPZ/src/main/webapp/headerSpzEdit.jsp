<%-- 
    Document   : headerSpzEdit
    Created on : Jan 10, 2016, 12:30:27 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="headerspz.jsp"/>
&gt;&gt;
<form action="${pageContext.request.contextPath}/SPZServlet/editspz" method="post">
    <input type="hidden" name="spzid" value="${spz.id}"/>
    <input type="hidden" name="userid" value="${user.id}"/>
    <input type="submit" value="${spz.id}"/>
</form>