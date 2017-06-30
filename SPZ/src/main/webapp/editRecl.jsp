<%-- 
    Document   : editRel
    Created on : Dec 31, 2015, 4:06:58 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="editRecl"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><f:message key="pageTitle"/> <c:out value="${spz.id}"/></title>
        <link rel="stylesheet" href="styles/dcb.css" type="text/css"/>
        <script type="text/javascript" src="scripts/support-scripts.js"></script>
    </head>
    <body>
        <jsp:include page="headerspz.jsp"/>
        <div class="center">
            <jsp:include page="navigationSpz.jsp"/>
        </div>
        <h1><f:message key="pageTitle"/></h1>
        <table class="border-fullwidthtable">
            <jsp:include page="editcommon.jsp"/>
            <tr>
                <td class="tablelabel"><f:message key="revised"/></td>
                <td colspan="3"><c:out value="${spz.revised}" escapeXml="false"/></td>
            </tr>
            <tr>
                <td class="tablelabel"><f:message key="solution"/></td>
                <td colspan="3"><c:out value="${spz.solution}" escapeXml="false"/></td>
            </tr>
            <tr>
                <td class="tablelabel"><f:message key="estWorkLoad"/></td>
                <td colspan="3"><c:out value="${spz.workLoadEstimation}"/> <f:message key="manHours"/> (<f:formatNumber value="${spz.workLoadEstimation/8.0}" maxFractionDigits="2"/> <f:message key="manDays"/>)</td>
            </tr>
            <tr>
                <td class="tablelabel"><f:message key="realWorkLoad"/></td>
                <td colspan="3"><c:out value="${spz.workLoadReal}"/> <f:message key="manHours"/> (<f:formatNumber value="${spz.workLoadReal/8.0}" maxFractionDigits="2"/> <f:message key="manDays"/>)</td> 
            </tr>
        </table>
        <c:if test="${user.isManager}">
            <form id="reimplForm" action="${pageContext.request.contextPath}/SPZServlet/reimpl" method="post">
                <input type="hidden" name="state" value="RECLAIMED"/>
                <input type="hidden" name="newstate" value="IMPLEMENTATION"/>
                <input type="hidden" name="spzid" value="${spz.id}"/>
                <input type="hidden" name="userid" value="${user.id}"/>
                <input type="hidden" name="configid" value="${config.id}"/>
                <input type="hidden" name="projectid" value="${project.id}"/>
                <!--<input type="submit" value="<f:message key='back'/>"/>-->
            </form>
                <form id="installForm" action="${pageContext.request.contextPath}/SPZServlet/releaseversion" method="post">
                <input type="hidden" name="state" value="RECLAIMED"/>
                <input type="hidden" name="spzid" value="${spz.id}"/>
                <input type="hidden" name="userid" value="${user.id}"/>
                <input type="hidden" name="configid" value="${config.id}"/>
                <input type="hidden" name="projectid" value="${project.id}"/>
                <!--<input type="submit" value="<f:message key='install'/>"/>-->
            </form>
            <form method="post">
                <input type="button" value="<f:message key='startImpl'/>" onclick="doPost(reimplForm)"/>
                <input type="button" value="<f:message key='release'/>" onclick="doPost(installForm)"/>
            </form>
        </c:if>
        <jsp:include page="listHistory.jsp"/>
        <jsp:include page="addNote.jsp"/>
    </body>
</html>
