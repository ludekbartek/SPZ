<%-- 
    Document   : editAnalyst
    Created on : Jan 5, 2016, 8:16:43 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="editAnalyst"/>
<f:setBundle basename="changeState" var="change"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><f:message key="pagetitle"/> <c:out value="${spz.id}"/> (<c:out value="${user.login}"/>)</title>
        <link rel="stylesheet" href="styles/dcb.css" type="text/css"/>
        <script type="text/javascript" src="scripts/support-scripts.js"></script>
    </head>
    <body>
        <jsp:include page="headerSpzEdit.jsp"/>
        <div class="center">
            <jsp:include page="navigationSpzEdit.jsp"/>
        </div>
        <!--<h1><f:message key="pagetitle"/></h1>-->
            <h1><f:message key="stateChangeHeader"/></h1>
        <div class="highlighted">
            <f:message key="request" bundle="${change}"/> '<f:message key="${newState}" bundle="${change}"/>' na SPZ:
        </div>
        <div id="info">
        <table class="border-fullwidthtable">
            <jsp:include page="editcommon.jsp"/>
        </table>
        <p>
            <form action="${pageContext.request.contextPath}/SPZServlet/editSpz" method="post">
            <div class="formItem">
                <span class="label">
                    <label class="textarealabel" for="desc"><f:message key="descLabel"/></label>
                </span>
                <span class="input">
                    <textarea id="desc" class="textareainput" name="desc" rows="5"></textarea>
                </span>
            </div>
           <div class="formItem">
               <span class="noteInput">
                    <input class="noteInput" type="checkbox" name="external"/> <label for="external"><f:message key="externalNote"/></label>
                </span>
            </div>
            <div>&nbsp;</div>
            <div class="formItem">
             <span class="label">
                 <label for="analyst"><f:message key="analystLabel"/></label>
             </span>
             <span class="input">
                <select name="analyst">
                    <c:forEach var="analyst" items="${analysts}">
                        <option value="<c:out value='${analyst.id}'/>"><c:out value="${analyst.name}"/></option>
                    </c:forEach>
                </select>
             </span>
            </div>
            <div class="formItem">
                <span class="noIndentInput">
                    <input type="submit" value="<f:message key='submit'/>"/>
                </span>
            </div>
            <input type="hidden" name="newstate" value="${newState}"/>
            <input type="hidden" name="userid" value="${user.id}"/>
            <input type="hidden" name="spzid" value="${spz.id}"/>
            <input type="hidden" name="configid" value="${config.id}"/>
            <input type="hidden" name="projectid" value="${project.id}"/>
         </form>
        </div>
        <jsp:include page="listHistory.jsp"/>
    </body>
</html>
