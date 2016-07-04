<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="addNote"/>
<h2><f:message key="addNoteHeading"/></h2>
<div>
    <form action="${pageContext.request.contextPath}/SPZServlet/addNote" enctype="multipart/form-data" method="post">
        <div class="note">
            <div class="formItem">
                <span class="areainput">
                    <textarea class="areainput" name="desc" cols="100" rows="9" maxlength="8000"><c:if test="${!empty desc}"><c:out value="${desc}"/></c:if></textarea>
                </span>
            </div>
            <input type="hidden" name="spzid" value="${spz.id}"/>
            <input type="hidden" name="jsp" value="${jsp}"/>
            <input type="hidden" name="userid" value="${user.id}"/>
            <input type="hidden" name="configid" value="${config.id}"/>
            <input type="hidden" name="projectid" value="${project.id}"/>
            <input type="checkbox" name="external" id="ext" <c:if test="${!empty ext and ext=='true'}">checked</c:if>/><label for="ext"><f:message key="extNote"/></label>
            <div class="formItem">
                <span class="label">
                    <input type="submit" value="<f:message key="saveNote"/>"/>
                </span>
            </div>
        </div>
        <div class="attachments">
            <div class="formItem">
                <span class="file">
                    <span class="label">Soubor 1:</span>
                    <span class="input">
                        <input type="file" name="soubor1"/>
                    </span>
                </span>
            </div>
            <div class="formItem">
                <span class="file">
                    <span class="label">Soubor 2:</span>
                    <span class="input">
                        <input type="file" name="soubor2" />
                    </span>
                </span>
            </div>
            <div class="formItem">
                <span class="file">
                    <span class="label">Soubor 3:</span>
                    <span class="input">
                        <input type="file" name="soubor3" />
                    </span>
                </span>
            </div>
        </div>
    </form>
</div>