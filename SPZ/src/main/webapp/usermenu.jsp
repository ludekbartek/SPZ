<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="useraction"/>
<div id="menu">
            <form action="${pageContext.request.contextPath}/SPZServlet/listusers" method="post">
                <input type="hidden" name="projectid" value="${project.id}"/>
                <input type="hidden" name="userid" value="${user.id}"/>
                <input type="submit" value="<f:message key='listusers'/>"/>
            </form>
            <form action="${pageContext.request.contextPath}/SPZServlet/adduser" method="post">
                <input type="hidden" name="projectid" value="${project.id}"/>
                <input type="hidden" name="userid" value="${user.id}"/>
                <input type="submit" value="<f:message key='addusers'/>"/>
            </form>
            <form action="${pageContext.request.contextPath}/SPZServlet/edituser" method="post">
                <input type="hidden" name="projectid" value="${project.id}"/>
                <input type="hidden" name="userid" value="${user.id}"/>
                <input type="submit" value="<f:message key='editusers'/>"/>
            </form>
        </div>