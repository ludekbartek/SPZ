<%-- 
    Document   : editDcbAcc
    Created on : Dec 30, 2015, 7:16:25 AM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<f:setBundle basename="editDcbAccepted"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><f:message key="spzInfoHeader"/> <c:out value="${spz.id}"/> </title>
    </head>
    <body>
        <h1><f:message key="spzInfoHeader"/></h1>
        <table>
            <jsp:include page="editcommon.jsp"/>
            <tr>
                <td class="label"><f:message key="revised"/></td>
                <td class="value" rowspan="3"><c:out value="${spz.revisedRequest}" escapeXml="true"/></td>
            </tr>
            <tr>
                <td class="label"><f:message key="solution"/></td>
                <td class="value"><c:out value="${spz.solution}" escapeXml="true"/></td>
            </tr>
            <tr>
                <td class="label"><f:message key="estWorkLoad"/></td>
                <td class="value"><c:out value="${spz.workLoadEstimation}"/></td>
            </tr>
        </table>
                <c:if test="${user.role!=0}">
                    <form action="${pageContext.request.contextPath}/SPZServlet/releaseversion" method="post">
                        <input type="hidden" name="spzid" value="${spz.id}"/>
                        <input type="hidden" name="userid" value="${user.id}"/>
                        <input type="hidden" name="change" value="true"/>
                        <input type="submit" value="<f:message key='releaseButton'/>"/>
                    </form>
                    <form action="${pageContext.request.contextPath}/SPZServlet/delete" method="post">
                        <input type="hidden" name="spzid" value="${spz.id}"/>
                        <input type="hidden" name="userid" value="${spz.id}"/>
                        <input type="hidden" name="state" value="DCB_ACCEPTED"/>
                        <input type="button" value="<f:message key='cancelButton'/>"/>
                    </form>
                    <form action="${pageContext.request.contextPath}/SPZServlet/startimpl" method="post">
                        <input type="hidden" name="spzid" value="${spz.id}"/>
                        <input type="hidden" name="userid" value="${user.id}"/>
                        <input type="hidden" name="change" value="true"/>
                        <input type="submit" value="<f:message key='startImplButton'/>"/>
                    </form>
                </c:if>
                <jsp:include page="listHistory.jsp"/>
    </body>
</html>
