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
        <link rel="stylesheet" href="/SPZ/styles/dcb.css" type="text/css"/>
        <script type="text/javascript" src="/SPZ/scripts/support-scripts.js"></script>
    </head>
    <body>
        <jsp:include page="headerspz.jsp"/>
        <div class="center">
            <jsp:include page="navigationSpz.jsp"/>
        </div>
        <h1>
            <c:choose>
                <c:when test="${empty change}"><f:message key="spzInfoHeader"/></c:when>
                <c:otherwise><f:message key="stateChangeHeader"/></c:otherwise>
            </c:choose> <c:out value="${spz.id}"/>
        </h1>
       <c:if test="${not empty change}">
        <div class="highlighted">
            <f:message key="request"/> 
            <c:choose>
                <c:when test="${not empty newState}">
                    '<f:message key="${newState}"/>'
                </c:when>
                <c:otherwise>
                    '<f:message key="refine"/>'
                </c:otherwise>
            </c:choose>
            na SPZ:
        </div>
       </c:if>
        <table class="border-fullwidthtable">
        <jsp:include page="editcommon.jsp"/>
        <tr>
            <td class="label"><f:message key="revisedDesc"/>:</td>
            <td><c:out value="${spz.revised}"/></td>
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
                <c:if test="${user.isAnalyst}">
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
                        <c:when test="${user.isUser}">
                            <input type="submit" value="<f:message key='submitRefineUserButton'/>"/>
                        </c:when>
                        <c:otherwise>
                            <input type="submit" value="<f:message key='submitRefineAnalButton'/>"/>
                        </c:otherwise>
                    </c:choose>
                </form>
            </c:when>
            <c:otherwise>
                <h3><f:message key="stateInfoHeader"/></h3>
                <form action="${pageContext.request.contextPath}/SPZServlet/editspz" method="post">
                    <input type="hidden" name="spzid" value="${spz.id}"/>
                    <input type="hidden" name="userid" value="${user.id}"/>
                    <input type="hidden" name="state" value="REFINE"/>
                    <input type="hidden" name="newstate" value="ANALYSIS"/>
                    <div class="formItem">
                        <span class="label"><label for="desc" class="textarealabel"><f:message key="note"/></span>
                        
                        <span class="input">
                            <textarea class="textareainput" name="desc" rows="5" maxlength="8000"></textarea>
                        </span>
                    </div>
                    <c:if test="${!user.isUser}">`
                        <div class='FormItem'>
                            <span class="label">&nbsp;</span>
                            <span class='input'>
                                <input type="checkbox" name="external"/><label for="external"><f:message key="visible"/></label>
                            </span>
                        </div>
                    </c:if>
                    <input type="hidden" name="configid" value="${config.id}"/>
                    <input type="hidden" name="projectid" value="${project.id}"/>
                    <div class='noIndentFormItem'>
                        <span class='noIndentInput'>
                            <input type="submit" value="<f:message key='submit'/>"/>
                        </span>
                    </div>
                </form>
            </c:otherwise>
        </c:choose>
        <jsp:include page="listHistory.jsp"/>
    </body>
</html>
