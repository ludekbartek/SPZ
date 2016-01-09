<%-- 
    Document   : projects
    Created on : Jan 9, 2016, 3:31:28 PM
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
        <title><f:message key="projectsHeader"/>(<c:out value="${user.login}"/>)</title>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <h1><f:message key="projectsHeader"/></h1>
        <table class="projects">
            <thead>
                <tr>
                    <td class="code"><f:message key="code"/></td>
                    <td class="name"><f:message key="name"/></td>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="project" items="${projects}">
                    <tr>
                        <td class="code"><c:out value="${project.code}"/></td>
                        <td class="name"><c:out value="${project.name}"/></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
