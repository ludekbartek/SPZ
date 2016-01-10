<%-- 
    Document   : commonheader
    Created on : Jan 9, 2016, 10:56:28 AM
    Author     : bar
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%--<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<sql:setDataSource driver="org.apache.derby.jdbc.ClientDriver"
                   url="jdbc:derby://localhost:1527/support"
                   user="suser"
                   password="suser" var="data"/>
<c:set var="confName" value="cfg1"/>
<c:if test="${not empty spz.configId}">
    <sql:query var="confName" sql="select code from SUSER.CONFIGURATION where id=${spz.configId}"/>
</c:if>
<jsp:include page="headerProject.jsp"/>
&gt;&gt;
<form action="${pageContext.request.contextPath}/SPZServlet/listspz" method="post">
    <input type="hidden" name="projectid" value="${spz.projectId}"/>
    <input type="hidden" name="configid" value="${spz.configId}"/>
    <input type="hidden" name="spzid" value="${spz.id}"/>
    <input type="submit" value="${confName}"/>
    <input type="hidden" name="userid" value="${user.id}"/>
</form>