<%-- 
    Document   : projectHeader
    Created on : Jan 9, 2016, 11:19:09 AM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp"/>
&gt;&gt;
<form action="${pageContext.request.pageContent}/SPZServlet/project" method="post">
    <input type="hidden" name="projectid" value="${spz.projectid}"/>
    <input type="hidden" name="userid" value="${user.id}"/>
    <input type="submit" value="${spz.projectName}"/>
</form>