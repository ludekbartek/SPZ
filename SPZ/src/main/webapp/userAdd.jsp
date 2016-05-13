<%-- 
    Document   : userAdd
    Created on : May 10, 2016, 5:17:40 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="useraction"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><f:message key="newUser"/></title>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <h1><f:message key="userListHeading"/></h1>
        <jsp:include page="usermenu.jsp"/>
        <div id="content">
            <h2><f:message key="newUser"/></h2>
            <c:if test="${not empty error}">
                <div id="erorr"><c:out value="${error}"/></div>
            </c:if>
            <form action="${pageContext.request.contextPath}/SPZServlet/adduser" method="post">
                <input type="hidden" name="userid" value="${user.id}"/>
                <c:if test="${not empty token}">
                    <input type="hidden" name="token" value="${token}"/>
                </c:if>
                <c:if test="${not empty projectid}">
                    <input type="hidden" name="projectid" value="${project.id}"/>
                </c:if>
                <table>
                    <tr>
                        <td><f:message key="userlogin"/>:</td>
                        <td><input type="text" name="login" <c:if test="${not empty newUser}">value="<c:out value='${newUser.login}'/>"</c:if>/></td>
                    </tr>
                    <tr>
                        <td><f:message key="fullname"/>:</td>
                    <td><input type="text" name="name" <c:if test="${not empty newUser}">value="<c:out value='${newUser.name}'/>"</c:if>/></td>
                    </tr>
                    <tr>
                        <td><f:message key="email"/>:</td>
                        <td><input type="email" name="email" <c:if test="${not empty newUser}">value="<c:out value='${newUser.email}'/>"</c:if>/></td>
                    </tr>
                    <tr>
                        <td><f:message key="company"/>:</td>
                        <td><input type="text" name="company"/></td>
                    </tr>
                    <tr>
                        <td><f:message key="phone"/>:</td>
                        <td><input type="tel" name="phone" <c:if test="${not empty newUser}">value="<c:out value='${newUser.phone}'/>"</c:if>/></td>
                    </tr>
                    <tr>
                        <td><f:message key="fax"/>:</td>
                        <td><input type="tel" name="fax" <c:if test="${not empty newUser}">value="<c:out value='${newUser.fax}'/>"</c:if>/></td>
                    </tr>
                    <tr>
                        <td><f:message key="oldpassword"/> '<c:out value="${user.login}"/>':</td>
                        <td><input type="password" name="adminPasswd" value=""/></td>
                    </tr>
                    <tr>
                        <td><f:message key="newPasswd"/></td>
                        <td><input type="password" name="newPassword" value=""/></td>
                    </tr>
                    <tr>
                        <td><f:message key="newPasswdRe"/></td>
                        <td><input type="password" name="newPasswordRe" value=""/></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="submit" value="<f:message key='addUser'/>"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </body>
</html>
