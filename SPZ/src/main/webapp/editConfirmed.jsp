<%-- 
    Document   : editConf - editace SPZ ve stavu prijata instalace
    Created on : Jan 3, 2016, 1:09:15 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="editConf"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><f:message key="pageTitle"/> <c:out value="${spz.id}"/> (<c:out value="${user.login}"/>)</title>
        <link rel="stylesheet" href="styles/dcb.css" type="text/css"/>
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
                <td colspan="3"><c:out value="${spz.workLoadEstimation}"/></td>
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
        <c:if test="${user.role!=0}">
            <form action="${pageContext.request.contextPath}/SPZServlet/invoiceSPZ" method="post">
                <input type="hidden" name="spzid" value="${spz.id}"/>
                <input type="hidden" name="userid" value="${user.id}"/>
                <input type="submit" value="<f:message key='invoice'/>"/>
                <input type="hidden" name="configid" value="${config.id}"/>
                <input type="hidden" name="projectid" value="${project.id}"/>
            </form>
        </c:if>    
        <jsp:include page="listHistory.jsp"/>
        <c:set var="jsp" value="'editConf.jsp'"/>
        <jsp:include page="addNote.jsp"/>
    </body>
</html>
