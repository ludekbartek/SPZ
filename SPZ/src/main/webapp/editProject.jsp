<%-- 
    Document   : editProject
    Created on : May 26, 2016, 7:40:55 PM
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
        <title><f:message key="projectDefinition"/></title>
        <script type="text/javascript" src="scripts/support-scripts.js"></script>
    </head>
    <body>
        <jsp:include page="headerProject.jsp"/>
        <div id="left">
            <ul>
                <c:forEach var="projectItem" items="${projects}">
                    <li><form action="${pageContext.request.contextPath}/SPZServlet/editProject" method="post">
                            <input type="hidden" name="projectid" value="${projectItem.id}"/>
                            <input type="hidden" name="userid" value="${user.id}"/>
                            <div class="id">
                                <label for="name"><f:message key="projectCode"/></label>
                                <input type="text" name="name" value="<c:if test='${not empty $project}'>${project.name}</c:if>"/>
                            </div>
                            <div id="desc">
                                <label for="description"><f:message key="projectDesc"/></label>
                                <textarea name="description" cols="64" rows="4"><c:if test="${not empty $project}"><c:out value="${project.description}"/></c:if></textarea>
                            </div>
                            <input type="submit" value="${projectItem.name}"/>
                        </form>
                        <c:if test="${not empty projectItem.configs}">
                            <ul>
                                <c:forEach var="config" items="${projectItem.configs}">
                                    <li>
                                        <form action="${pageContext.request.contextPath}/SPZServlet/editConfig" method="post">
                                            <input type="hidden" name="projectid" value="${projectItem.id}"/> 
                                            <input type="hidden" name="configid" value="${config.id}"/>
                                            <input type="hidden" name="userid" value="${user.id}"/>
                                            <input type="submit" value="${config.name}"/>
                                        </form>
                                    </li>
                                </c:forEach>
                        </ul>
                    </c:if>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <div id="content">
            <h1><f:message key="projectEdit"/> <c:out value="${project.name}"/></h1>
            <form action="${pageContext.request.contextPath}/SPZServlet/editProject" method="post">
                <input type="hidden" name="projectid" value="${project.id}"/>
                <input type="hidden" name="userid" value="${user.id}"/>
                <div class="id">
                    <label for="name"><f:message key="projectCode"/></label>
                    <input type="text" name="name" value="<c:if test='${not empty project}'>${project.name}</c:if>"/>
                </div>
                <div id="desc">
                    <label for="description"><f:message key="projectDesc"/></label>
                    <textarea name="description" cols="64" rows="4"><c:if test="${not empty project}"><c:out value="${project.description}"/></c:if></textarea>
                </div>
                <input type="submit" value="<f:message key='changeDesc'/>"/>
            </form>
            <h2><f:message key="projectConfs"/></h2>
            <c:if test="${not empty project.configs}">
            
            <table class="fullwidthtable">
                <thead>
                    <th><f:message key="code"/></th>
                    <th><f:message key="desc"/></th>
                    <th/>
                </thead>
                <tbody>
                    <c:forEach var="config" items="${project.configs}">
                        <tr>
                            <td>
                                <form action="${pageContext.request.contextPath}/SPZServlet/editConfig" method="post">
                                    <input type="hidden" name="userid" value="${user.id}"/>
                                    <input type="hidden" name="projectid" value="${project.id}"/>
                                    <input type="hidden" name="configid" value="${config.id}"/>
                                    <input type="submit" value="${config.name}"/>
                                </form>
                            </td>
                            <td><c:out value="${config.description}"/></td>
                            <td>
                                <form action="${pageContext.request.contextPath}/SPZServlet/deleteCfg" method="post">
                                    <input type="hidden" name="userid" value="${user.id}"/>
                                    <input type="hidden" name="projectid" value="${project.id}"/>
                                    <input type="hidden" name="cfgid" value="${config.id}"/>
                                    <input type="submit" value="<f:message key='deletecfg'/>"/>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            </c:if>        
            <h2><f:message key="newConfig"/></h2>
            <form action="${pageContext.request.contextPath}/SPZServlet/addConfig" method="post">
                <input type="hidden" name="userid" value="${user.id}"/>
                <input type="hidden" name="projectid" value="${project.id}"/>
                <div class="confName">
                    <label for="confName"><f:message key="code"/>: </label>
                    <input type="text" name="confName" size="22"/>
                </div>
                <div class="confDesc">
                    <label for="confDesc"><f:message key="desc"/>: </label>
                    <textarea name="confDesc" rows="4" cols="64"></textarea>
                </div>   
                <input type="submit" value="<f:message key='newConfig'/>"/>    
            </form>
        </div>
    </body>
</html>
