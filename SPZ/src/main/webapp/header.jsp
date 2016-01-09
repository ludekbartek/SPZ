<%-- 
    Document   : header
    Created on : Jan 9, 2016, 11:20:35 AM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
&gt;&gt;
<form action="${pageContext.request.pageContent}/SPZServlet/listProjects" method="post">
    <input type="submit" value="<f:message key='projects'/>"/>
    <input type="hidden" name="userid" value="${user.id}"/>
</form>