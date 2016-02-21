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
<div class="debug">Name:${user.name} (id:<c:out value="${user.id}"/>)<br/>Role:<c:out value="${user.role}"/></div>
<c:forEach items="${spz.history}" var="item">
    <div class="state-header"><c:out value="${item.code}"/> <f:formatDate type="both" dateStyle="LONG" timeStyle="short" value="${item.issueDate}"/>
         <c:out value="${item.issuer.name}"/>
    </div>
         <div  class="state-body">
             <c:out value="${item.issuer.name}"/>
             (<f:formatDate type="both" dateStyle="LONG" timeStyle="SHORT" value="${item.issueDate}"/>)
             <c:forEach items="${item.notes}" var="note">
                 <div class="note">
                     <c:if test="${not empty note.noteIssuer}">
                         <c:out value="${note.noteIssuer}"/>
                     </c:if>
                     (<f:formatDate type="both" dateStyle="LONG" timeStyle="SHORT" value="${note.noteDate}"/>)
                     <c:choose>
                         <c:when test="${note.external==1 || user.role!=0}">
                            <div class="debug">External:<c:out value="${note.external==1}"/></div>
                            <c:if test="${note.external==0}">
                                <div class="note-header">
                                     #########<f:message key="internal"/>#########
                                </div>
                            </c:if>
                            <div class="notetext">
                                <c:out value="${note.noteText}" escapeXml="false"/> 
                            </div>
                         </c:when>
                     </c:choose>
                     
                 </div>
            </c:forEach>
             <!--(<c:out value="${item.issueDate}"/>)-->
         </div>
         <div class="body">
             <c:out value="${item.revisedRequestDescription}" escapeXml="false"/>
         </div>
             <hr/>
</c:forEach>
<c:if test="${item.code!='Registrovaná'}">
    <form action="${pageContext.request.contextPath}/SPZServlet/removeState" method="post">
     <input type="hidden" name="spzstateid" value="${item.id}"/>
     <input type="hidden" name="spzid" value="${spz.id}"/>
     <input type="hidden" name="userid" value="${user.id}"/>
     <input type="hidden" name="configid" value="${config.id}"/>
     <input type="hidden" name="projectid" value="${project.id}"/>
     <input type="submit" value="smazat posledni stav" onsubmit="return confirm('Opravdu smazat posledni stav?');"/>
    </form>
 </c:if>

   