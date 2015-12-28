            <%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <tr>
                <td class="label">Cislo:</td>
                <td class="highlight-value"><c:out value="${spz.reqNumber}"/></td>
                <td class="label">Priorita:</td>
                <td><c:out value="${spz.priority}"/></td>
                
            </tr>
            <tr>
                <td class="label">Kategorie:</td>
                <td><c:choose>
                        <c:when test="${spz.category=='1'}">Standardni</c:when>
                        <c:otherwise>Nestandardni</c:otherwise>
                    </c:choose></td>
                <td/>
            </tr>
            <tr>
                <td class="label">Nazev:</td>
                <td class="highligh-value"><c:out value="${spz.shortName}" /></td>
                <td class="label">Typ:</td>
                <td><c:out value="${spz.requestType}"/></td>
            </tr>
            <tr>
                <td class="label">Zadal:</td>
                <td>
            <c:choose>
                <c:when test="${!empty spz.issuer}">
                    <c:out value="${spz.issuer}"/>
                </c:when>
                <c:otherwise>Administrator</c:otherwise>
            </c:choose>, <!--<c:out value="${spz.issueDate}"/>-->
            <f:formatDate dateStyle="LONG" type="both" timeStyle="SHORT" value="${spz.issueDate}"/> 
                </td>
                <td class="label">Kontakt</td>
                <td><c:out value="${spz.contactPerson}"/></td>
            </tr>
            <tr>
                <td class="label">Zadani:</td>
                <td colspan="3"><c:out value="${spz.requestDescription}" escapeXml="false"/></td>
            </tr>
            <tr>
                <td class="label">Analytik:</td>
                <td colspan="3"><c:if test="${!empty spz.analyst}"><c:out value="${spz.analyst}"/></c:if></td>
            </tr>
            <tr>
                <td class="label">Vyvojar</td>
                <td colspan="3">
                    <c:choose>
                        <c:when test="${!empty spz.developer}">
                            <c:out value="${spz.developer}"/>
                        </c:when>
                        <c:otherwise>
                            Neprirazen
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
