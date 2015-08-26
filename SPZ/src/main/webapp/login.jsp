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
        
        <!-- Pouze pro testovaci ucely. V budoucnu odstranit.-->
        <meta http-equiv="Refresh" content="0; URL=/SPZ/SPZservlet/listSPZ">
        
        <title>Login</title>
    </head>
    <body>
        <form action="j_security_check" method="post">
            <label for="login">Uživatelské jméno:</label>
            <input type="text" width="15" name="j_username"/>
            <br/>
            <label for="passwd">Heslo:</label>
            <input type="password" width="20" name="j_password"/>
            <br/>
            <input type="submit" value="Přihlásit"/>
        </form>
    </body>
</html>
