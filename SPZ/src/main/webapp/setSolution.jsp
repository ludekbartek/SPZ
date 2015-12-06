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
    </head>
    <body>
        <h1><f:message key="stateChange"/></h1>
        <div><f:message key="stateChangeHeader"/></div>
        <jsp:include page="editcommon.jsp"/>
        <div class="text"><f:message key="infoState"/>:</div>
        <form action="${pageContext.request.contextPath}/SPZServlet/editspz">
            <input type="hidden" name="state" value="ANALYSIS"/>
            <input type="hidden" name="newstate" value="SPECIFIED"/>
            <div class="areainput">
                <f:message key="revLabel"/>:
                <textarea name="revisedsolutiondescription"></textarea>
            </div>
            <div class="areainput">
                <f:message key="solDesc"/>:
                <textarea name="solutiondescription"></textarea>
            </div>
            <div class="singleareainput">
                <f:message key="estWork"/>:
                <textarea name="estimatedworkload"></textarea>
            </div>
            <div class="areainput">
                <f:message key="note"/>:
                <textarea name="desc"></textarea>
            </div>
            <input type="checkbox" id="ext"/><label for="ext"><f:message key="extNote"/></label>
            <input type="submit" name="<f:message key="send"/>"/>
        </form>        
        <h1><f:message key="history"/></h1>
        <jsp:include page="listHistory.jsp"/>
    </body>
</html>
