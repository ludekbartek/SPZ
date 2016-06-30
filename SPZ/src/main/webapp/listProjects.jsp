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
        <script type="text/javascript" src="scripts/support-scripts.js"></script>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <%--<div class="center"><jsp:include page="navigation.jsp"/></div>--%>
        <h2 class="center"><f:message key="availProjects"/></h2>
        <table  class="fullwidthtable">
            <thead>
                <tr>
                    <td class="code"><f:message key="code"/></td>
                    <td class="desc"><f:message key="desc"/></td>
                    
                    <c:if test="${user.classType==3}">
                        <td class="actions"><f:message key="projectActions"/></td>
                        
                    </c:if>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="project" items="${projects}">
                    <tr>
                        <td>
                            <form name="prj${project.id}" action="${pageContext.request.contextPath}/SPZServlet/listconfigurations" method="post">
                                <input type="hidden" name="projectid" value="${project.id}"/>
                                <input type="hidden" name="userid" value="${user.id}"/>
                                <%--<input type="submit" value="${project.name}"/>--%>
                                <a href="javascript: doPost(prj${project.id})">${project.name}</a>
                            </form>
                        </td>
                        <td>
                            <c:out value="${project.description}"/>
                        </td>
                        <%--Pouze pro admina --%>
                        <c:if test="${user.classType==3}">
                        <td>
                            <form action="${pageContext.request.contextPath}/SPZServlet/editProject" method="post">
                                <input type="hidden" name="projectid" value="${project.id}"/>
                                <input type="hidden" name="userid" value="${user.id}"/>
                                <input type="submit" value="<f:message key='editProject'/>"/>
                            </form>
                        </td>
                        </c:if>
                        <%-- --%>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    <%-- Pouze pro admina --%>
    <c:if test="${user.classType==3}">           
        <h1><f:message key="newProject"/></h1>
        <div id="left">
            <form action="${pageContext.request.contextPath}/SPZServlet/addProject" method="post">
                <div id="projectName">
                    <label for="name"><f:message key="projectCode"/>: </label>
                    <input type="text" name="name" value="" size="50"/>
                </div>
                <div id="projectDesc">
                    <label for="description"><f:message key="projectDesc"/>: </label>
                    <textarea name="description" cols="64" rows="4"></textarea>
                </div>
                <input type="hidden" name="userid" value="${user.id}"/>
                <input type="submit" value="<f:message key='addProject'/>"/>
            </form>
        </div>
    </c:if>
    <%-- --%>
        
    </body>
</html>
