<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="addNote"/>
<h2><f:message key="addNoteHeading"/></h2>
<form action="${pageContext.request.contextPath}/SPZServlet/addNote" enctype="multipart/form-data" method="post">
    <textarea name="desc" cols="80" rows="8" maxlength="8000"><c:if test="${!empty desc}"><c:out value="${desc}"/></c:if></textarea>
    <input type="hidden" name="id" value="${spz.id}"/>
    <input type="checkbox" name="external" id="ext" <c:if test="${!empty ext and ext=='true'}">checked="true"</c:if>/><label for="ext"><f:message key="extNote"/></label>
    <br/>
    <input type="submit" value="<f:message key="saveNote"/>"/>
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