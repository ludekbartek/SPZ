<%-- 
    Document   : editAccepted
    Created on : Dec 29, 2015, 3:13:30 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<f:setBundle basename="accepted"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><f:message key="spzInfoHeader"/> <c:out value="${spz.id}"/></title>
    </head>
    <body>
        <h1><f:message key="spzInfoHeader"/></h1>
        <table>
        <jsp:include page="editcommon.jsp"/>
        <tr>
            <td class="label"><f:message key="revised"/></td>
            <td colspan="3"><c:out value="${spz.revised}" escapeXml="true"/></td>
        </tr>
        <tr>
            <td class="label"><f:message key="solution"/></td>
            <td colspan="3"><c:out value="${spz.solution}" escapeXml="true"/></td>
        </tr>
        <tr>
            <td class="label"><f:message key="estWorkLoad"/></td>
            <td colspan="3"><c:out value="${spz.workLoadEstimation}"/></td>
        </tr>
        </table>
        <c:if test="${empty change}">
            <form action="${pageContext.request.contextPath}/SPZServlet/acceptImpl">
                <input type="hidden" name="spzid" value="${spz.id}"/>
                <input type="hidden" name="userid" value="${user.id}"/>
                <input type="submit" value="<f:message key='acceptButton'/>"/>
            </form>
            <form action="${pageContext.request.contextPath}/SPZServlet/">
                <input type="hidden" name="newstate" value="CANCELED"/>
                <input type="hidden" name="spzid" value="${spz.id}"/>
                <input type="hidden" name="userid" value="${user.id}"/>
                <input type="hidden" name="state" value="ACCEPTED"/>
                <input type="submit" value="<f:message key='cancel'/>"/>
            </form>
        </c:if>
            <jsp:include page="listHistory.jsp"/>
    </body>
</html>
