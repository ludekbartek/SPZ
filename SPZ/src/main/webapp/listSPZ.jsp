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
        <title>System servisni podpory</title>
        <script type="text/javascript">
            copyValue(from, to)
            {
                document.write(from);
                to.value = from.value;
            }
        </script>
    </head>
    <body>
        <h1>System servisni podpory</h1>
        <div id="heading" style="width: 100%">
            <span class="filter" style="text-align: center;width:10%;float:left;">
                <div class="title">Filtr:</div>
                <div class="selector">
                    <select id="filter" name="filter">
                        <option></option>
                    </select>
                </div>
            </span>
            <span class="search" style="text-align: center;width:30%;float:left;">
                <div>Hledany text:</div>
                <div><input type="text" width="15" name="searched" id="searched" onchange="copy(searched,value);"/></div>
            </span>
            <span class="where" style="text-align: center;width:20%;float:left;">
                <div>V polich:</div>
                <div>
                    <select name="fields">
                        <option></option>
                    </select>
                </div>
            </span>
            <span class="actions" style="text-align: left;float: left;">
                <span class="action" style="text-align: left;float: left;">
                <form action="${pageContext.request.contextPath}/SPZServlet/listspz" method="post">
                    <input id="value" type="hidden" name="value"/>
                    <input type="submit" value="Hledej"/>
                </form>
                </span>
                <span class="action" style="text-align: left;float: left;">
                <form action="${pageContext.request.contextPath}/SPZServlet/addspz" method="post">
                    <input type="submit" value="Nova SPZ"/>
                </form>
                </span>
                <span class="action" style="text-align: left;float:left;">
                <form action="${pageContext.request.contextPath}/SPZServlet/reports" method="post">
                    <input type="submit" value="Sestavy"/>
                </form>
                </span>
            </span>
        </div>
        <p/>
        <div id="table" style="width:100%;float:left;">
        <table>
            <thead style="border-bottom: black solid;background-color: #cccccc">
                <th style="width:9%">Cislo <br/>pozadavku</th>
                <th style="width:5%">Pri.</th>
                <th>Typ <br/>pozadavku</th>
                <th>Zadal</th>
                <th>Kontaktni<br/> osoba</th>
                <th>Dne</th>
                <th>Zadani</th>
                <th>Specs<br/> dne</th>
                <th>Odhad.<br/> prac.</th>
                <th>Instalace</th>
                <th>Skut. prac.</th>
                <th>Stav<br/> SPZ</th>
                <th>Ze dne</th>
            </thead>
            <c:forEach items="${spzs}" var="item">
                <tr>
                <!--    <td>
                        <c:out value="${item.id}"/>
                        <input type="hidden" name="id" value="${item.id}"/>
                    </td>-->
                    <td>
                        <a href="${pageContext.request.contextPath}/SPZServlet/edit?id=${item.id}"><c:out  value="${item.reqnumber}"/>
                    </td>
                    <c:out value="${item.prioity}"/>
                    <td>
                   <!--     <select name="priority">
                            <option <c:if test="${item.priority == 1}">selected="true"</c:if>>1</option>
                            <option <c:if test="${item.priority == 2}">selected="true"</c:if>>2</option>
                            <option <c:if test="${item.priority == 3}">selected="true"</c:if>>3</option>
                        </select>-->
                        
                    </td>
                    <td>
                        <c:out value="${item.requesttype}"/>
                    </td>
                    <td>
                        zde bude zadavatel
                    </td>
                    <td>
                        <c:out value="${item.contactperson}"/>
                    </td>
                    <td>
                        <f:formatDate dateStyle="dd.mm.yy" value="${item.issuedate}"/> 
                    </td>
                    <td>
                        <c:out value="${item.requestdescription}"/>
                    </td>
                    <td>
                        <f:formatDate value='${item.implementationacceptdate}' pattern='dd.MM.yyyy'/>
                    </td>
                    
                </tr>
                
            </c:forEach>
              <!--  <tr>
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
                            <c:when test="not empty $invspz">
                                <option value="1" <c:if test="${invspz.priority}==1">selected="true"</c:if>>1</option>
                                <option value="2" <c:if test="${invspz.priority}==2">selected="true"</c:if>>2</option>
                                <option value="3" <c:if test="${invspz.priority}==3">selected="true"</c:if>>3</option>
                            </c:when>
                            <c:otherwise>
                                <option value="1" selected="true">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
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
                                <option value="radna"<c:if test="${invspz.reqtype == 'radna'}">selected="true"</c:if>>radna</option>
                                <option value="mimoradna"<c:if test="${invspz.reqtype = 'mimoradna'}">selected="true"</c:if>>mimoradna</option>
                            </select>
                            </c:when>
                            <c:otherwise>
                                <select name="reqtype">
                                    <option value="radna" selected="true">radna</option>
                                    <option value="mimoradna">mimoradna</option>
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
                </tr>-->
        </table>
        </div>
    </body>
</html>
