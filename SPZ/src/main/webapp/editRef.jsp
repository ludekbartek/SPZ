<%-- 
    Document   : editRef
    Created on : Dec 28, 2015, 1:44:42 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="refine"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>
            <c:choose>
                <c:when test="${empty change}"><f:message key="spzInfoHeader"/></c:when>
                <c:otherwise><f:message key="stateChangeHeader"/></c:otherwise>
            </c:choose>
            <c:out value="${spz.id}"/>
        </title>
        <link rel="stylesheet" href="styles/dcb.css" type="text/css"/>
        <script type="text/javascript" src="scripts/support-scripts.js"></script>
    </head>
    <body>
        <jsp:include page="headerspz.jsp"/>
        <h1>
            <c:choose>
                <c:when test="${empty change}"><f:message key="spzInfoHeader"/></c:when>
                <c:otherwise><f:message key="stateChangeHeader"/></c:otherwise>
            </c:choose> <c:out value="${spz.id}"/>
        </h1>
        
        <table class="border-fullwidthtable">
        <jsp:include page="editcommon.jsp"/>
        <tr>
            <td class="label"><f:message key="revisedDesc"/>:</td>
            <td><c:out value="${spz.revisedRequest}"/></td>
        </tr>
        <tr>
            <td class="label"><f:message key="solution"/>:</td>
        </tr>
        <tr>
            
            <td><c:out value="${spz.history[0].solutionDescription}"/></td>
        </tr>
        <tr>
            <td class="label"><f:message key="estWork"/></td>
            <td><c:out value="${spz.workLoadEstimation}"/></td>
        </tr>
        </table>
            <c:choose>
                <c:when test="${empty change}">
                <c:if test="${user.role==1}">
                    <form action="${pageContext.request.contextPath}/SPZServlet/delete" method="post">
                        <input type="submit" value="<f:message key='cancelButton'/>"/>
                        <input type="hidden" name="spzid" value="${spz.id}"/>
                        <input type="hidden" name="userid" value="${user.id}"/>
                        <input type="hidden" name="configid" value="${config.id}"/>
                        <input type="hidden" name="projectid" value="${project.id}"/>
                    </form>
                </c:if>
                <form action="${pageContext.request.contextPath}/SPZServlet/editref" method="post">
                    <input type="hidden" name="spzid" value="${spz.id}"/>
                    <input type="hidden" name="change" value="true"/>
                    <input type="hidden" name="userid" value="${user.id}"/>
                    <input type="hidden" name="configid" value="${config.id}"/>
                    <input type="hidden" name="projectid" value="${project.id}"/>
                    <c:choose>
                        <c:when test="${user.role==0}">
                            <input type="submit" value="<f:message key='submitRefineUserButton'/>"/>
                        </c:when>
                        <c:otherwise>
                            <input type="submit" value="<f:message key='submitRefineAnalButton'/>"/>
                        </c:otherwise>
                    </c:choose>
                </form>
            </c:when>
            <c:otherwise>
                <h2><f:message key="stateInfoHeader"/></h2>
                <form action="${pageContext.request.contextPath}/SPZServlet/editspz" method="post">
                    <input type="hidden" name="spzid" value="${spz.id}"/>
                    <input type="hidden" name="userid" value="${user.id}"/>
                    <input type="hidden" name="state" value="REFINE"/>
                    <input type="hidden" name="newstate" value="ANALYSIS"/>
                    <textarea name="desc" class="desc" cols="80" rows="5"></textarea>
                    <input type="checkbox" name="external"/><label for="external"><f:message key="visible"/></label>
                    <input type="hidden" name="configid" value="${config.id}"/>
                    <input type="hidden" name="projectid" value="${project.id}"/>
                    <input type="submit" value="<f:message key='submit'/>"/>
                </form>
            </c:otherwise>
        </c:choose>
        <jsp:include page="listHistory.jsp"/>
    </body>
</html>
