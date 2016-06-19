<%-- 
    Document   : changeState
    Created on : Dec 30, 2015, 9:49:48 AM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <h1><f:message key="stateChangeHeader"/></h1>
        <div class="highlighted">
            <f:message key="request"/> '<f:message key="${newState}"/>' na SPZ:
        </div>
        <c:set var="jsp" value="./changeState.jsp"/>
        <table>
            <jsp:include page="editcommon.jsp"/>
        </table>
        <h2><f:message key="stateInfo"/> '<f:message key="${newState}"/>'</h2>
        <form action='${pageContext.request.contextPath}/SPZServlet/editspz' method="post">
            <div class="areainput">
                <label for="relnotes"><f:message key="relnotes"/></label>
                <textarea name="relnotes" cols="80" rows="5" maxlength="9000"></textarea>
            </div>
            <div class="textinput">
                <label for="mandays"><f:message key="mandays"/></label>
                <input type="text" name="mandays" value="${spz.workLoadEstimation}"/>
            </div>
            <div class="textinput">
                <label for="outofdevel"><f:message key="outofdevel"/></label>
                <input type="text" name="outofdevel" value="0.0"/>
            </div>
            <div class="areainput">
                <label for='note'><f:message key="noteLabel"/></label>
                <textarea name='note' cols='80' rows="5" maxlength="8000"></textarea>
            </div>
            <input type='hidden' name='spzid' value='${spz.id}'/>
            <input type='hidden' name='userid' value='${user.id}'/>
            <input type='hidden' name="state" value='${spzState}'/>
            <input type="hidden" name="newstate" value="${newState}"/>
            <input type="hidden" name="configid" value="${config.id}"/>
            <input type="hidden" name="projectid" value="${project.id}"/>
            <input type="checkbox" name="external"/><label for="external"><f:message key="externalNote"/></label>
            <br/>
            <input type="submit" value="<f:message key='submit'/>"/>
        </form>
        <jsp:include page="listHistory.jsp"/>
    </body>
</html>
