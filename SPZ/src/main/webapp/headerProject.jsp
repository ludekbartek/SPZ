<%-- 
    Document   : projectHeader
    Created on : Jan 9, 2016, 11:19:09 AM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<sql:setDataSource driver="org.apache.derby.jdbc.ClientDriver" 
                   url="jdbc:derby://localhost:1527/support"
                   user="suser"
                   password="suser"/>
<c:set var="projectName" value="BT"/>
<c:if test="${not empty $spz.projectid}">
<sql:query sql='select name from SPZ.PROJECT where id=${spz.projectid}' var="projectName"/>
</c:if>
<jsp:include page="header.jsp"/>
&gt;&gt;
<form action="${pageContext.request.contextPath}/SPZServlet/listConfigurations" method="post">
    <input type="hidden" name="projectid" value="${spz.projectid}"/>
    <input type="hidden" name="userid" value="${user.id}"/>
    <input type="submit" value="${projectName}"/>
</form>