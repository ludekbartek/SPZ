<%-- 
    Document   : acceptSol
    Created on : Dec 20, 2015, 5:35:34 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="acceptSol"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><f:message key="pageTitle"/>Informace o SPZ <c:out value="${spz.id}"/> (<c:out value="${spz.issuer}"/>)</title>
        <link rel="stylesheet" href="styles/dcb.css" type="text/css"/>
    </head>
    <body>
        <jsp:include page="headerSpzEdit.jsp"/>
        <h1><f:message key="pageTitle"/></h1>
        <div class="highlighted">
            <f:message key="request"/>:
        </div>
        <c:set var="jsp" value="./acceptSol.jsp"/>
        <table>
        <jsp:include page="editcommon.jsp"/>
        </table>
        <h2><f:message key="reqInfo"/>:</h2>
        <form action='${pageContext.request.contextPath}/SPZServlet/editspz' method="post">
            <label for='note'><f:message key="note"/>:</label>
            <textarea name='note' cols='80' rows="5" maxlength="8000"></textarea>
            <input type='hidden' name='spzid' value='${spz.id}'/>
            <input type='hidden' name='userid' value='${user.id}'/>
            <input type='hidden' name="state" value='SPECIFIED'/>
            <input type="hidden" name="newstate" value="ACCEPTED"/>
            <input type="submit" value="<f:message key='submit'/>"/>
        </form>
        <jsp:include page="listHistory.jsp"/>
    </body>
</html>
