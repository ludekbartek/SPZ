<%-- 
    Document   : listSPZ
    Created on : Aug 26, 2015, 7:15:26 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Servisni pozadavky zakazniku</title>
    </head>
    <body>
        <h1>Tady bude seznam SPZ s moznymi akcemi.</h1>
        <table>
            <thead>
                <th>ID</th>
                <th>Cislo pozadavku</th>
                <th>Priorita</th>
                <th>Datum nahlaseni</th>
                <th>Kontaktni osoba</th>
                <th>Typ pozadavku</th>
            </thead>
            <c:forEach items="${spzs}" var="item">
                <tr>
                    <td>
                        <c:out value="${item.id}"/>
                    </td>
                    <td>
                        <c:out value="${item.reqnumber}"/>
                    </td>
                    <td>
                        <c:out value="${item.priority}"/>
                    </td>
                    <td>
                        <c:out value="${item.issuedate}"/>
                    </td>
                    <td>
                        <c:out value="${item.contactperson}"/>
                    </td>
                    <td>
                        <c:out value="${item.requesttype}"/>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
