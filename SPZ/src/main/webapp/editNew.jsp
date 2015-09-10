<%-- 
    Document   : editNew
    Created on : Sep 10, 2015, 1:58:55 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>System servisni podpory</h1>
        <h2>Informace o SPZ</h2>
        <table>
            <%@include file="editcommon.jsp" %>
            <tr>
                <td colspan="3">
                    <form action="${pageContext.request.contextPath}/SPZServlet/edit">
                        <input type="submit" value="Predat k analyze"/>
                        <input type="hidden" name="state" value="new"/>
                        <input type="hidden" name="newState" value="analysis"/>
                    </form>
                </td>
            </tr>
        </table>
    </body>
</html>
