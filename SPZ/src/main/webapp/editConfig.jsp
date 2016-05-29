<%-- 
    Document   : editConfig - Uprava konfigurace a uzivatelskych prav
    Created on : May 29, 2016, 7:01:21 AM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="configs"/>

<!DOCTYPE html>
<html>
    <head>
        <link type="text/css" rel="stylesheet" href="styles/dcb.css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><f:message key="configTitle"/></title>
    </head>
    <body>
        <div id="left">
            <ul>
            <c:forEach var="project" items="${projects}">
                <li>
                    <form action="${pageContext.request.contextPath}/SPZServlet/editproject" method="post">
                        <input type="hidden" name="userid" value="${user.id}"/>
                        <input type="hidden" name="projectid" value="${project.id}"/>
                        <input type="submit" value="${project.name}"/>
                    </form>
                    <ul>
                        <c:forEach var="config" items="${project.configs}">
                            <li>
                                <form action="${pageContext.request.contextPath}/SPZServlet/editconfig" method="post">
                                    <input type="hidden" name="userid" value="${user.id}"/>
                                    <input type="hidden" name="projectid" value="${project.id}"/>
                                    <input type="hidden" name="configid" value="${config.id}"/>
                                    <input type="submit" value="${config.name}"/>
                                </form>
                            </li>
                        </c:forEach>
                    </ul>
                </li>
            </c:forEach>
            </ul>    
        </div>
        <div id="content">
            <h1><f:message key="headerTop"/></h1>
            <jsp:include page="header.jsp"/>
            <div class="editConfig">
                <h2><f:message key="editConfig"> <c:out value="${project.name}"/>/<c:out value="${config.name}"/></f:message></h2>
                <form action="${pageContext.request.contextPath}/SPZServlet/editconfig" method="post">
                    <input type="hidden" name="userid" value="${user.id}"/>
                    <input type="hidden" name="projectid" value="${project.id}"/>
                    <input type="hidden" name="configid" value="${config.id}"/>
                    <div class="input">
                        <div class="desc">
                            <label for="description"><f:message key="desc"/>:</label>
                        </div>
                        <div class="input">
                        <textarea cols="128" rows="4" name="description">
                            <c:out value="${config.description}"/>
                        </textarea>
                        </div>
                    </div>
                    <div class="submit">
                        <input type="submit" value="<f:message key='submitDesc'/>"/>
                    </div>
                </form>
            </div>
            <div class="addAccess">
                <h2><f:message key="newUser"/></h2>
                <form action="${pageContext.request.contextPath}/addRole">
                    <div class="input">
                        <label for="userSelect"><f:message key="login"/>:</label>
                        <select name="userid">
                            <c:forEach var="user" items="${users}">
                                <option value="${user.id}"><c:out value="${user.name}"/> (<c:out value="${user.login}"/>)</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="input">
                        <label for="roleSelect"><f:message key="role"/>:</label>
                        <select name="roleid">
                            <option value="3"><f:message key="admin"/></option>
                            <option value="2"><f:message key="projectManager"/></option>
                            <option value="1"><f:message key="developer"/></option>
                            <option value="0"><f:message key="user"/></option>
                        </select>
                    </div>
                    <div class="input">
                        <input type="submit" value="<f:message key='addUser'/>"/>
                    </div>
                </form>
            </div>
            <div id="usersList">
                <h2><f:message key="users"/></h2>
                <table class="fullwidthtable">
                    <thead>
                        <tr>
                            <th><f:message key="login"/></th>
                            <th><f:message key="name"/></th>
                            <th><f:message key="role"/></th>
                            <th/>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="user" items="${users}">
                            <tr>
                                <td><c:out value="${user.login}"/></td>
                                <td><c:out value="${user.name}"/></td>
                                <td><f:message key="role${user.role}"/></td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/deleteRole" method="post">
                                        <input type="hidden" name="userid" value="${user.id}"/>
                                        <input type="hidden" name="role" value="${user.role}"/>
                                        <input type="hidden" name="projectid" value="${project.id}"/>
                                        <input type="hidden" name="configid" value="${config.id}"/>
                                        <input type="submit" value="<f:message key='deleteRole'/>"/>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
