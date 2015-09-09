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
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <c:if test="${action=='add'}"><title>Nova SPZ</title></c:if>
        <c:if test="${action=='edit'}"><title>Uprava SPZ</title></c:if>
        <style>
            h1{
                text-align: center;
            }
            form{
                width:100%;
            }
            label{
                width: 20%;
            }
            .formItem{
                width: 80%;
            }
        </style>
    </head>
    
    <body>
        <h1>Uprava SPZ</h1>
        <div id="form" style="width: 100%;">
            <form action="${pageContext.request.contextPath}/SPZServlet/editspz" method="post">
                <div class="formItem">
                    <span class="label"><label class="label" for="reqnumber">Nazev</label></span>
                    <span class="input"><input type="text" name="reqnumber" value="<c:if test='${not empty spz.reqnumber}'><c:out value='${spz.reqnumber}'/></c:if>"></span>
                    <c:out value="${spz.reqnumber}"/>
                </div>
                <div class="formItem">
                    <span class="label"><label for="contactperson">Kontakt</label></span>
                    <span class="input"><input type="text" name="contactperson" value="<c:if test='${!empty spz.contactperson}'><c:out value='${spz.contactperson}'/></c:if>"/></span>
                </div>
                <div class="formItem">
                    <span class="label"><label class="label" for="requestdescription">Zadani</label></span>
                    <span class="input"><textarea name="requestdescription" rows="5" cols="80">
                        <c:if test="${!empty requestdescription}"><c:out value="${requestdescription}"/></c:if></textarea></span>
                </div>
                <div class="formItem">
                    <span class="label"><label for="reqtype">Typ:</label></span>
                    <span class="input">
                        <select name="reqtype">
                            <option value="placeny">Placeny</option>
                            <option value="neplaceny">Neplaceny</option>
                        </select>
                    </span>
                </div>
                <div class="formItem">
                    <span class="label"><label for="priority">Priorita</label></span>
                    <span class="input">
                        <select name="priority">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3" selected="true">3</option>
                        </select>
                    </span>
                </div>
                <div class="formItem">
                    <span class="label"><label for="desc">Poznamka</label></span>
                    <span class="input">
                        <textarea name="desc" cols="80" rows="5"><c:out value="${desc}"/>
                            
                        </textarea>
                    </span>
                </div>
                <div class="formItem">
                    <input type="checkbox" id="ext" name="external" value="1"/>
                    <label for="ext">Viditelna i pro zakaznika?</label>
                </div>
                <div class="file">
                    <span class="label"><label for="file1">Soubor 1:</label></span>
                    <span class="input"><input type="file" name="file1"/></span>
                </div>
                <div class="file">
                    <span class="label"><label for="file2">Soubor 2:</label></span>
                    <span class="input"><input type="file" name="file2"/></span>
                </div>
                <span class="formItem"><input type="submit" value="Registrovat novou SPZ"/></span>
        </form>
        </div>
    </body>
</html>
