<%-- 
    Document   : editAnalyst
    Created on : Jan 5, 2016, 8:16:43 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="editAnalyst"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><f:message key="pagetitle"/> <c:out value="${spz.id}"/> (<c:out value="${user.login}"/>)</title>
        <link rel="stylesheet" href="styles/dcb.css" type="text/css"/>
        <script src="/SPZ/scripts/support-scripts.js" type="text/javascript"></script>
    </head>
    <body>
        <jsp:include page="headerSpzEdit.jsp"/>
        <div class="center">
            <jsp:include page="navigationSpz.jsp"/>
        </div>
        <h1><f:message key="pagetitle"/></h1>
        <table class="infotable">
            <jsp:include page="editcommon.jsp"/>
        </table>
            <form action="${pageContext.request.contextPath}/SPZServlet/changeAnalyst" method="post">
            <div class="formItem">
                <span class="label">
                <label class="textarealabel" for="desc"><f:message key="descLabel"/></label>
                </span>
                <span class="input">
                    <textarea class="textareainput" name="desc" <%--cols="80"--%> rows="5"></textarea>
                </span>
            </div>
            <div class="formItem">
                <span class="label">&nbsp;</span>
                <span class="input">
                    <input type="checkbox" name="external"/><label for="external"><f:message key="externalNote"/></label>
                </span>
            </div>
            <div class="formItem">
                <span class="label"><label for="analyst"><f:message key="analystLabel"/></label></span>
                <span class="input">
                    <select name="analyst">
                        <c:forEach var="analyst" items="${analysts}">
                            <option value="<c:out value='${analyst.id}'/>"><c:out value="${analyst.name}"/></option>
                        </c:forEach>
                    </select>
                </span>
            </div>
            
            <input type="submit" value="<f:message key='submit'/>"/>
            <input type="hidden" name="userid" value="${user.id}"/>
            <input type="hidden" name="spzid" value="${spz.id}"/>
            <input type="hidden" name="configid" value="${config.id}"/>
            <input type="hidden" name="projectid" value="${project.id}"/>
         </form>
        <jsp:include page="listHistory.jsp"/>
    </body>
</html>
