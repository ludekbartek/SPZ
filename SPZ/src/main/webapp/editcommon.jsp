            <%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <f:setBundle basename="common"/>
            <tr>
                <td class="label"><f:message key="number"/>:</td>
                <td class="highlight-value"><c:out value="${spz.reqNumber}"/></td>
                <td class="label"><f:message key="priority"/>:</td>
                <td><c:out value="${spz.priority}"/></td>
                
            </tr>
            <tr>
                <td class="label"><f:message key="category"/>:</td>
                <td><c:choose>
                        <c:when test="${spz.category=='1'}"><f:message key="standard"/></c:when>
                        <c:otherwise><f:message key="nonstandard"/></c:otherwise>
                    </c:choose></td>
                <td/>
            </tr>
            <tr>
                <td class="label"><f:message key="name"/>:</td>
                <td class="highligh-value"><c:out value="${spz.shortName}" /></td>
                <td class="label"><f:message key="typ"/>:</td>
                <td><f:message key="${spz.requestType}"/></td>
            </tr>
            <tr>
                <td class="label"><f:message key="zadal"/>:</td>
                <td>
            <c:choose>
                <c:when test="${!empty spz.issuer}">
                    <c:out value="${spz.issuer}"/>
                </c:when>
                <c:otherwise>Administrator</c:otherwise>
            </c:choose>, <!--<c:out value="${spz.issueDate}"/>-->
            <f:formatDate dateStyle="LONG" type="both" timeStyle="SHORT" value="${spz.issueDate}"/> 
                </td>
                <td class="label"><f:message key="kontakt"/>:</td>
                <td><c:out value="${spz.contactPerson}"/></td>
            </tr>
            <tr>
                <td class="label"><f:message key="zadani"/>:</td>
                <td colspan="3"><c:out value="${spz.requestDescription}" escapeXml="false"/></td>
            </tr>
            <tr>
                <td class="label"><f:message key="analytik"/>:</td>
                <td colspan="3"><c:if test="${!empty spz.analyst}"><c:out value="${spz.analyst}"/></c:if></td>
            </tr>
            <tr>
                <td class="label"><f:message key="vyvojar"/>:</td>
                <td colspan="3">
                    <c:choose>
                        <c:when test="${!empty spz.developer}">
                            <c:out value="${spz.developer}"/>
                        </c:when>
                        <c:otherwise>
                            <f:message key="neprirazen"/>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
