<%-- 
    Document   : configs
    Created on : Jan 9, 2016, 3:39:54 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="projects"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><f:message key="confHeader"/>(${user.login})</title>
    </head>
    <body>
        <jsp:include page="headerProject.jsp"/>
        <h1><f:message key="installs"/></h1>
    </body>
</html>
