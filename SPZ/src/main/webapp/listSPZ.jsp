<%-- 
    Document   : listSPZ
    Created on : Aug 26, 2015, 7:15:26 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="list"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><f:message key="header"/></title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/dcb.css" type="text/css"/>
        <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/support-scripts.js"></script>
    </head>
    <body>
        <jsp:include page="headerspz.jsp"/>
        <div class="center">
            <jsp:include page="navigationProject.jsp"/>
        </div>
        <c:if test="${! empty error}">
            <div id="erorr" style="background-color: yellow;border-color: red;">
                <c:out value="${error}"/>
            </div>
        </c:if>
            <h1><f:message key="spzList"/></h1>
        <div id="listspzheading" style="width: 98%">
                <form id="spzlist" action="${pageContext.request.contextPath}/SPZServlet/listspz" method="post" style="leftfloat">
                    <input type="hidden" name="userid" value="${user.id}"/>
                    <input type="hidden" name="projectid" value="${project.id}"/>
                    <input type="hidden" name="configid" value="${config.id}"/>
                    <span class="filter" style="text-align: center;width:20%;float:left;">
                        <span class="title">Filtr:</span>
                        <span class="filter-select">
                            <select name="filter" onchange="doPost(spzlist);">
                                <option value="0" <c:if test="${filter == '0'}">SELECTED</c:if>>Všechny</option>
                                <option value="1" <c:if test="${filter == '1'}">SELECTED</c:if>>Jen neuzavřené</option>
                                <option value="2" <c:if test="${filter == '2'}">SELECTED</c:if>>Čekající na klienta</option>
                                <option value="3" <c:if test="${filter == '3'}">SELECTED</c:if>>Čekající na implementaci</option>
                                <option value="4" <c:if test="${filter == '4'}">SELECTED</c:if>>Instalované/Čeká na převzetí</option>
                                <option value="5" <c:if test="${filter == '5'}">SELECTED</c:if>>Převzaté/Čeká na fakturaci</option>
                                <option value="6" <c:if test="${filter == '6'}">SELECTED</c:if>>Jen vyřešené</option>
                                <option value="7" <c:if test="${filter == '7'}">SELECTED</c:if>>Jen zrušené</option>
                            </select>
                        </span>
                    </span>
                    <span class="search" style="text-align: center;width:30%;float:left;">
                        <span>Hledany text:</span>
                        <span><input type="text" size="18" maxlength="18" name="searched"/></span>
                    </span>
                    <span class="where" style="width: 30%;float:left;">
                        <span>V polich:</span>
                        <span>
                            <select name="fields">
                                <option value="" SELECTED>Bez omezení</option>
                                <option value="name" >Název SPZ</option>
                                <option value="desc" >Zadání SPZ</option>
                                <option value="rdesc" >Revidované zadání</option>
                                <option value="solution" >Řešení SPZ</option>
                            </select>
                        </span>
                    </span>
                    <input type="submit" value="<f:message key='hledej'/>"/>
                </form>
                <form id="reports" action="${pageContext.request.contextPath}/SPZServlet/reports" method="post" class="rightfloat">    
                    <input type="hidden" name="userid" value="${user.id}"/>
                    <input type="hidden" name="configid" value="${config.id}"/>
                    <input type="hidden" name="projectid" value="${project.id}"/>
                    <%--<input type="submit" value='<f:message key="reports"/>'/>--%>
                    <a href="javascript: doPost(reports)"><f:message key="reports"/></a>
                </form>
                <form id="addspz" action="${pageContext.request.contextPath}/SPZServlet/addspz" method="post" class="rightfloat">
                    <!--<input type="hidden" name="action" value="/addspz"/>-->
                    <input type="hidden" name="userid" value="${user.id}"/>
                    <input type="hidden" name="configid" value="${config.id}"/>
                    <input type="hidden" name="projectid" value="${project.id}"/>
                    <%--<input type="submit" value='<f:message key="newSpz"/>'/>--%>
                    <a href="javascript: doPost(addspz)"><f:message key="newSpz"/></a>
                </form>
        </div>
        <br/>
        <div id="table">
        <table class="fullwidthtable">
            <thead>
                <th><f:message key="reqid"/></th>
                <th><f:message key="priority"/><!--Pri.--></th>
                <th><f:message key="reqtype"/><!--Typ pozadavku--></th>
                <th><f:message key="issuer"/><!--Zadal--></th>
                <th><f:message key="contact"/><!--Kontaktni osoba--></th>
                <th><f:message key="issuedate"/><!--Dne--></th>
                <th><f:message key="reqspec"/><!--Zadani--></th>
                <th><f:message key="specdate"/><!--Specs dne--></th>
                <th><f:message key="estwork"/><!--Odhad. prac.--></th>
                <th><f:message key="install"/><!--Instalace--></th>
                <th><f:message key="realwork"/><!--Skut. prac.--></th>
                <th><f:message key="state"/><!--Stav SPZ--></th>
                <th><f:message key="category"/><!--Kategorie--></th>
                <th><f:message key="changedate"/><!--Ze dne--></th>
            </thead>
            <c:forEach items="${spzs}" var="item">
                <tr>
                
                    <td class="item">
                        <c:set var="formid" value="spz${item.reqNumber}"/>
                        <form name="${formid}" action="${pageContext.request.contextPath}/SPZServlet/editspz" method="post">
                            <input type="hidden" name="spzid" value="${item.id}"/>
                            <input type="hidden" name="userid" value="${user.id}"/>
                            <input type="hidden" name="configid" value="${config.id}"/>
                            <input type="hidden" name="projectid" value="${project.id}"/>
                            <input type="hidden" name="reqNumber" value="${item.reqNumber}"/>
                            <%-- <input type="submit" value="${item.reqNumber}"/>--%>
                            <input type="hidden" name="priority" value="${item.priority}"/>
                            <input type="hidden" name="requestType" value="${item.requestType}"/>
                            <input type="hidden" name="contactPerson" value="${item.contactPerson}"/>
                            <input type="hidden" name="issuedate" value="${item.issueDate}"/>
                            <input type="hidden" name="shortName" value="${item.shortName}"/>
                            <input type="hidden" name="implementationAcceptDate" value="${item.implementationAcceptDate}"/>
                            <a href="javascript: doPost(${formid})"><c:out value="${item.reqNumber}"/></a>
                        </form>
                            
                    </td>
                   <td class="item">
                        <c:out value="${item.priority}"/>
                    </td>
                    <td class="item">
                        <f:message key="${item.requestType}"/>
                    </td>
                    <td class="item">
                        <c:out value="${item.issuer}"/>
                    </td>
                    <td class="item">
                        <c:out value="${item.contactPerson}"/>
                    </td>
                    <td class="item">
                        <f:formatDate pattern="d.M.yy" value="${item.issueDate}"/> 
                    </td>
                    <td class="item">
                        <c:out value="${item.shortName}"/>
                    </td>
                    <td class="item">
                       <f:formatDate value="${item.specDate}" pattern="d.M.yy"/>
                    </td>
                    <td class="item">
                       <c:if test="${item.workLoadEstimation gt 0}">
                          <f:formatNumber value="${item.workLoadEstimation}"/> <f:message key="manhour"/>
                        </c:if>
                    </td>
                    <td class="item">
                        <f:formatDate value="${item.installDate}" pattern="d.M.yy"/>
                    </td>
                    <td class="item">
                        <c:if test="${item.workLoadReal gt 0}">
                            <f:formatNumber value="${item.workLoadReal}"/> <f:message key="manhour"/>
                        </c:if>
                    </td>
                    <td class="item" <c:if test="${item.canContinue}">style="color: green"</c:if>>
                        <f:message key="${item.spzState}"/>
                    </td>
                    <td class="item">
                        <c:choose>
                            <c:when test="${item.category=='1'}"><f:message key="standard"/></c:when>
                            <c:otherwise><f:message key="nonstandard"/></c:otherwise>
                        </c:choose>
                    </td>
                    <td class="item">
                        <%--<c:out value="${item.date}"/>--%>
                        <f:formatDate value="${item.date}" pattern="d.M.yy"/>
                    </td>
                </tr>
                
            </c:forEach>
              <%--  <tr>
                <form action="${pageContext.request.contextPath}/SPZServlet/addSPZ" method="post">
                    <td>
                        
                    </td>
                    <td>
                        <c:choose> 
                            <c:when test="exist $invspz">
                                <input name="reqNumber" type="text" value="{$spz.reqNumber}"/>
                            </c:when>
                            <c:otherwise>
                                <input name="reqNumber" type="text" />
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <select name="priority">
                        <c:choose>
                            <c:when test="not empty $invspz">
                                <option value="1" <c:if test="${invspz.priority}==1">selected="true"</c:if>1</option>
                                <option value="2" <c:if test="${invspz.priority}==2">selected="true"</c:if>2</option>
                                <option value="3" <c:if test="${invspz.priority}==3">selected="true"</c:if>3</option>
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
                                <input type="date" name="issuedate" value="${invspz.issueDate}"/>
                            </c:when>
                            <c:otherwise>
                                <input type="date" name="issueDate"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <input type="text" name="contactPerson"/>
                    </td>
                    <td><c:choose>
                            <c:when test="exist $invspz">
                            <select name="reqtype">
                                <option value="radna"<c:if test="${invspz.reqType == 'radna'}">selected="true"</c:if>>radna</option>
                                <option value="mimoradna"<c:if test="${invspz.reqType = 'mimoradna'}">selected="true"</c:if>>mimoradna</option>
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
                                <input type="text" name="shortName" value="${invspz.shortName}"/>
                            </c:when>
                            <c:otherwise>
                                <input type="text" name="shortName"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <textarea rows="3" cols="40" name="requestdescription">
                            <c:if test="exist $invspz">
                                <c:out value="${invspz.requestDescrption}"/>
                            </c:if>    
                        </textarea>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="exist $invspz">
                             <input type="date" name="implementationAcceptanceDate" value="${invspz.implementationAcceptanceDate}"/>
                            </c:when>
                            <c:otherwise>
                                <input type="date" name="implementationAcceptanceDate"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <input type="submit" value="Pridat"/>
                    </td>
                </form>
                </tr>--%>
        </table>
        </div>
    </body>
</html>
