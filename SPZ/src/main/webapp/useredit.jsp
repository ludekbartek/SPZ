<%-- 
    Document   : useredit
    Created on : Apr 24, 2016, 3:37:50 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<f:setBundle basename="useraction"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><f:message key="useredittitle"/></title>
    </head>
    <body>
        <div id="heading">
            <h1><f:message key="usereditheader"/></h1>
        <jsp:include page="header.jsp"/>
        </div>
        <jsp:include page="usermenu.jsp"/>
        <c:choose>
            <c:when test="${not empty editeduser}">
                <c:set var="usr" value="${editeduser}"/>
            </c:when>
            <c:otherwise>
                <c:set var="usr" value="${user}"/>
            </c:otherwise>
        </c:choose>
        <div id="content">      <c:out value="${usr.login}"/>
                                <input type="hidden" name="login" value="${usr.login}"/>
                                
            <h2><f:message key="persDataChange"/></h2>
            <form action="${pageContext.request.contextPath}/SPZServlet/edituser" method="post">
                <input type="hidden" name="userid" value="${user.id}"/>
                <c:if test="${not empty token}">
                    <input type="hidden" name="token" value="${token}"/>
                </c:if>
                    <br/>
                    editedUserId: <c:out value="${editeduser.id}"/>
                    <br/>
                    <c:choose>
                        <c:when test="${not empty editeduser}">
                            <input type="hidden" name="editeduserid" value="${editeduser.id}"/>
                        </c:when>
                        <c:otherwise>
                            <input type="hidden" name="userId" value="${user.id}"/>
                        </c:otherwise>
                    </c:choose>
                <table class="bordless">
                    <tr>
                        <td class="highlighted">
                            <f:message key="userlogin"/>:
                        </td>
                        <td>
                            <c:out value="${usr.login}"/>
                            <input type="hidden" name="login" value="${usr.login}"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="highlighted">
                            <f:message key="fullname"/>:
                        </td>
                        <td>
                            <input type="text" name="name" value="<c:out value='${usr.name}'/>"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="highlighted">
                            <f:message key="email"/>:
                        </td>
                        <td>
                            <input type="email" name="email" value="<c:out value='${usr.email}'/>"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="highlighted">
                            <f:message key="company"/>:
                        </td>
                        <td>
                            <input type="text" name="company" value="<c:out value='${usr.company}'/>"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="highlighted">
                            <f:message key="phone"/>:
                        </td>
                        <td>
                            <input type="tel" name="phone" value='<c:out value="${usr.phone}"/>'/>
                        </td>
                    </tr>
                    <tr>
                        <td class="highlighted">
                            <f:message key="fax"/>:
                        </td>
                        <td>
                            <input type="tel" name="fax" value='<c:out value="${usr.fax}"/>'/>
                        </td>
                    </tr>
                    <tr>
                        <td class="highlighted">
                            <f:message key="oldpassword"/> '<c:out value="${user.login}"/>':
                        </td>
                        <td>
                            <input type='password' name='password' value=""/>
                        </td>
                    </tr>
                    <tr>
                        <td class="highlighted">
                            <f:message key="newPasswd"/>:
                        </td>
                        <td>
                            <input type="password" name="newPassword" value=""/>
                        </td>
                    </tr>
                    <tr>
                        <td class="highlighted">
                            <f:message key="newPasswdRe"/>:
                        </td>
                        <td>
                            <input type="password" name="retypePasswd" value=""/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="submit" value="<f:message key='submit'/>"/>
                        </td>
                    </tr>
                </table>
            </form>
                        <!-- User roles:
                                     0 - klient/uzivatel
                                     1 - analytik
                                     2 - projekovy manazer
                                     3 - administrator
                                -->
            <c:if test="${user.classType=='3'}">
                <form action="${pageContext.request.contextPath}/SPZServlet/editRoles" method="post">
                    <input type="hidden" name="userid" value="${user.id}"/>
                    Edited user id:<c:out value="${usr.id}"/><br/>
                    <input type="hidden" name="editeduserid" value="${usr.id}"/>
                    <c:if test="${not empty token}">
                        <input type="hidden" name="token" value="${token}"/>
                    </c:if>
                    <!--<br/>
                    Called from: <c:out value="${requestScope['javax.servlet.forward.path_info']}"/>
                    <br/>
                    Query URI: <c:out value="${requestScope['javax.servlet.forward.request_uri']}"/>
                    <br/>-->
                    <table>
                        <tr>
                            <td class="highlighted">
                                <f:message key="userlogin"/>:
                            </td>
                            <td>
                                <c:out value="${usr.login}"/>
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
                                <!-- User roles:
                                     0 - klient/uzivatel
                                     1 - analytik
                                     2 - projekovy manazer
                                     3 - administrator
                                -->
                                <br/>
                                Role: <c:out value="${usr.classType}"/>
                                <br/>
                                <input type="radio" name="superuser" value="yes" <c:if test='${usr.classType=="3"}'>checked</c:if>/><f:message key="yes"/>
                                &nbsp;
                                <input type="radio" name="superuser" value="no" <c:if test='${usr.classType!="3"}'>checked</c:if>/><f:message key="no"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="highlighted">
                                <f:message key="regularuser"/>:
                            </td>
                            <td>
                                <!-- User roles:
                                     0 - klient/uzivatel
                                     1 - analytik
                                     2 - projekovy manazer
                                     3 - administrator
                                -->
                                <input type="radio" name="role_user" value="yes" <c:if test='${usr.classType=="0"}'>checked</c:if>/><f:message key="yes"/>
                                &nbsp;
                                <input type="radio" name="role_user" value="no" <c:if test='${usr.classType!="0"}'>checked</c:if>/><f:message key="no"/>
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
