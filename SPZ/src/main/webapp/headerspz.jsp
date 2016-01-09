<%-- 
    Document   : commonheader
    Created on : Jan 9, 2016, 10:56:28 AM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="projectHeader"/>
<form action="${pageContext.request.pageContent}/SPZServlet/" method="post">
    <input type="hidden" name="projectid" value="${spz.projectId}"/>
    <input type="hidden" name="configid" value="${spz.configId}"/>
    <input type="submit" value="${spz.configName}"/>
</form>