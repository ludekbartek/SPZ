<%-- 
    Document   : editSPZ
    Created on : Sep 1, 2015, 3:12:20 PM
    Author     : bar
--%>
<!--
Pridat zadani typu radna/mimoradna, rozhodit editaci/vytvareni, 
pri editaci pouze popisy (viz stara verze).
-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="addSpz"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><f:message key="pageTitle"/></title>
       <!-- <script>
           procedure copy(where, wh)
           {
               shortname.value=reqnumber.value;
           }
       </script>-->
      <script src="scripts/support-scripts.js"></script>
      <link rel="stylesheet" href="styles/dcb.css" type="text/css"/>
      <meta name="description" content="addSPZ.jsp"/>
    </head>
    
    <body>
        <jsp:include page="headerspz.jsp"/>
        <div class="center"><jsp:include page="navigationSpz.jsp"/></div>
        <h1>
            <c:choose>
                    <c:when test="${action=='add'}">
                        <f:message key="pageTitle"/>
                    </c:when>
                    <c:otherwise>
                        <f:message key="pageTitleUpd"/>
                    </c:otherwise>
            </c:choose>
        </h1>
        <c:set var="jsp" value="./addSPZ.jsp"/>
        <c:if test="${!empty error}">
            <p style="background-color: yellow;text-align: center;color: red;font-size: xx-large">
                <c:out value="${error}"/>
            </p>
        </c:if>
        <div id="form">
            <form action="${pageContext.request.contextPath}/SPZServlet/addspz" method="post" enctype="multipart/form-data">
                <input type="hidden" name="userid" value="${user.id}"/>
                <input type="hidden" name="configid" value="${config.id}"/>
                <input type="hidden" name="projectid" value="${project.id}"/>
                <div class="formItem">
                    <span class="label"><label for="shortname"><f:message key="label"/></label></span>
                    <span class="input"><input type="text" name="shortname" maxlength="50" size="87" value="<c:if test="${!empty spz.reqnumber}">${spz.reqnumber}</c:if>"></span>
                </div>
                <div class="formItem">
                    <span class="label"><label for="contactperson"><f:message key="contactperson"/></label></span>
                    <span class="input"><input type="text" name="contactperson" size="87" maxlength="32" value="<c:if test="${!empty spz.contactperson}">${spz.contactperson}</c:if>"/></span>
                </div>
                <div class="formItem">
                    <span class="textarealabel"><label class="textarealabel" for="requestdescription"><f:message key="requestdescription"/></label></span>
                    <span class="input"><textarea name="requestdescription" rows="9" maxlength="9000" cols="100"></textarea></span>
                </div>
                <div class="formItem">
                    <span class="label"><label for="reqtype"><f:message key="reqtype"/></label></span>
                    <span class="input">
                        <select name="reqtype">
                            <option value="placeny"><f:message key="placeny"/></option>
                            <option value="neplaceny"><f:message key="neplaceny"/></option>
                        </select>
                    </span>
                </div>
                <div class="formItem">
                    <span class="label"><f:message key="category"/></span>
                    <span class="input">
                        <select name="category">
                            <option value="1" selected><f:message key="standard"/></option>
                            <option value="0"><f:message key="nonstandard"/></option>
                        </select>
                    </span>
                </div>
                <div class="formItem">
                    <span class="label"><label for="priority"><f:message key="priority"/></label></span>
                    <span class="input">
                        <select name="priority">
                            <option value="1" <c:if test="${priority=='1'}">selected</c:if>>1</option>
                            <option value="2" <c:if test="${priority=='2'}">selected</c:if>>2</option>
                            <option value="3" <c:if test="${priority=='3' or empty priority}">selected</c:if>>3</option>
                            <option value="4" <c:if test="${priority=='4'}">selected</c:if>>4</option>
                            <option value="5" <c:if test="${priority=='5'}">selected</c:if>>5</option>
                        </select>
                    </span>
                </div>
                        
                <div class="formItem">
                    <span class="label"><label for="desc"><f:message key="desc"/></label></span>
                    <span class="input">
                        <textarea name="desc" maxlength="8000" cols="100" rows="5"></textarea>
                    </span>
                </div>
                <div class="debug">
                    Role: <c:out value="${user.role}"/>
                </div>
            <c:choose> 
                <c:when test="${user.role ne 0}">
                <div class="formItem">
                    <span class="input"><input type="checkbox" id="ext" name="external" value="1"/></span>
                    <span class="label"><label for="ext"><f:message key="ext"/></label></span>
                </div>
                </c:when>
                <c:otherwise>
                    <span class="input"><input type="hidden" id="ext" name="external" value="1"/></span>
                </c:otherwise>
            </c:choose>
                <div class="label"><f:message key="attachments"/></div>
                <div class="formItem">
                <span class="file">
                    <span class="label"><label for="soubor1"><f:message key="file1"/></label></span>
                    <span class="input"><input type="file" name="soubor1"/></span>
                </span>
                <span class="file">
                    <span class="label"><label for="soubor2"><f:message key="file2"/></label></span>
                    <span class="input"><input type="file" name="soubor2"/></span>
                </span>
                <span class="file">
                    <span class="label"><label for="soubor3"><f:message key="file3"/></label></span>
                    <span class="input"><input type="file" name="soubor3"/></span>
                </span>
                </div>
                <div class="formItem">
                    <span class="label"><input type="submit" value="<f:message key="register"/>" onfocus="shortname.value = reqnumber.value;"/></span>
                </div>
            </form>
        </div>
    </body>
</html>
