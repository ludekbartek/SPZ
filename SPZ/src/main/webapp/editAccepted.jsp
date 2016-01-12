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
        <title><f:message key="spzInfoHeader"/> <c:out value="${spz.id}"/></title><link rel="stylesheet" href="styles/lists.css" type="text/css"/>
        <link rel="stylesheet" href="styles/dcb.cs" type="text/css"/>
    </head>
    <body>
        <jsp:include page="headerspz.jsp"/>
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
            <c:choose>
        <c:when test="${empty change }">
            <c:if test="${user.role !=0}">
            <form action="${pageContext.request.contextPath}/SPZServlet/acceptimpl" method="post">
                <input type="hidden" name="spzid" value="${spz.id}"/>
                <input type="hidden" name="userid" value="${user.id}"/>
                <input type="hidden" name="configid" value="${config.id}"/>
                <input type="hidden" name="projectid" value="${project.id}"/>
                <input type="submit" value="<f:message key='acceptButton'/>"/>
            </form>
            
            <form action="${pageContext.request.contextPath}/SPZServlet/delete" method="post">
                <input type="hidden" name="newstate" value="CANCELED"/>
                <input type="hidden" name="spzid" value="${spz.id}"/>
                <input type="hidden" name="userid" value="${user.id}"/>
                <input type="hidden" name="state" value="ACCEPTED"/>
                <input type="hidden" name="configid" value="${config.id}"/>
                <input type="hidden" name="projectid" value="${project.id}"/>
                <input type="submit" value="<f:message key='cancel'/>"/>
            </form>
            
            </c:if>
        </c:when>
            <c:otherwise>
                <form action="${pageContext.request.contextPath}/SPZServlet/editspz" method="post">
                    <input type="hidden" name="spzid" value="${spz.id}"/>
                    <input type="hidden" name="userid" value="${user.id}"/>
                    <input type="hidden" name="state" value="ACCEPTED"/>
                    <input type="hidden" name="newstate" value="DCB_ACCEPTED"/>
                    <input type="hidden" name="configid" value="${config.id}"/>
                    <input type="hidden" name="projectid" value="${project.id}"/>
                    <textarea name="desc" cols="80" rows="8"></textarea>
                    <input type="checkbox" name="external"/><label for="external"><f:message key="visible"/></label>
                    <input type="submit" value="<f:message key='submit'/>"/>
                </form>
            </c:otherwise>
            </c:choose>
            <jsp:include page="listHistory.jsp"/>
            <c:set var="jsp" value="./editAccepted.jsp"/>
            <jsp:include page="addNote.jsp"/>
    </body>
</html>
