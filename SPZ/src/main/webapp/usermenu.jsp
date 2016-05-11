<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<f:setBundle basename="useraction"/>
<div id="menu">
    <c:if test="${user.classType=='3'}">
        <form action="${pageContext.request.contextPath}/SPZServlet/listusers" method="post">
            <input type="hidden" name="projectid" value="${project.id}"/>
            <input type="hidden" name="userid" value="${user.id}"/>
            <input type="submit" value="<f:message key='listusers'/>"/>
        </form>

        <form action="${pageContext.request.contextPath}/SPZServlet/adduser" method="post">
            <input type="hidden" name="projectid" value="${project.id}"/>
            <input type="hidden" name="userid" value="${user.id}"/>
            <input type="hidden" name="source" value="${requestScope['javax.servlet.forward.path_info']}"/>
            <input type="submit" value="<f:message key='addusers'/>"/>
        </form>
    </c:if>
    <form action="${pageContext.request.contextPath}/SPZServlet/edituser" method="post">
        <input type="hidden" name="projectid" value="${project.id}"/>
        <input type="hidden" name="userid" value="${user.id}"/>
        <input type="hidden" name="source" value="${requestScope['javax.servlet.forward.path_info']}"/>
        <input type="submit" value="<f:message key='editusers'/>"/>
    </form>
</div>