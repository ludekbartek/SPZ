<%-- 
    Document   : editCanceled
    Created on : Sep 26, 2015, 10:15:57 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<c:set var="jsp" value="./editCanceled.jsp"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Změna stavu SPZ</title>
        <link rel="stylesheet" href="styles/dcb.css" type="text/css"/>
        <script src="/SPZ/scripts/support-scripts.js" type="text/javascript"></script>
    </head>
    <body>
        <jsp:include page="headerspz.jsp"/>
        <div class="center">
            <jsp:include page="navigationSpz.jsp"/>
        </div>
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
        <jsp:include page="addNote.jsp"/>
    </body>
</html>
