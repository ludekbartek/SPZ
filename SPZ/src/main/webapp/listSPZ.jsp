<%-- 
    Document   : listSPZ
    Created on : Aug 26, 2015, 7:15:26 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Servisni pozadavky zakazniku</title>
    </head>
    <body>
        <h1>Tady bude seznam SPZ s moznymi akcemi.</h1>
        <table border="1">
            
            <thead>
                <th>ID</th>
                <th>Cislo pozadavku</th>
                <th>Priorita</th>
                <th>Datum nahlaseni</th>
                <th>Kontaktni osoba</th>
                <th>Typ pozadavku</th>
                <th>Kratky popis</th>
                <th>Popis pozadavku</th>
                <th>Datum akceptace implementace</th>
                <th>Akce</th>
            </thead>
            <c:forEach items="${spzs}" var="item">
                <tr>
                <form action="${pageContext.request.contextPath}/SPZServlet/editSPZ" method="post">
                    <td>
                        <c:out value="${item.id}"/>
                    </td>
                    <td>
                        <input type="text" name="reqnumber" value="${item.reqnumber}"/>
                    </td>
                    <td>
                        <select name="priority">
                            <option <c:if test="${item.priority}=='1'">selected="true"</c:if>>1</option>
                            <option <c:if test="${item.priority}=='2'">selected="true"</c:if>>2</option>
                            <option <c:if test="${item.priority}=='3'">selected="true"</c:if>>3</option>
                        </select>
                        <!--<c:out value="${item.priority}"/>-->
                    </td>
                    <td>
                        <input name="issuedate" type="date" value="<f:formatDate value='${item.issuedate}' pattern='dd.MM.yyyy'/>"/>
                        <!--<f:formatDate value="${item.issuedate}" pattern="dd.MM.yyyy"/>-->
                    </td>
                    <td>
                        <input name="contactperson" type="text" value="${item.contactperson}"/>
                        <!--<c:out value="${item.contactperson}"/>-->
                    </td>
                    <td>
                        <select name="reqtype"> 
                            <option <c:if test="${item.requesttype}=='radna'">selected="true"</c:if>>radna</option>
                            <option <c:if test="${item.requesttype}=='mimoradna'">selected="true"</c:if>>mimoradna</option>
                        </select>
                        <!--<c:out value="${item.requesttype}"/>-->
                    </td>
                    <td>
                        <!--<c:out value="${item.shortname}"/>-->
                        <input name="shortname" type="text" value="${item.shortname}"/>
                        
                    </td>
                    <td>
                        <textarea name="requestdescription" rows="3" cols="40">
                            <c:out value="${item.requestdescription}"/>
                        </textarea>
                    </td>
                    <td>
                        <input name="implementationacceptdate" type="date" value="<f:formatDate value='${item.implementationacceptdate}' pattern='dd.MM.yyyy'/>"/>
                        <!--<c:out value="${item.implementationacceptdate}"/>-->
                    </td>
                    <td>
                        <input type="submit" value="Uprava"/>
                    </td>
                    </form>
                </tr>
                
            </c:forEach>
                <tr>
                <form action="${pageContext.request.contextPath}/SPZServlet/addSPZ" method="post">
                    <td>
                        
                    </td>
                    <td>
                        <c:choose> 
                            <c:when test="exist $invspz">
                                <input name="reqnumber" type="text" value="{$spz.reqnumber}"/>
                            </c:when>
                            <c:otherwise>
                                <input name="reqnumber" type="text" />
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <select name="priority">
                        <c:choose>
                            <c:when test="exist $invspz">
                                <option <c:if test="${invspz.priority}=='1'">selected="true"</c:if>>1</option>
                                <option <c:if test="${invspz.priority}=='2'">selected="true"</c:if>>2</option>
                                <option <c:if test="${invspz.priority}=='3'">selected="true"</c:if>>3</option>
                            </c:when>
                            <c:otherwise>
                                <option selected="true">1</option>
                                <option>2</option>
                                <option>3</option>
                            </c:otherwise>
                        </c:choose>
                       </select>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="exist $invspz">
                                <input type="date" name="issuedate" value="${invspz.issuedate}"/>
                            </c:when>
                            <c:otherwise>
                                <input type="date" name="issuedate"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <input type="text" name="contactperson"/>
                    </td>
                    <td><c:choose>
                            <c:when test="exist $invspz">
                            <select name="reqtype">
                                <option <c:if test="${invspz.reqtype == 'radna'}">selected="true"</c:if>>radna</option>
                                <option <c:if test="${invspz.reqtype = 'mimoradna'}">selected="true"</c:if>>mimoradna</option>
                            </select>
                            </c:when>
                            <c:otherwise>
                                <select name="reqtype">
                                    <option selected="true">radna</option>
                                    <option>mimoradna</option>
                                </select>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="exist $invspz">
                                <input type="text" name="shortname" value="${invspz.shortname}"/>
                            </c:when>
                            <c:otherwise>
                                <input type="text" name="shortname"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <textarea rows="3" cols="40" name="requestdescription">
                            <c:if test="exist $invspz">
                                <c:out value="${invspz.requestdescrption}"/>
                            </c:if>    
                        </textarea>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="exist $invspz">
                             <input type="date" name="implementationacceptancedate" value="${invspz.implementationacceptancedate}"/>
                            </c:when>
                            <c:otherwise>
                                <input type="date" name="implementationacceptancedate"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <input type="submit" value="Pridat"/>
                    </td>
                </form>
                </tr>
                
        </table>
    </body>
</html>
