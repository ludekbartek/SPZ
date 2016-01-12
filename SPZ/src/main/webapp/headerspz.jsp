<%-- 
    Document   : commonheader
    Created on : Jan 9, 2016, 10:56:28 AM
    Author     : bar
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%--<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<jsp:include page="headerProject.jsp"/>
&gt;&gt;
<form action="${pageContext.request.contextPath}/SPZServlet/listspz" method="post">
    <input type="hidden" name="projectid" value="${project.id}"/>
    <input type="hidden" name="configid" value="${config.id}"/>
    <input type="hidden" name="spzid" value="${spz.id}"/>
    <input type="submit" value="${config.code}"/>
    <input type="hidden" name="userid" value="${user.id}"/>
</form>