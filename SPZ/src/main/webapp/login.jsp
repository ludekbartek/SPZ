<%-- 
    Document   : login
    Created on : Aug 21, 2015, 9:37:52 AM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <!-- Pouze pro testovaci ucely. V budoucnu odstranit.-->
       <!--<meta http-equiv="Refresh" content="0; URL=${pageContext.request.contextPath}/SPZServlet/listSPZ">-->
        <meta http-equiv="Refresh" content="0; URL=https://${pageContext.request.serverName}:8443/SPZ/SPZServlet"/>
        <title>Login</title>
        <link rel="stylesheet" type="text/css" href="styles/dcb.css">
    </head>
    <body>
        <!--<form action="${pageContext.request.contextPath}/SPZServlet/login" method="post">
            <label for="login">Uživatelské jméno:</label>
            <input type="hidden" name="action" value="login"/>
            <input type="text" size="15"  maxlength="15" name="j_username"/>
            <br/>
            <label for="passwd">Heslo:</label>
            <input type="password" size="20" maxlength="20" name="j_password"/>
            <br/>
            <input type="submit" value="Přihlásit"/>
        </form>
        <p/>-->
    </body>
</html>
