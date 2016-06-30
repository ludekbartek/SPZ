<%-- 
    Document   : editNew
    Created on : Sep 10, 2015, 1:58:55 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="editNew"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><f:message key="pageTitle"/>(<c:out value="${user.id}"/></title>
        <link rel="stylesheet" href="styles/dcb.css" type="text/css"/>
        <script type="text/javascript" src="scripts/support-scripts.js"></script>
    </head>
    <body>
        <jsp:include page="headerspz.jsp"/>
        <div class="center">
            <jsp:include page="navigationSpz.jsp"/>
        </div>
        <h2><f:message key="pageTitle"/></h2>
        <table class="border-fullwidthtable">
            <c:set var="jsp" value="./editNew.jsp"/>
            <%@include file="editcommon.jsp" %>
            <tr>
                <td colspan="3">
                    <form action="${pageContext.request.contextPath}/SPZServlet/edit" method="post">
                        <input type="submit" value="<f:message key='submitAnal'/>"/>
                        <input type="hidden" name="state" value="new"/>
                        <input type="hidden" name="userid" value="${user.id}"/>
                        <input type="hidden" name="spzid" value="${spz.id}"/>
                        <input type="hidden" name="newState" value="analysis"/>
                        <input type="hidden" name="configid" value="${config.id}"/>
                        <input type="hidden" name="projectid" value="${project.id}"/>
                    </form>
                    <form action="${pageContext.request.contextPath}/SPZServlet/delete" method="post">    
                        <input type="submit" value="<f:message key='cancel'/>"/>
                        <input type="hidden" name="state" value="new"/>
                        <input type="hidden" name="userid" value="${user.id}"/>
                        <input type="hidden" name="spzid" value="${spz.id}"/>
                        <input type="hidden" name="newState" value="canceled"/>
                        <input type="hidden" name="configid" value="${config.id}"/>
                        <input type="hidden" name="projectid" value="${project.id}"/>
                    </form>
                </td>
            </tr>
        </table>
    </body>
</html>
