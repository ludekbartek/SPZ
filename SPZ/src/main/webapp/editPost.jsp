<%-- 
    Document   : editNew
    Created on : Sep 10, 2015, 1:58:55 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="editPost" var="loc"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><f:message key="pageTitle" bundle="${loc}"/> (${user.login})</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/dcb.css" type="text/css"/>
        <script type="text/javascript" src="scripts/support-scripts.js"></script>
    </head>
    <body>
        <form id="doAccept" action="${pageContext.request.contextPath}/SPZServlet/acceptspzreq" method="post">
        <!--            <input type="submit" value="<f:message key='submitAnal' bundle="${loc}"/>"/>
                    <input type="submit" value="<f:message key='cancel' bundle='${loc}'/>" onclick="doSubmit(doCancel)"/>-->
                    <input type="hidden" name="spzid" value="${spz.id}"/>
                    <input type="hidden" name="state" value="POSTED"/>
                    <input type="hidden" name="userid" value="${user.id}"/>
                    <input type="hidden" name="newstate" value="ANALYSIS"/>
                    <input type="hidden" name="projectid" value="${project.id}"/>
                    <input type="hidden" name="configid" value="${config.id}"/>
                </form>
        <jsp:include page="headerspz.jsp"/>
        <div class="center">
            <jsp:include page="navigationSpz.jsp"/>
        </div>
        <h2><f:message key="pageTitle" bundle="${loc}"/></h2>
        <c:set var="jsp" value="./editPost.jsp"/>
        <table class="border-fullwidthtable">
            <%@include file="editcommon.jsp" %>
        </table>
        <div class="controls">
            <c:choose>
                <c:when test="${user.role!='0'}">
                    <form method="post">
                        <input type="button" value="<f:message key='submitAnal' bundle="${loc}"/>" onclick="doPost(doAccept)"/>
                        <input type="button" value="<f:message key='cancel' bundle='${loc}'/>" onclick="doPost(doCancel)"/>        
                    </form>
                </c:when>
                <c:otherwise>
                    <form> 
                        <input type="button" value="<f:message key='cancel' bundle='${loc}'/>" onclick="doPost(doCancel)"/>        
                    </form>
                </c:otherwise>
            </c:choose>
            <form id="doCancel" action="${pageContext.request.contextPath}/SPZServlet/delete" method="post">    
                <input type="hidden" name="spzid" value="${spz.id}"/>
                <!--<input type="submit" value="<f:message key='cancel' bundle="${loc}"/>"/>-->
                <input type="hidden" name="newstate" value="CANCELED"/>
                <input type="hidden" name="state" value="POSTED"/>
                <input type="hidden" name="userid" value="${user.id}"/>
                <input type="hidden" name="projectid" value="${project.id}"/>
                <input type="hidden" name="configid" value="${config.id}"/>
            </form>
        </div>
        <!--<h2>Historie SPZ</h2>-->
        <%@include  file="listHistory.jsp"%>
        <!--<h2>Pridej poznamku</h2>
        <form action="${pageContext.request.contextPath}/SPZServlet/addNote" enctype="multipart/form-data" method="post">
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
        <jsp:include page="addNote.jsp"/>
        <%-- Muze pouze Projektovy manazer a admin --%>
        <c:if test="${user.role>=2}">
        <div class="updateSPZ">    
            <form id="changeSPZ" action="${pageContext.request.contextPath}/SPZServlet/updateSPZ" method="post">
                <%--<div class="formItem">
                    <span class="label">
                        <input type="submit" value="<f:message key='changeSpz' bundle="${loc}"/>"/>
                    </span>
                </div>--%>
                <input type="hidden" name="spzid" value="${spz.id}"/>
                <input type="hidden" name="userid" value="${user.id}"/>
                <input type="hidden" name="projectid" value="${project.id}"/>
                <input type="hidden" name="configid" value="${config.id}"/>
            </form>
        </div>
            
        <div class="rightfloat">
            <a href="javascript:doPost(changeSPZ)"><f:message key="changeSpz" bundle="${loc}"/></a>
        </div>
        </c:if>
    </body>
</html>
