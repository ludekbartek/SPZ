<%-- 
    Document   : editNew
    Created on : Sep 10, 2015, 1:58:55 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Informace o SPZ</title>
    </head>
    <body>
        <h1>System servisni podpory</h1>
        <h2>Informace o SPZ</h2>
        <table>
            <%@include file="editcommon.jsp" %>
            <tr>
                <td colspan="3">
                    <form action="${pageContext.request.contextPath}/SPZServlet/editSPZ" method="post">
                        <input type="submit" value="Predat k analyze"/>
                        <input type="hidden" name="id" value="${spz.id}"/>
                        <input type="hidden" name="state" value="POSTED"/>
                        <input type="hidden" name="newstate" value="ANALYSIS"/>
                    </form>
                    <form action="${pageContext.request.contextPath}/SPZServlet/delete" method="post">    
                        <input type="hidden" name="id" value="${spz.id}"/>
                        <input type="submit" value="Zrusit"/>
                        <input type="hidden" name="newstate" value="CANCELED"/>
                        <input type="hidden" name="id" value="${spz.id}"/>
                        <input type="hidden" name="state" value="POSTED"/>
                    </form>
                </td>
            </tr>
        </table>
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
        <p style="text-align:right;">
            <form action="${pageContext.request.contextPath}/SPZServlet/updateSPZ" method="post">
                <input type="button" value="Zmena SPZ"/>
                <input type="hidden" name="id" value="${spz.id}"/>
            </form>
        </p>
    </body>
</html>
