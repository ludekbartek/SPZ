<%-- 
    Document   : editDeleted
    Created on : Dec 6, 2015, 1:13:02 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Informace os SPZ <c:out value="${spz.reqNumber}"/></title>
    </head>
    <body>
        <h1>Informace o SPZ</h1>
        <jsp:include page="editcommon.jsp"/>
        <jsp:include page="listHistory.jsp"/>
        <h2>Pridej poznamku</h2>
        <jsp:include page="addNote.jsp"/>
</html>
