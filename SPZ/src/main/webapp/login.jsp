<%-- 
    Document   : login
    Created on : Aug 21, 2015, 9:37:52 AM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="style/dcb.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <form action="${pageContext.request.contextPath}/SPZServlet" method="post">
            <label for="login">Uživatelské jméno:</label>
            <input type="text" width="8" name="login"/>
            <br/>
            <label for="passwd">Heslo:</label>
            <input type="password" width="10" name="passwd"/>
            <br/>
            <input type="submit" value="Přihlásit"/>
        </form>
    </body>
</html>
