<%-- 
    Document   : editSPZ
    Created on : Apr 11, 2017, 4:56:24 AM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="editSPZ"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><f:message key="pageTitle"/> ${spz.id} (${user.login})</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/dcb.css" type="text/css"/>
        <script type="text/javascript" src="scripts/support-scripts.js"></script>
    </head>
    <body>
        <jsp:include page="headerspz.jsp"/>
        <div class="center">
            <jsp:include page="navigationSpzEdit.jsp"/>
        </div>
        <h2><f:message key="pageTitle"/></h2>
        <form action="${pageContext.request.contextPath}/SPZServlet/updateSPZ" method="post">
            <input type="hidden" name="userid" value="${user.id}"/>
            <input type="hidden" name="changed" value="1"/>
            <input type="hidden" name="projectid" value="${project.id}"/>
            <input type="hidden" name="configid" value="${config.id}"/>
            <input type="hidden" name="requestnumber" value="${spz.reqNumber}"/>
            <div class="formItem">
                <span class="label">
                    <label for="spzid"><f:message key="id"/></label>
                </span>
                <span class="input">
                    <input type="text" readonly name="spzid" class="textinput" value="${spz.id}"/>
                </span>
            </div>
            <div class="formItem">
                <span class="label">
                    <label for="shortname"><f:message key="shortname"/></label>
                </span>
                <span class="input">
                    <input type="text" name="shortname" value="${spz.shortName}" class="textinput"/>
                </span>
            </div>
            <div class="formItem">
                <span class="label"><f:message key="contact"/></span>
                <span class="input">
                    <input type="text" name="contactperson" value="${spz.contactPerson}" class="textinput"/>
                </span>
            </div>
            <div class="formItem">
                <span class="textarealabel"><f:message key="requestdescription"/></span>
                <span class="input">
                    <textarea class="textareainput" name="requestdescription" rows="9" maxlength="9000" <%--cols="100-"--%>>
                        <c:out value="${spz.requestDescription}"/>
                    </textarea>
                </span>
            </div>
            <div class="formItem">
                <span class="label"><label for="reqtype"><f:message key="reqtype"/></label></span>
                <span class="input">
                    <select name="reqtype" id="reqtype">
                        <option value="placeny" <c:if test="${spz.requestType=='placeny'}">selected</c:if>><f:message key="placeny"/></option>
                        <option value="neplaceny" <c:if test="${spz.requestType='neplaceny'}">selected</c:if>><f:message key="neplaceny"/></option>
                    </select>
                </span>
            </div>
            <div class="formItem">
                <span class="label"><label for="category"><f:message key="category"/></label></span>
                <span class="input">
                    <select name="category" id="category">
                        <option value="1" <c:if test="${spz.category==1}">selected</c:if>><f:message key="standard"/></option>
                        <option value="0" <c:if test="${spz.category==0}">selected</c:if>><f:message key="nonstandard"/></option>
                    </select>
                </span>
            </div>
            <div class="formItem">
                    <span class="label"><label for="priority"><f:message key="priority"/></label></span>
                    <span class="input">
                        <select name="priority" id="priority">
                            <option value="1" <c:if test="${priority=='1'}">selected</c:if>>1</option>
                            <option value="2" <c:if test="${priority=='2'}">selected</c:if>>2</option>
                            <option value="3" <c:if test="${priority=='3' or empty priority}">selected</c:if>>3</option>
                            <option value="4" <c:if test="${priority=='4'}">selected</c:if>>4</option>
                            <option value="5" <c:if test="${priority=='5'}">selected</c:if>>5</option>
                        </select>
                    </span>
            </div>
            <div class="formItem">
                <span class="input">
                    <input type="submit" value="<f:message key='changeSPZ'/>"/>
                </span>
            </div>
        </form>
    </body>
</html>
