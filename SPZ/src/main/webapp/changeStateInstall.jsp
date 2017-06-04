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
        <div class="center">
            <jsp:include page="navigationSpzEdit.jsp"/>
        </div>
        <h1><f:message key="stateChangeHeader"/></h1>
        <div class="highlighted">
            <f:message key="request"/> '<f:message key="${newState}"/>' na SPZ:
        </div>
        <c:set var="jsp" value="./changeState.jsp"/>
        <table class="border-fullwidthtable">
            <jsp:include page="editcommon.jsp"/>
        </table>
        <h3><f:message key="stateInfo"/> '<f:message key="${newState}"/>'</h3>
        <form action='${pageContext.request.contextPath}/SPZServlet/editspz' method="post">
            <div class="formItem">
                <span class="textarealabel">
                    <label for="relnotes"><f:message key="relnotes"/></label>
                </span>
                <span class="textareainput">
                    <textarea name="relnotes" <%--cols="80"--%> rows="8" maxlength="9000"></textarea>
                </span>
            </div>
            <div class="formItem">
                <span class="label">
                    <label for="mandays"><f:message key="mandays"/></label>
                </span>
                <span class="input">
                    <input type="text" name="mandays" value="${spz.workLoadEstimation}"/>
                </span>
            </div>
            <div class="formItem">
                <span class="label">
                    <label for="outofdevel"><f:message key="outofdevel"/></label>
                </span>
                <span class="input">
                    <input type="text" name="outofdevel" value="0.0"/>
                </span>
            </div>
            <div class="formItem">
                <span class="textarealabel">
                    <label for='note'><f:message key="noteLabel"/></label>
                </span>
                <span class="textareainput">
                    <textarea name='note' <%--cols='80'--%> rows="5" maxlength="8000"></textarea>
                </span>
            </div>
            <input type='hidden' name='spzid' value='${spz.id}'/>
            <input type='hidden' name='userid' value='${user.id}'/>
            <input type='hidden' name="state" value='${spzState}'/>
            <input type="hidden" name="newstate" value="${newState}"/>
            <input type="hidden" name="configid" value="${config.id}"/>
            <input type="hidden" name="projectid" value="${project.id}"/>
            <div class="formItem">
                <span class="noIndentInput">
                    <input type="checkbox" name="external"/><label for="external"><f:message key="externalNote"/></label>
                </span>
            </div>
            <div class="formItem">
            <span class="noIndentInput">
                <input type="submit" value="<f:message key='submit'/>"/>
            </span>
            </div>
        </form>
        <jsp:include page="listHistory.jsp"/>
    </body>
</html>
