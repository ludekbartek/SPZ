<%-- 
    Document   : editSpec
    Created on : Dec 6, 2015, 6:18:50 PM
    Author     : bar
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<f:setBundle basename="editSpec"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><f:message key="pageTitle"/> <c:out value="${spz.id}"/> (<c:out value="${spz.issuer}"/>)</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/dcb.css" type="text/css"/>
        <script type="text/javascript" src="scripts/support-scripts.js"></script>
    </head>
    <body>
        <jsp:include page="headerspz.jsp"/>
        <div class="center">
            <jsp:include page="navigationSpz.jsp"/>
        </div>
        <h1><f:message key="pageTitle"/></h1>
        <c:set var="jsp" value="./editSpec.jsp"/>
        <table class="border-fullwidthtable">
            <jsp:include page="editcommonspec.jsp"/>
            <%--<tr>
                <td class="label"><f:message key="revisedReq"/>:</td>
                <td colspan="3"><c:out value="${spz.revisedRequest}"/></td>
            </tr>
            <tr>
                <td class="label"><f:message key="solProp"/>:</td>
                <td colspan="3"><c:out value="${spz.solution}"/></td>
            </tr>
            <tr>
                <td class="label"><f:message key="workLoadEst"/>:</td>
                
                <td colspan="3"><f:formatNumber minFractionDigits="2" maxFractionDigits="2" value="${spz.workLoadEstimation}"/> <f:message key="manHours"/> 
                    (<f:formatNumber minFractionDigits="2" maxFractionDigits="2" value="${spz.workLoadEstimation/8.0}"/> <f:message key="manDays"/>)</td>
            </tr>--%>
        </table>
        <span class="debug">
            User: <c:out value="${user.name}"/> (id:<c:out value="${user.id}"/>, role:<c:out value="${user.role}"/>)
        </span>
            <%-- <c:if test="${user.role!=0}">--%>
            <form action="${pageContext.request.contextPath}/SPZServlet/delete" method="post">
                <input type="hidden" name="spzid" value="${spz.id}"/>
                <input type="hidden" name="state" value="SPECIFIED"/>
                <input type="hidden" name="userid" value="${user.id}"/>
                <input type="hidden" name="newState" value="CANCELED"/>
                <input type="hidden" name="configid" value="${config.id}"/>
                <input type="hidden" name="projectid" value="${project.id}"/>
                <input type="submit" value="<f:message key='cancel'/>"/>
            </form>
        <%-- </c:if> --%>
        <c:if test="${user.role==0}">
            <form action="${pageContext.request.contextPath}/SPZServlet/editspz" method="post">
                <input type="hidden" name="spzid" value="${spz.id}"/>
                <input type="hidden" name="userid" value="${user.id}"/>
                <input type="hidden" name="state" value="SPECIFIED"/>
                <input type="hidden" name="newState" value="RANALYSIS"/>
                <input type="hidden" name="configid" value="${config.id}"/>
                <input type="hidden" name="projectid" value="${project.id}"/>
                <input type="submit" value="<f:message key='submit'/>"/>
            </form>
            <form action="${pageContext.request.contextPath}/SPZServlet/acceptsolution" method="post">
                <input type="hidden" name="spzid" value="${spz.id}"/>
                <input type="hidden" name="userid" value="${user.id}"/>
                <input type="hidden" name="state" value="SPECIFIED"/>
                <input type="hidden" name="configid" value="${config.id}"/>
                <input type="hidden" name="projectid" value="${project.id}"/>
                <input type="submit" value="<f:message key='acceptSol'/>"/>
            </form>    
        </c:if>
        <jsp:include page="listHistory.jsp"/>
        <!--<h2>Pridej poznamku</h2>-->
        <jsp:include page="addNote.jsp"/>
        <!--<form action="${pageContext.request.contextPath}/SPZServlet/addNote" enctype="multipart/form-data" method="post">
            <textarea name="desc" cols="80" rows="8"><c:if test="${!empty desc}"><c:out value="${desc}"/></c:if></textarea>
            <input type="hidden" name="id" value="${spz.id}"/>
            <input type="checkbox" name="external" id="ext" <c:if test="${!empty ext and ext=='true'}">checked="true"</c:if>/><label for="ext">Poznamka viditelna pro zakaznika</label>
            <br/>
            <input type="submit" value="Ulozit poznamku"/>
            <br/>
            <table>
                <tr>
                    <td>Soubor 1:<input type="file" name="soubor1" <c:if test="${! empty soubor1}">value='<c:out value="${soubor1}"/>'</c:if>/></td>
                </tr>
                <tr>
                    <td>Soubor 2:<input type="file" name="soubor2" <c:if test="${! empty soubor2}">value='<c:out value="${soubor2}"/>'</c:if>/></td>
                </tr>
                <tr>
                    <td>Soubor 3:<input type="file" name="soubor3" <c:if test="${! empty soubor3}">value='<c:out value="${soubor3}"/>'</c:if>/></td>
                </tr>
            </table>
            
        </form>-->
    </body>
</html>
