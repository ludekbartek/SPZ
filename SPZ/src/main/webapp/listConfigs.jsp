<%-- 
    Document   : listConfigs
    Created on : Jan 10, 2016, 10:16:04 AM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="list"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><f:message key="confList"/><c:out value="${user.login}"/></title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/dcb.css" type="text/css"/>
    </head>
    <body>
        <jsp:include page="headerProject.jsp"/>
        <h1><f:message key="availConfs"/></h1>
        <table class="fullwidthtable">
            <thead>
                <tr>
                    <td style="width: 20%;"><f:message key="code"/></td>
                    <td style="width: 80%;"><f:message key="desc"/></td>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="conf" items="${configs}">
                    <tr>
                        <td>
                            <form action="${pageContext.request.contextPath}/SPZServlet/listspz" method="post">
                                <input type="hidden" name="projectid" value="${project.id}"/>
                                <input type="hidden" name="configid" value="${conf.id}"/>
                                <input type="hidden" name="userid" value="${user.id}"/>
                                <input type="submit" value="${conf.code}"/>
                                <%--<c:out value="${conf.code}"/> --%>
                            </form>
                        </td>
                        <td><c:out value="${conf.description}"/></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
