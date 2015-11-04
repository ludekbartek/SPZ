<%-- 
    Document   : newjsp
    Created on : Sep 16, 2015, 3:35:49 AM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:forEach items="${spz.history}" var="item">
    <div class="state-header"><c:out value="${item.code}"/> <f:formatDate type="both" dateStyle="LONG" timeStyle="short" value="${item.issueDate}"/>
         <c:out value="${item.issuer}"/>
    </div>
         <div  class="state-body">
             <c:out value="${item.issuer}"/>
             (<f:formatDate type="both" dateStyle="LONG" timeStyle="SHORT" value="${item.issueDate}"/>)
                         
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
     <input type="submit" value="smazat posledni stav"/>
    </form>
 </c:if>

   