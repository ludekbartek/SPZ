
            <tr>
                <td class="label">Cislo:</td>
                <td class="highlight-value"><c:out value="${spz.reqnumber}"/></td>
                <td class="label">Priorita:</td>
                <td><c:out value="${spz.priority}"/></td>
            </tr>
            <tr>
                <td class="label">Nazev:</td>
                <td class="highligh-value"><c:out value="${spz.shortname}"/></td>
                <td class="label">Typ:</td>
                <td><c:out value="${spz.reqtype}"/></td>
            </tr>
            <tr>
                <td class="label">Zadal:</td>
                <td><c:out value="${spz.issuer}"/>, <c:out value="{spz.issuedate}"/></td>
                <td class="label">Kontakt</td>
                <td><c:out value="${spz.contactperson}"/></td>
            </tr>
            <tr>
                <td class="label">Zadani:</td>
                <td colspan="3"><c:out value="${spz.requestdescription}"/></td>
            </tr>
            <tr>
                <td class="label">Analytik:</td>
                <td colspan="3"><c:out value="${spz.analyst}"/></td>
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
