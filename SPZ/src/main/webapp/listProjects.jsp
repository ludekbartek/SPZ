<%-- 
    Document   : listProjects
    Created on : Jan 10, 2016, 11:18:36 AM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<f:setBundle basename="list"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><f:message key="projectList"/>(<c:out value="${user.login}"/>)</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/dcb.css" type="text/css"/>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <h1><f:message key="availProjects"/></h1>
        <table  class="fullwidthtable">
            <thead>
                <tr>
                    <td class="code"><f:message key="code"/></td>
                    <td class="desc"><f:message key="desc"/></td>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="project" items="${projects}">
                    <tr>
                        <td>
                            <form action="${pageContext.request.contextPath}/SPZServlet/listconfigurations" method="post">
                                <input type="hidden" name="projectid" value="${project.id}"/>
                                <input type="hidden" name="userid" value="${user.id}"/>
                                <input type="submit" value="${project.name}"/>
                            </form>
                        </td>
                        <td>
                            <c:out value="${project.description}"/>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
