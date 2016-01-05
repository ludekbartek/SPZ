<%-- 
    Document   : editAnalyst
    Created on : Jan 5, 2016, 8:16:43 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="editAnalyst"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><f:message key="pagetitle"/> <c:out value="${spz.id}"/> (<c:out value="${user.login}"/>)</title>
    </head>
    <body>
        <h1><f:message key="pagetitle"/></h1>
        <table>
            <jsp:include page="editcommon.jsp"/>
        </table>
        <form action="${pageContext.request.contextPath}/SPZServlet/changeAnalyst" method="post">
            <label for="desc"><f:message key="descLabel"/></label>
            <textarea nam="desc" cols="80" rows="5"></textarea>
            <label for="analyst"><f:message key="analystLabel"/></label>
            <select name="analyst">
                <c:forEach var="analyst" items="${analysts}">
                    <option value="<c:out value='${analyst.id}'/>"><c:out value="${analyst.name}"/></option>
                </c:forEach>
            </select>
            <input type="checkbox" name="external"/><label for="external"><f:message key="externalNote"/></label>
            <input type="submit" value="<f:message key='submit'/>"/>
            <input type="hidden" name="userid" value="${user.id}"/>
            <input type="hidden" name="spzid" value="${spz.id}"/>
        </form>
        <jsp:include page="listHistory.jsp"/>
    </body>
</html>
