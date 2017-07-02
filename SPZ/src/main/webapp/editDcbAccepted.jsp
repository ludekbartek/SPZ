<%-- 
    Document   : editDcbAcc
    Created on : Dec 30, 2015, 7:16:25 AM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<f:setBundle basename="editDcbAccepted"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><f:message key="spzInfoHeader"/> <c:out value="${spz.id}"/> </title>
        <link rel="stylesheet" href="styles/dcb.css" type="text/css"/>
        <script type="text/javascript" src="scripts/support-scripts.js"></script>
    </head>
    <body>
        <jsp:include page="headerspz.jsp"/>
        <div class="center">
            <jsp:include page="navigationSpz.jsp"/>
        </div>
        <h1><f:message key="spzInfoHeader"/></h1>
        <table class="border-fullwidthtable">
            <jsp:include page="editcommon.jsp"/>
            <tr>
                <td class="tablelabel"><f:message key="revised"/></td>
                <td class="value" rowspan="3"><c:out value="${spz.revised}" escapeXml="false"/></td>
            </tr>
            <tr>
                <td class="tablelabel"><f:message key="solution"/></td>
                <td colspan="3" class="value"><c:out value="${spz.solution}" escapeXml="false"/></td>
                
            </tr>
            <tr>
                <td class="tablelabel"><f:message key="estWorkLoad"/></td>
                <td colspan="3" class="value">
                    <c:out value="${spz.workLoadEstimation}"/> <f:message key="manHours"/>
                    (<f:formatNumber maxFractionDigits="2" value="${spz.workLoadEstimation / 8.0}"/>
                    <f:message key="manDays"/>)
                </td>
            </tr>
        </table>
                <c:if test="${!user.isUser}">
                    
                    <form id="releaseForm" action="${pageContext.request.contextPath}/SPZServlet/releaseversion" method="post">
                        <input type="hidden" name="spzid" value="${spz.id}"/>
                        <input type="hidden" name="userid" value="${user.id}"/>
                        <input type="hidden" name="change" value="true"/>
                        <!--<input type="submit" value="<f:message key='releaseButton'/>"/>-->
                        <input type="hidden" name="configid" value="${config.id}"/>
                        <input type="hidden" name="projectid" value="${project.id}"/>
                    </form>
                    <form id="deleteForm" action="${pageContext.request.contextPath}/SPZServlet/delete" method="post">
                        <input type="hidden" name="spzid" value="${spz.id}"/>
                        <input type="hidden" name="userid" value="${spz.id}"/>
                        <input type="hidden" name="state" value="DCB_ACCEPTED"/>
                        <!--<input type="button" value="<f:message key='cancelButton'/>"/>-->
                        <input type="hidden" name="configid" value="${config.id}"/>
                        <input type="hidden" name="projectid" value="${project.id}"/>
                    </form>
                    <form id="startImplForm" action="${pageContext.request.contextPath}/SPZServlet/startimpl" method="post">
                        <input type="hidden" name="spzid" value="${spz.id}"/>
                        <input type="hidden" name="userid" value="${user.id}"/>
                        <input type="hidden" name="change" value="true"/>
                        <!--<input type="submit" value="<f:message key='startImplButton'/>"/>-->
                        <input type="hidden" name="configid" value="${config.id}"/>
                        <input type="hidden" name="projectid" value="${project.id}"/>
                    </form>
                    <form method="post">
                        <c:if test="${!user.isManager}">
                            <input type="button" value="<f:message key='startImplButton'/>" onclick="doPost(startImplForm)"/>
                        </c:if>
                        <input type="button" value="<f:message key='releaseButton'/>" onclick="doPost(releaseForm)"/>
                        <input type="button" value="<f:message key='cancelButton'/>" onclick="doPost(deleteForm)"/>
                    </form>
                </c:if>
                <jsp:include page="listHistory.jsp"/>
    </body>
</html>
