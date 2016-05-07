<%-- 
    Document   : useredit
    Created on : Apr 24, 2016, 3:37:50 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div id="heading">
        <jsp:include page="header.jsp"/>
        </div>
        <f:setBundle basename="useraction"/>
        <jsp:include page="usermenu.jsp"/>
        <div id="content">
            <h2><f:message key="persDataChange"/></h2>
            <form action="${pageContext.request.contextPath}/SPZServlet/edituser" method="post">
                <input type="hidden" name="userid" value="${user.id}"/>
                <table class="bordless">
                    <tr>
                        <td class="highlighted">
                            <f:message key="userlogin"/>:
                        </td>
                        <td>
                            <c:out value="${user.login}"/>
                            <input type="hidden" name="login" value="${user.login}"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="highlighted">
                            <f:message key="fullname"/>:
                        </td>
                        <td>
                            <input type="text" name="name" value="<c:out value='${user.name}'/>"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="highlighted">
                            <f:message key="email"/>:
                        </td>
                        <td>
                            <input type="email" name="email" value="<c:out value='${user.email}'/>"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="highlighted">
                            <f:message key="company"/>:
                        </td>
                        <td>
                            <input type="text" name="company" value="<c:out value='${user.company}'/>"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="highlighted">
                            <f:message key="phone"/>:
                        </td>
                        <td>
                            <input type="tel" name="phone" value='<c:out value="${user.phone}"/>'/>
                        </td>
                    </tr>
                    <tr>
                        <td class="highlighted">
                            <f:message key="fax"/>:
                        </td>
                        <td>
                            <input type="tel" name="fax" value='<c:out value="${user.fax}"/>'/>
                        </td>
                    </tr>
                    <tr>
                        <td class="highlighted">
                            <f:message key="oldpassword"/> '<c:out value="${user.login}"/>':
                        </td>
                        <td>
                            <input type='password' name='password'/>
                        </td>
                    </tr>
                    <tr>
                        <td class="highlighted">
                            <f:message key="newPasswd"/>:
                        </td>
                        <td>
                            <input type="password" name="newPassword"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="highlighted">
                            <f:message key="newPasswdRe"/>:
                        </td>
                        <td>
                            <input type="password" name="retypePasswd"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="submit" value="<f:message key='submit'/>"/>
                        </td>
                    </tr>
                </table>
            </form>
            <c:if test="${user.role!=0}">
                <form action="${pageContext.request.contextPath}/SPZServlet/editRoles" method="post">
                    <input type="hidden" name="userid" value="${user.id}"/>
                    <table>
                        <tr>
                            <td class="highlighted">
                                <f:message key="userlogin"/>:
                            </td>
                            <td>
                                <c:out value="${user.login}"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="highlighted">
                                <f:message key="oldpassword"/>:
                            </td>
                            <td>
                                <input type="password" name="password" value=""/>
                            </td>
                        </tr>
                        <tr>
                            <td class="highlighted">
                                <f:message key="superUser"/>:
                            </td>
                            <td>
                                <input type="radio" name="superuser" value="yes"/><f:message key="yes"/>
                                &nbsp;
                                <input type="radio" name="superuser" value="no"/><f:message key="no"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="highlighted">
                                <f:message key="regularuser"/>:
                            </td>
                            <td>
                                <input type="radio" name="role_user" value="yes" <c:if test='${user.role=="3"}'>checked</c:if>/><f:message key="yes"/>
                                &nbsp;
                                <input type="radio" name="role_user" value="no" <c:if test='${user.role=="0"}'>checked</c:if>/><f:message key="no"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <input type="submit" value="<f:message key='submit'/>"/>
                            </td>
                        </tr>
                    </table>
                </form>
            </c:if>
        </div>
    </body>
</html>
