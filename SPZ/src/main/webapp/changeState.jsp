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
    </head>
    <body>
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
            <label for='note'><f:message key="noteLabel"/></label>
            <textarea name='note' cols='80' rows="5" maxlength="8000"></textarea>
            <input type='hidden' name='spzid' value='${spz.id}'/>
            <input type='hidden' name='userid' value='${user.id}'/>
            <input type='hidden' name="state" value='${spzState}'/>
            <input type="hidden" name="newstate" value="${newState}"/>
            <input type="checkbox" name="external"/><label for="external"><f:message key="externalNote"/></label>
            <br/>
            <c:if test="${!empty developers}">
                <label for="developer"><f:message key="developer"/></label>
                <select name="developer">
                    <c:forEach var="developer" items="${developers}">
                        <option><c:out value="${developer.name}"/></option>
                    </c:forEach>
                </select>
            </c:if>
            <input type="submit" value="<f:message key='submit'/>"/>
        </form>
        <jsp:include page="listHistory.jsp"/>
    </body>
</html>
