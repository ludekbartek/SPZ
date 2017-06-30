<%-- 
    Document   : setSolution
    Created on : Nov 21, 2015, 12:00:04 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>

<html>
    <f:setBundle basename="setsolution"/>
    <f:setBundle basename="addNote" var="addNote"/>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><f:message key="stateChange"/> <c:out value="${spz.id}"/></title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/dcb.css" type="text/css"/>
        <script type="text/javascript" src="scripts/support-scripts.js"></script>
    </head>
    <body>
        <jsp:include page="headerSpzEdit.jsp"/>
        <div class="center">
            <jsp:include page="navigationSpzEdit.jsp"/>
        </div>
        <h1><f:message key="stateChange"/></h1>
        <div class="highlighted"><f:message key="stateChangeHeader"/></div>
        <table class="infotable">
            <jsp:include page="editcommon.jsp"/>
        </table>
        <h3><f:message key="infoState"/>:</h3>
        <form action="${pageContext.request.contextPath}/SPZServlet/editspz" method="post">
            <input type="hidden" name="state" value="ANALYSIS"/>
            <input type="hidden" name="newstate" value="SPECIFIED"/>
            <input type="hidden" name="jsp" value="listSpz.jsp"/>
            <input type="hidden" name="spzid" value="${spz.id}"/>
            <input type="hidden" name="userid" value="${user.id}"/>
            <input type="hidden" name="configid" value="${config.id}"/>
            <input type="hidden" name="projectid" value="${project.id}"/>
            <div class="formItem">
            <span class="label">
                <label for="revisedrequest" class="textarealabel"><f:message key="revLabel"/>:</label>
            </span>
            <span class="input">
                <textarea class="textareainput" name="revisedrequest" rows="5" maxlength="8000"><c:if test="${not empty spz.requestDescription}"><c:out value="${spz.requestDescription}"/></c:if></textarea>
            </span>
            </div>
            <div class="formItem">
                <span class="label">    
                    <label for="solutiondescription" class="textareainput"><f:message key="solDesc"/>:</label>
                </span>
                <span class="input">
                    <textarea class="textareainput" rows="5" maxlength="8000" name="solutiondescription"></textarea>
                </span>
            </div>
                
            <div class="formItem">
                <span class="label">
                    <f:message key="estWork"/>:
                </span>
                <span class="textinput">
                    <input class="textinput" type="text" name="estimatedworkload"  maxlength="5" size="69"/>
                </span>
            </div>
            
            <div class="formItem">
                <span class="label">
                    <label class="textarealabel" for="desc"><f:message key="note"/>:</label>
                </span>
                <span class="input">
                    <textarea class="textareainput" rows="5" maxlength="8000" name="desc"></textarea>
                </span>
            </div>
            <c:if test="${!user.isUser}">
                <div class="formItem">
                    <span class="label">
                        &nbsp;
                    </span>
                    <span class="input">
                        <input type="checkbox" name="external"/><label for="external"><f:message key="extNote" bundle="${addNote}"/></label>
                    </span>
                </div>
            </c:if>
            <div class="formItem">
                <div class="noIndentInput">
                    <input type="submit" value="<f:message key='submit'/>"/>
                </div>
            </div>
            
        </form>
        <%--<jsp:include page="addNote.jsp"/>--%>
        <div>
        <!--<h1><f:message key="history"/></h1>-->
        <jsp:include page="listHistory.jsp"/>
        </div>
    </body>
</html>
