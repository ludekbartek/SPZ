<%-- 
    Document   : commonheader
    Created on : Jan 9, 2016, 10:56:28 AM
    Author     : bar
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%--<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>

<jsp:include page="headerProject.jsp"/>
&gt;&gt;<a href="javascript: doPost(editconfig)">${config.code}</a>
<form id="editconfig" action="${pageContext.request.contextPath}/SPZServlet/listspz" method="post" class="navigationform">
    <input type="hidden" name="projectid" value="${project.id}"/>
    <input type="hidden" name="configid" value="${config.id}"/>
    <input type="hidden" name="spzid" value="${spz.id}"/>
    <%--<input type="submit" value="${config.code}" class="linkbutton"/>--%>
    <%--<a href="javascript: doPost(editconfig)">${config.code}</a>--%>
    <input type="hidden" name="userid" value="${user.id}"/>
</form>