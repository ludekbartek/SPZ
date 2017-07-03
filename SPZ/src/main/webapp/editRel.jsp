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
                <td colspan="3"><c:out value="${spz.workLoadEstimation}"/></td>
            </tr>
        </table>
        <c:if test="${user.isManager}">
            <form id="reimplForm" action="${pageContext.request.contextPath}/SPZServlet/reimpl" method="post">
                <input type="hidden" name="state" value="RELEASED"/>
                <input type="hidden" name="newstate" value="DCB_ACCEPTED"/>
                <input type="hidden" name="spzid" value="${spz.id}"/>
                <input type="hidden" name="userid" value="${user.id}"/>
                <input type="hidden" name="configid" value="${config.id}"/>
                <input type="hidden" name="projectid" value="${project.id}"/>
                <!--<input type="submit" value="<f:message key='back'/>"/>-->
            </form>
                <form id="installForm" action="${pageContext.request.contextPath}/SPZServlet/install" method="post">
                <input type="hidden" name="state" value="RELEASED"/>
                <input type="hidden" name="spzid" value="${spz.id}"/>
                <input type="hidden" name="userid" value="${user.id}"/>
                <input type="hidden" name="configid" value="${config.id}"/>
                <input type="hidden" name="projectid" value="${project.id}"/>
                <!--<input type="submit" value="<f:message key='install'/>"/>-->
            </form>
            <form id="deleteForm" action="${pageContext.request.contextPath}/SPZServlet/delete" method="post">
                <input type="hidden" name="state" value="RELEASED"/>
                <input type="hidden" name="newstate" value="CANCELED"/>
                <input type="hidden" name="spzid" value="${spz.id}"/>
                <input type="hidden" name="userid" value="${user.id}"/>
                <input type="hidden" name="configid" value="${config.id}"/>
                <input type="hidden" name="projectid" value="${project.id}"/>
                <!--<input type="submit" value="<f:message key='cancel'/>"/>-->
            </form>
            <form method="post">
                <input type="button" value="<f:message key='back'/>" onclick="doPost(reimplForm)"/>
                <input type="button" value="<f:message key='install'/>" onclick="doPost(installForm)"/>
                <c:if test="${user.isManager}">
                    <input type="button" value="<f:message key='cancel'/>" onclick="doPost(deleteForm)"/>
                </c:if>
            </form>
        </c:if>
        <jsp:include page="listHistory.jsp"/>
        <jsp:include page="addNote.jsp"/>
    </body>
</html>
