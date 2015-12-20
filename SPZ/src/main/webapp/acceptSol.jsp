<%-- 
    Document   : acceptSol
    Created on : Dec 20, 2015, 5:35:34 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Informace o SPZ <c:out value="${spz.id}"/> (<c:out value="${spz.issuer}"/>)</title>
    </head>
    <body>
        <h1>Zmena stavu SPZ</h1>
        <div class="highlighted">
            Pozadujete 'Schvalit reseni' na SPZ:
        </div>
        <c:set var="jsp" value="./acceptSol.jsp"/>
        <jsp:include page="editcommon.jsp"/>
        <h2>Informace pro stav 'Schvalit reseni:'</h2>
        <form action='${pageContext.request.contextPath}/SPZServlet/editspz' method="post">
        <label for='note'>Poznamka:</label>
        <textarea name='note' cols='80' rows="5" maxlength="8000"></textarea>
        <input type='hidden' name='spzid' value='${spz.id}'/>
        <input type='hidden' name='userid' value='${user.id}'/>
        <input type='hidden' name="state" value='SPECIFIED'/>
        <input type="hidden" name="newstate" value="ACCEPTED"/>
        <input type="submit" value="Odeslat"/>
        </form>
        <jsp:include page="listHistory.jsp"/>
    </body>
</html>
