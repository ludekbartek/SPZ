<%-- 
    Document   : editAnalysis
    Created on : Oct 4, 2015, 1:30:13 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Změna stavu SPZ <c:out value="${spz.id}"/><c:if test="${not empty spz.issuer}">(${spz.issuer})</c:if></title>
        <f:bundle basename="cz.dcb.resources"/>
    </head>
    <body>
        <h1><f:message key="analysisHeader"/></h1>
        <f:message key="analysisRequest"/>
    </body>
</html>
