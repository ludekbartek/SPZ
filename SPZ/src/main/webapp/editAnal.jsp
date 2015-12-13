<%-- 
    Document   : editAnalysis
    Created on : Oct 4, 2015, 1:30:13 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="analysis"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><f:message key="header"/> <c:out value="${spz.id}"/><c:if test="${not empty spz.issuer}">(${spz.issuer})</c:if></title>
    </head>
    <body>
    
    <h1><f:message key="header"/></h1>
    <jsp:include page="editcommon.jsp"/>
    <c:if test="${user.role!='0'}">
        <c:set var="jsp" value="./editAnal.jsp"/>
        <form action="${pageContext.request.contextPath}/SPZServlet/editspz" method="post">
            <input type="hidden" name="state" value="ANALYSIS"/>
            <input type="hidden" name="newstate" value="REFINE"/>
            <input type="hidden" name="spzid" value="${spz.id}"/>
            <input type="hidden" name="userid" value="${user.id}"/>
            <input type="submit" value="Vratit zpet klientovi k doplneni"/>
        </form>
        <form action="${pageContext.request.contextPath}/SPZServlet/spzsolution" method="post">
            <input type="hidden" name="state" value="ANALYSIS"/>
            <input type="hidden" name="newstate" value="ANALYSIS"/>
            <input type="hidden" name="spzid" value="${spz.id}"/>
            <input type="hidden" name="userid" value="${user.id}"/>
            <input type="submit" value="Navrhnout reseni"/>
        </form>
        <form action="${pageContext.request.contextPath}/SPZServlet/changeanalyst" method="post">
            <input type="hidden" name="state" value="ANALYSIS"/>
            <input type="hidden" name="newstate" value="ANALYSIS"/>
            <input type="hidden" name="spzid" value="${spz.id}"/>
            <input type="hidden" name="userid" value="${user.id}"/>
            <input type="submit" value="Zmenit analytika"/>
        </form>
        <form action="${pageContext.request.contextPath}/SPZServlet/delete" method="post">
            <input type="submit" value="Zrusit"/>
            <input type="hidden" name="newstate" value="CANCELED"/>
            <input type="hidden" name="spzid" value="${spz.id}"/>
            <input type="hidden" name="userid" value="${user.id}"/>
            <input type="hidden" name="state" value="ANALYSIS"/>
        </form>
    </c:if>
    <jsp:include page="listHistory.jsp"/>
    </body>
</html>
