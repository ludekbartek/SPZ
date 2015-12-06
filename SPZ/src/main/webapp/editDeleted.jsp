<%-- 
    Document   : editDeleted
    Created on : Dec 6, 2015, 1:13:02 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Informace os SPZ <c:out value="${spz.reqNumber}"/></title>
    </head>
    <body>
        <h1>Informace o SPZ</h1>
        <jsp:include page="editcommon.jsp"/>
        <jsp:include page="listHistory.jsp"/>
        <h2>Pridej poznamku</h2>
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
            
        </form>
        
    </body>
</html>
