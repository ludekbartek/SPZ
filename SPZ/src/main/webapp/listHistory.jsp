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
<div id="history">
    <h3><f:message key="spzHistory"/></h3>
<hr>
<span class="debug">Name:${user.name} (id:<c:out value="${user.id}"/>)<br/>Role:<c:out value="${user.role}"/></span>

<c:forEach items="${spz.history}" var="item">
    <div class="state-header"><c:out value="${item.code}"/> <f:formatDate type="both" dateStyle="LONG" timeStyle="short" value="${item.issueDate}"/>
         <c:out value="${item.issuer.name}"/>
    </div>
         <div  class="state-body">
             <%--<c:out value="${item.issuer.name}"/>
(<f:formatDate type="both" dateStyle="LONG" timeStyle="SHORT" value="${item.issueDate}"/>)--%>
             <c:forEach items="${item.notes}" var="note">
                     
                 <c:choose>
                     <c:when test="${note.external==0 and user.role!=0}">
                         <div class="note">
                             <!--<div>
                                 externi: <c:out value="${note.external}"/><br/>
                                 role: <c:out value="${user.role}"/><br/>
                                 text: <c:out value="${note.noteText}"/><br/>
                             </div>-->
                             <c:if test="${not empty note.noteIssuer}">
                                 <c:out value="${note.noteIssuer}"/>
                             </c:if>
                             (<f:formatDate type="both" dateStyle="LONG" timeStyle="SHORT" value="${note.noteDate}"/>)

                                     #########<f:message key="internal"/>#########
                                    <div class="notetext">
                                        <c:out value="${note.noteText}" escapeXml="false"/> 
                                        <c:if test="${fn:length(note.attachments) gt 0}">
                                              <c:forEach var="attachment" items="${note.attachments}">
                                                  (<a href="${pageContext.request.contextPath}/SPZServlet/getattach?attachmentId=<c:out value='${attachment.id}'/>&userid=<c:out value='${user.id}'/>&projectid=<c:out value='${project.id}'/>"/><c:out value="${attachment.content}"/></a>)
                                              </c:forEach>
                                        </c:if>
                                    </div>
                            </div>
                 </c:when>
                 <c:when test="${note.external==1}">
                    <div class="note">
                     <!--<div>
                         externi: <c:out value="${note.external}"/><br/>
                         role: <c:out value="${user.role}"/><br/>
                         text: <c:out value="${note.noteText}"/><br/>
                     </div>-->
             
                         <div class="notetext">
                             <c:out value="${note.noteText}" escapeXml="false"/> 
                            <c:if test="${fn:length(note.attachments) gt 0}">
                                  <c:forEach var="attachment" items="${note.attachments}">
                                      (<a href="${pageContext.request.contextPath}/SPZServlet/getattach?attachmentId=<c:out value='${attachment.id}'/>&userid=<c:out value='${user.id}'/>&projectid=<c:out value='${project.id}'/>"/><c:out value="${attachment.content}"/></a>) 
                                  </c:forEach>
                            </c:if>
                         </div>
                    </div>
                 </c:when>
             </c:choose>
                     
                 
            </c:forEach>
            <c:if test="${not empty spz.workLoadEstimation and spz.workLoadEstimation gt 0}">
                 <div class="state-item">
                    <f:message key="estwork"/>: 
                    <f:formatNumber minFractionDigits="1" maxFractionDigits="1" value="${spz.workLoadEstimation}"/>
                    <f:message key="manhours"/>
                 </div>
            </c:if>
             <c:if test="${not empty spz.revised}">
                <div class="state-item">
                     <f:message key="revisedsol"/>:
                     <c:out escapeXml="false" value="${spz.revised}"/>
                </div>
             </c:if>
             <c:if test="${not empty $spz.solution}">
                 <div class="state-item"> 
                     <f:message key="solution"/>:
                     <c:out escapeXml="false" value="${spz.solution}"/>
                 </div>
             </c:if> 
            <%--(<c:out value="${item.issueDate}"/>)--%>
         </div>
         <%--<div class="body">
             <c:out value="${item.revisedRequestDescription}" escapeXml="false"/>
         </div>--%>
             <hr/>
</c:forEach>
<c:if test="${item.code!='Registrovaná'}">
    <form action="${pageContext.request.contextPath}/SPZServlet/removestate" method="post">
     <input type="hidden" name="spzstateid" value="${item.id}"/>
     <input type="hidden" name="spzid" value="${spz.id}"/>
     <input type="hidden" name="userid" value="${user.id}"/>
     <input type="hidden" name="configid" value="${config.id}"/>
     <input type="hidden" name="projectid" value="${project.id}"/>
     <input type="submit" value="smazat posledni stav" onsubmit="return confirm('Opravdu smazat posledni stav?');"/>
    </form>
 </c:if>

</div>   