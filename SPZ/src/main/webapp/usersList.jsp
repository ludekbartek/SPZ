<%-- 
    Document   : userslist
    Created on : May 8, 2016, 7:46:31 PM
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
        <title><f:message key="userListHeading"/></title>
        <link rel="stylesheet" href="${pageContext.request.contentPath}/styles/dcb.css"/>
        <script type="text/javascript" src="${pageContext.request.contentPath}/scripts/support-scripts.js"></script>
    </head>
    <body>
        <div id="heading"><jsp:include page="header.jsp"/></div>
        
        <h1><f:message key="userListHeading"/></h1>
        <div id="user">
            <c:out value="${user.name}"/>
        </div>
        <jsp:include page="usermenu.jsp"/>
        <table>
            <thead>
                <th>
                    <f:message key="userlogin"/>   
                </th>
                <th>
                    <f:message key="fullname"/>
                </th>
                <th>
                    <f:message key="superUser"/>
                </th>
                <th>
                    <f:message key="regularuser"/>
                </th>
            </thead>
            <tbody>
                <c:forEach var="userItem" items="${users}">
                    <tr>
                        <td>
                            <form name="useredit${userItem.id}" action="${pageContext.request.contextPath}/SPZServlet/edituser" method="post">
                                <input type="hidden" name="userid" value="${user.id}"/>
                                <input type="hidden" name="editeduserid" value="${userItem.id}"/>
                                <input type="hidden" name="source" value="${requestScope['javax.servlet.forward.path_info']}"/>
                                <input type="submit" value="${userItem.login}"/>
                            </form> 
                        </td>
                        <td>
                            <c:out value="${userItem.name}"/>
                        </td>
                        <td>
                            <c:if test="${userItem.classType=='3'}">Yes</c:if>
                        </td>
                        <td>
                            <c:if test="${userItem.classType=='0'}">Yes</c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>
