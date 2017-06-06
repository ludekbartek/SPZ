<%-- 
    Document   : changeState
    Created on : Dec 30, 2015, 9:49:48 AM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<f:setBundle basename="changeState"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><f:message key="stateChangeHeader"/> ${spz.id}</title>
        <link rel="stylesheet" href="styles/dcb.css" type="text/css"/>
        <script type="text/javascript" src="scripts/support-scripts.js"></script>
    </head>
    <body>
        <jsp:include page="headerSpzEdit.jsp"/>
        <div class="center">
            <jsp:include page="navigationSpzEdit.jsp"/>
        </div>
        <h1><f:message key="stateChangeHeader"/></h1>
        <div class="highlighted">
            <f:message key="request"/> '<f:message key="${newState}"/>' na SPZ:
        </div>
        <c:set var="jsp" value="./changeState.jsp"/>
        <c:set var="startStateLab[]" value="{'start'}"/>
        <table class="border-fullwidthtable">
            <jsp:include page="editcommon.jsp"/>
            <c:if test="${spz.spzState=='IMPLEMENTATION' || spz.spzState=='DCB_ACCEPTED'}">
                <tr>
                    <td><f:message key='solutionDesc'/>:</td>
                    <td><c:out value="${spz.solution}" escapeXml="false"/></td>
                </tr>
                <tr>
                    <td><f:message key="workLoadEst"/>:</td>
                    <td><c:out value="${spz.workLoadEstimation}"/> <f:message key="manHours"/>
                        (<f:formatNumber maxFractionDigits="2" value="${spz.workLoadEstimation/8.0}"/>
                        <f:message key="manDays"/>)
                    </td>
                </tr>
            </c:if>
        </table>
        <h3><f:message key="stateInfoFor"/> '<f:message key='${newState}'/>'</h3>
        <form action='${pageContext.request.contextPath}/SPZServlet/editspz' method="post">
            <div class="formItem">
                <span class="label">
                    <label class="textarealabel" for='note'><f:message key="noteLabel"/></label>
                </span>
                <span class="input">
                    <textarea class="textareainput" name='note'  rows="5" maxlength="8000"></textarea>
                </span>
            </div>
            <input type='hidden' name='spzid' value='${spz.id}'/>
            <input type='hidden' name='userid' value='${user.id}'/>
            <input type='hidden' name="state" value='${spzState}'/>
            <input type="hidden" name="newstate" value="${newState}"/>
            <input type="hidden" name="configid" value="${config.id}"/>
            <input type="hidden" name="projectid" value="${project.id}"/>
            <div class="formItem">
            <c:choose>
                <c:when test="${user.role!=0}">
                    <span class="label">&nbsp;</span>
                    <span class="input"><input type="checkbox" name="external"/><label for="external"><f:message key="externalNote"/></label></span>
                </c:when>
                <c:otherwise>
                    <input type="hidden" name="external" value="1"/>
                </c:otherwise>
            </c:choose>
            </div>
            <c:if test="${!empty developers}">
                <div class="formItem">
                <span class="label">
                    <label for="developer"><f:message key="developer"/></label>
                </span>
                <span class="input">
                    <select name="developer">
                        <c:forEach var="developer" items="${developers}">
                            <option value="${developer.login}"><c:out value="${developer.name}"/></option>
                        </c:forEach>
                    </select>
                </span>
            </c:if>
            <input type="submit" value="<f:message key='submit'/>"/>
        </form>
        <jsp:include page="listHistory.jsp"/>
    </body>
</html>
