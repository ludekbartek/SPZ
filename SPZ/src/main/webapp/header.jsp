<%-- 
    Document   : header
    Created on : Jan 9, 2016, 11:20:35 AM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="headers"/>
<link rel="stylesheet" href="styles/dcb.css"/>
<div class="rightfloat">
    <form name="useraction" action="${pageContext.request.contextPath}/SPZServlet/editUser" method="post">
    <%--<input type="submit" value="<c:out value='${user.login} (${user.name})'/>" class="linkbutton"/>--%>
        <input type="hidden" name="userid" value="${user.id}"/>
        <a href="javascript: doPost(useraction);"><c:out value="${user.login} (${user.name})"/></a>  
    </form>
</div>

<div class="header"><f:message key="header"/></div>

<div class="leftfloat">
<form id="projects" action="${pageContext.request.contextPath}/SPZServlet/listProjects" method="post" class="navigationform">
   <!-- <input type="submit" value="<f:message key='projects'/>" class="linkbutton"/>-->
    <input type="hidden" name="userid" value="${user.id}"/>
    <a href="javascript: doPost(projects)"><f:message key="projects"/></a>
</form>
</div>
