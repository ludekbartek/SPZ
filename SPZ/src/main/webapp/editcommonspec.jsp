<%-- 
    Document   : editcommonspec
    Created on : Jan 17, 2016, 4:54:52 PM
    Author     : bar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<f:setBundle basename="editSpec"/>
<jsp:include page="editcommon.jsp"/>
<tr>
    <td class="label"><f:message key="revisedReq"/>:</td>
    <td colspan="3"><c:out value="${spz.revised}" escapeXml="false"/></td>
</tr>
<tr>
    <td class="label"><f:message key="solProp"/>:</td>
    <td colspan="3"><c:out value="${spz.solution}" escapeXml="false"/></td>
</tr>
<tr>
    <td class="label"><f:message key="workLoadEst"/>:</td>

    <td colspan="3"><f:formatNumber minFractionDigits="2" maxFractionDigits="2" value="${spz.workLoadEstimation}"/> <f:message key="manHours"/> 
        (<f:formatNumber minFractionDigits="2" maxFractionDigits="2" value="${spz.workLoadEstimation/8.0}"/> <f:message key="manDays"/>)</td>
</tr>