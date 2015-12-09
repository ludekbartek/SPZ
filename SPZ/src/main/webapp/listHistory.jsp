<%-- 
    Document   : newjsp
    Created on : Sep 16, 2015, 3:35:49 AM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<f:setBundle basename="list"/>
<h2>Historie SPZ</h2>
<c:forEach items="${spz.history}" var="item">
    <div class="state-header"><c:out value="${item.code}"/> <f:formatDate type="both" dateStyle="LONG" timeStyle="short" value="${item.issueDate}"/>
         <c:out value="${item.issuer}"/>
    </div>
         <div  class="state-body">
             <c:out value="${item.issuer}"/>
             (<f:formatDate type="both" dateStyle="LONG" timeStyle="SHORT" value="${item.issueDate}"/>)
             <c:forEach items="${item.notes}" var="note">
                 <div class="note">
                     <c:choose>
                         <c:when test="${empty noteIssuer}">
                             Nezadan
                         </c:when>
                         <c:otherwise>
                             <c:out value="${note.noteIssuer}"/>
                         </c:otherwise>
                     </c:choose> 
                     (<f:formatDate type="both" dateStyle="LONG" timeStyle="SHORT" value="${note.noteDate}"/>)
                     <c:if test="${note.internal!=1}">
                         <div class="note-header">
                             #########<f:message key="internal"/>#########
                         </div>
                             
                     </c:if>
                     <div class="notetext">
                        <c:out value="${note.noteText}" escapeXml="false"/> 
                     </div>
                 </div>
            </c:forEach>
             <!--(<c:out value="${item.issueDate}"/>)-->
         </div>
         <div class="body">
             <c:out value="${item.revisedRequestDescription}" escapeXml="false"/>
         </div>
</c:forEach>
<c:if test="${item.code!='Registrovaná'}">
    <form action="${pageContext.request.contextPath}/SPZServlet/removeState" method="post">
     <input type="hidden" name="spzstateid" value="${item.id}"/>
     <input type="hidden" name="id" value="${spz.id}"/>
     <input type="submit" value="smazat posledni stav" onsubmit="return confirm('Opravdu smazat posledni stav?');"/>
    </form>
 </c:if>

   