<%-- 
    Document   : acceptSol
    Created on : Dec 20, 2015, 5:35:34 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="acceptSol"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><f:message key="pageTitle"/> <c:out value="${spz.id}"/> (<c:out value="${spz.issuer}"/>)</title>
        <link rel="stylesheet" href="styles/dcb.css" type="text/css"/>
        <script type="text/javascript" src="scripts/support-scripts.js"></script>
    </head>
    <body>
        <jsp:include page="headerSpzEdit.jsp"/>
        <div class="center">
            <jsp:include page="navigationSpzEdit.jsp"/>
        </div>
        <h1><f:message key="pageTitle"/></h1>
        <div class="highlighted">
            <f:message key="request"/>:
        </div>
        <c:set var="jsp" value="./acceptSol.jsp"/>
        <table class="border-fullwidthtable">
        <jsp:include page="editcommon.jsp"/>
        <tr>
            <td><f:message key="solutionInfo"/></td>
            <td colspan="3"><c:out escapeXml="false" value="${spz.solution}" /></td>
        </tr>
        </table>
        <h3><f:message key="reqInfo"/>:</h3>
        <form action='${pageContext.request.contextPath}/SPZServlet/editspz' method="post">
            <div class="formItem">
                <span class="label"><label for='note'><f:message key="note"/>:</label></span>
                <span class="input"><textarea class="textareainput" name='note' rows="5" maxlength="8000"></textarea></span>
            </div>
            <input type='hidden' name='spzid' value='${spz.id}'/>
            <input type='hidden' name='userid' value='${user.id}'/>
            <input type="hidden" name="projectid" value="${project.id}"/>
            <input type="hidden" name="configid" value="${config.id}"/>
            <input type='hidden' name="state" value='SPECIFIED'/>
            <input type="hidden" name="newstate" value="ACCEPTED"/>
            <div class="formItem">
                <span class="noIndentInput">
                    <input type="submit" value="<f:message key='submit'/>"/>
                </span>
            </div>
        </form>
        <jsp:include page="listHistory.jsp"/>
    </body>
</html>
