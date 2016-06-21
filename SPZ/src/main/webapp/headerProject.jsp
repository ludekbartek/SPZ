<%-- 
    Document   : projectHeader
    Created on : Jan 9, 2016, 11:19:09 AM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<%-- <sql:setDataSource driver="org.apache.derby.jdbc.ClientDriver" 
                   url="jdbc:derby://localhost:1527/support"
                   user="suser"
                   password="suser"/>
--%>

<jsp:include page="header.jsp"/>
&gt;&gt;<a href="javascript: doPost(configs)"><c:out value="${project.name}"/></a>

<form name="configs" action="${pageContext.request.contextPath}/SPZServlet/listConfigurations" method="post" class="navigationform">
    <input type="hidden" name="projectid" value="${project.id}"/>
    <input type="hidden" name="userid" value="${user.id}"/>
    <input type="hidden" name="spzid" value="${spz.id}"/>
   <%-- <input type="submit" value="${project.name}" class="linkbutton"/>--%>
   <%--<a href="javascript: doPost(configs)"><c:out value="${project.name}"/></a>--%>
</form>

