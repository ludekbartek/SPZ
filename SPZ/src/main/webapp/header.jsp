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
<div class="leftfloat">
    <span id="logo"><img src="images/DCB_3.jpg" alt="Logo DCB s.r.o." height="100"/></span>
</div>
<div class="rightfloat">
    <form name="useraction" action="${pageContext.request.contextPath}/SPZServlet/editUser" method="post">
    <%--<input type="submit" value="<c:out value='${user.login} (${user.name})'/>" class="linkbutton"/>--%>
        <input type="hidden" name="userid" value="${user.id}"/>
        <a href="javascript: doPost(useraction);"><c:out value="${user.login} (${user.name})"/></a>  
    </form>
</div>

<div class="center">    
<!--<div class="leftfloat">-->
<h2 class="topheader"><f:message key="header"/></h2>
<div class="navigation">
<form id="projects" action="${pageContext.request.contextPath}/SPZServlet/listProjects" method="post" class="navigationform">
   <%-- <input type="submit" value="<f:message key='projects'/>" class="linkbutton"/>--%>
    <input type="hidden" name="userid" value="${user.id}"/>
    <%--<a href="javascript: doPost(projects)"><f:message key="projects"/></a>--%>
</form>
<%--<jsp:include page="navigation.jsp"/>--%>
</div>
</div>
