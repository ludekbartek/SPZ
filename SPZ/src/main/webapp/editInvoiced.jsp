<%-- 
    Document   : editInvoiced
    Created on : Jan 3, 2016, 4:40:08 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="editInvoiced"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><f:message key="pageTitle"/> <c:out value="${spz.id}"/> (<c:out value="${user.login}"/>)</title>
        <link rel="stylesheet" href="styles/dcb.css" type="text/css"/>
        <script type="text/javascript" src="scripts/support-scripts.js"></script>
    </head>
    <body>
        <jsp:include page="headerspz.jsp"/>
        <h1><f:message key="pageTitle"/></h1>
        <table>
            <jsp:include page="editcommon.jsp"/>
            <tr>
                <td class="label"><f:message key="revised"/></td>
                <td colspan="3"><c:out value="${spz.revised}"/></td>
            </tr>
            <tr>
                <td class="label"><f:message key="solution"/></td>
                <td colspan="3"><c:out value="${spz.solution}"/></td>
            </tr>
            <tr>
                <td class="label"><f:message key="estWorkLoad"/></td>
                <td colspan="3"><c:out value="${workLoadEstimation}"/></td>
            </tr>
            <tr>
                <td class="label"><f:message key="solInfo"/></td>
                <td colspan="3"><c:out value="${spz.solutionInfo}"/></td>
            </tr>
            <tr>
                <td class="label"><f:message key="realWorkLoad"/></td>
                <td colspan="3"><c:out value="${spz.workLoadReal}"/></td>
            </tr>
        </table>
        <jsp:include page="listHistory.jsp"/>
        <jsp:include page="addNote.jsp"/>
    </body>
</html>
