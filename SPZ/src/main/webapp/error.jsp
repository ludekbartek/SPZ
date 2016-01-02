<%-- 
    Document   : error
    Created on : Jan 2, 2016, 12:17:02 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<f:setBundle basename="errors"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><f:message key="title"/></title>
    </head>
    <body>
        <h1><f:message key="title"/> <c:out value="${error}"/></h1>
        <a href="${pageContext.request.contextPath}/"><f:message key="back"/></a>
    </body>
</html>
