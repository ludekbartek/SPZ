<%-- 
    Document   : editRel
    Created on : Dec 31, 2015, 4:06:58 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="editRel"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><f:message key="pageTitle"/> <c:out value="${spz.id}"/></title>
        <link rel="stylesheet" href="styles/dcb.css" type="text/css"/>
    </head>
    <body>
        <h1><f:message key="pageTitle"/></h1>
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
        <c:if test="${user.role == '2'}">
            <form action="${pageContext.request.contextPath}/SPZServlet/editspz" method="post">
                <input type="hidden" name="state" value="RELEASED"/>
                <input type="hidden" name="newState" value="DCB_ACCEPTED"/>
                <input type="hidden" name="spzid" value="${spz.id}"/>
                <input type="hidden" name="userid" value="${user.id}"/>
                <input type="submit" value="<f:message key='back'/>"/>
            </form>
                <form action="${pageContext.request.contextPath}/SPZServlet/install" method="post">
                <input type="hidden" name="state" value="RELEASED"/>
                <input type="hidden" name="spzid" value="${spz.id}"/>
                <input type="hidden" name="userid" value="${user.id}"/>
                <input type="submit" value="<f:message key='install'/>"/>
            </form>
            <form action="${pageContext.request.contextPath}/SPZServlet/delete" method="post">
                <input type="hidden" name="state" value="RELEASED"/>
                <input type="hidden" name="newState" value="CANCELED"/>
                <input type="hidden" name="spzid" value="${spz.id}"/>
                <input type="hidden" name="userid" value="${user.id}"/>
                <input type="submit" value="<f:message key='cancel'/>"/>
            </form>
            
        </c:if>
            <jsp:include page="listHistory.jsp"/>
    </body>
</html>
