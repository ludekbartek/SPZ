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
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><f:message key="stateChange"/> <c:out value="${spz.id}"/></title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/dcb.css" type="text/css"/>
    </head>
    <body>
        <jsp:include page="headerSpzEdit.jsp"/>
        <h1><f:message key="stateChange"/></h1>
        <div><f:message key="stateChangeHeader"/></div>
        <table class="infotable">
            <jsp:include page="editcommon.jsp"/>
        </table>
        <div class="text"><f:message key="infoState"/>:</div>
        <form action="${pageContext.request.contextPath}/SPZServlet/editspz" method="post">
            <input type="hidden" name="state" value="ANALYSIS"/>
            <input type="hidden" name="newstate" value="SPECIFIED"/>
            <input type="hidden" name="jsp" value="listSpz.jsp"/>
            <input type="hidden" name="spzid" value="${spz.id}"/>
            <input type="hidden" name="userid" value="${user.id}"/>
            <input type="hidden" name="configid" value="${config.id}"/>
            <input type="hidden" name="projectid" value="${project.id}"/>
            <div class="areainput">
                <f:message key="revLabel"/>:
                <textarea name="revisedrequest"><c:if test="${not empty spz.requestDescription}"><c:out value="${spz.requestDescription}"/></c:if></textarea>
            </div>
            <div class="areainput">
                <f:message key="solDesc"/>:
                <textarea name="solutiondescription"></textarea>
            </div>
            <div class="singleareainput">
                <f:message key="estWork"/>:
                <input type="text" name="estimatedworkload" maxlength="5"/>
            </div>
                <input type="submit" value="<f:message key='submit'/>"/>
        </form>
        <jsp:include page="addNote.jsp"/>
        <h1><f:message key="history"/></h1>
        <jsp:include page="listHistory.jsp"/>
    </body>
</html>
