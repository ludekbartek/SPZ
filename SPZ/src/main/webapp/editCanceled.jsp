<%-- 
    Document   : editCanceled
    Created on : Sep 26, 2015, 10:15:57 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Změna stavu SPZ</title>
    </head>
    <body>
        <h1>Systém servisní podpory</h1>
        <h1>Změna stavu</h1>
        Požadujete 'Zrušit' na SPZ:
        <div>
            <span class='left'>
                <span class='label'>
                   Číslo:
                </span>
                <span class='highlight-value'>
                    <c:out value="${spz.id}"/>
                </span>
            </span>
            <span class='center'>
                <span class='label'>
                    Priorita:
                </span>
                <span class='value'>
                    <c:out value="${spz.priority}"/>
                </span>
            </span>
        </div>
        <div>
            <span class='left'>
                <span class="label">Název:</span>
                <span class="highlight-value">
                    <c:out value="${spz.reqNumber}"/>
                </span>
            </span>            
            
            <span class="center">
                <span class="label">
                    Typ:
                </span>
                <span class="highlight-value">
                    <c:out value="${spz.kind}"/>
                </span>
            </span>
        </div>
        <div>
            <span class="left">
                <span class="label">
                    Zadal:
                </span>
                <span class="value">
                    <c:out value="${spz.issuer}"/><br/>
                    <f:formatDate dateStyle="LONG" type="both" timeStyle="SHORT" value="${spz.issueDate}"/>
                </span>
            </span>
            <span class="center">
                <span class="label">Zadal:</span>
                <span class="value">${spz.contactPerson}</span>
            </span>
        </div>
        <div>
            <span class="left">
                <span class="label">
                    Zadání:
                </span>
                <span class="value">
                    <c:out value="${spz.requestDescription}"/>
                </span>
            </span>
        </div>
        <div>
            <span class="left">
                <span class="label">
                    Analytik:
                </span>
                <span class="value">
                    <c:out value="${spz.analyst}"/>
                </span>
            </span>
        </div>
        <div>
            <span class="left">
                <span class="label">
                    Vývojář:
                </span>
                <span class="value">
                    <c:out value="${spz.developer}"/>
                </span>
            </span>
        </div>
                <c:import url="listHistory.jsp"/>
                <h2>Informace pro stav 'Zrušit'</h2>
                <!--<form action="${pageContext.request.contextPath}/SPZServlet/cancelState">
                    <input type='hidden' value="${spz.id}"/>
                    <div>
                        <label for='note'>Poznámka:</label>
                        <textarea cols="40" rows="5" name="note">
                        </textarea>
                    </div>
                    <div>
                        <input type='checkbox' name='visible'/>Viditelná i pro zákazníka? 
                    </div>
                    <div>
                        <input type='submit' value="Odeslat!"/>
                    </div>
                </form>-->
                <jsp:include page="addNote.jsp"/>
                <!--<h2>Historie SPZ</h2>-->
                
    </body>
</html>
