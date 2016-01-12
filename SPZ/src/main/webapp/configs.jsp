<%-- 
    Document   : configs
    Created on : Jan 9, 2016, 3:39:54 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="projects"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><f:message key="confHeader"/>(${user.login})</title>
        <link rel="stylesheet" href="styles/dcb.css" type="text/css"/>
        
        <c:if test="${cfgs.length<=1}">
            <meta http-equiv="Refresh" content="0; URL=${pageContext.request.contextPath}/SPZServlet/listspz"/>
        </c:if>
    </head>
    <body>
        <jsp:include page="headerProject.jsp"/>
        <h1><f:message key="installs"/></h1>
        <table class="doubleValueList">
            <thead>
                <tr>
                    <td><f:message key="code"/></td>
                    <td><f:message key="desc"/></td>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="cfg" items="${cfgs}">
                    <tr>
                        <td class="id">
                            <form action="${pageContext.request.contextPath}/SPZServlet/listSpz" method="post">
                                <input type="hidden" name="userid" value="${user.id}"/>
                                <input type="hidden" name="cfgid" value="${cfg.id}"/>
                                <input type="submit" value="${cfg.id}"/>
                            </form>
                        </td>
                        <td class="desc">
                            <c:out value="${cfg.desc}"/>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
