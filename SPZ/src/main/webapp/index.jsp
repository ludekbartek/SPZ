<%-- 
    Document   : index
    Created on : Aug 21, 2015, 8:54:15 AM
    Author     : bar
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:if test="${empty user}">
            <meta http-equiv="Refresh" content="0; URL=login.jsp"/>
        </c:if>
        <meta http-equiv="Refresh" content="0; URL=${pageContext.request.contextPath}/SPZServlet/listSPZ"/>
        <link rel="stylesheet" type="text/css" href="styles/dcb.css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>JSP Page</title>
    </head>
    <body>
        <h1>SPZ</h1>
    </body>
</html>
