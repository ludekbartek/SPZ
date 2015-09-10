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
       <title>Nova SPZ</title>
       <script>
           procedure copy()
           {
               shortname.value=reqnumber.value;
           }
       </script>
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
        <h1>
            <c:choose>
                    <c:when test="${action=='add'}">
                        Nova SPZ
                    </c:when>
                    <c:otherwise>
                        Uprava SPZ
                    </c:otherwise>
            </c:choose>
        </h1>
        <c:if test="${!empty error}">
            <p style="background-color: yellow;text-align: center;color: red;font-size: xx-large">
                <c:out value="${error}"/>
            </p>
        </c:if>
        <div id="form" style="width: 100%;">
            <form action="${pageContext.request.contextPath}/SPZServlet/addspz" method="post">
                <div class="formItem">
                    <span class="label"><label class="label" for="reqnumber">Nazev</label></span>
                    <span class="input"><input type="text" name="reqnumber" value="<c:if test="${!empty spz.reqnumber}">${spz.reqnumber}</c:if>"></span>
                    <input type="hidden" name="shortname"/>
                </div>
                <div class="formItem">
                    <span class="label"><label for="contactperson">Kontakt</label></span>
                    <span class="input"><input type="text" name="contactperson" value="<c:if test="${!empty spz.contactperson}">${spz.contactperson}</c:if>"/></span>
                </div>
                <div class="formItem">
                    <span class="label"><label class="label" for="requestdescription">Zadani</label></span>
                    <span class="input"><textarea name="requestdescription" rows="5" cols="80"></textarea></span>
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
                            <option value="1" <c:if test="${priority=='1'}">selected="true"</c:if>>1</option>
                            <option value="2" <c:if test="${priority=='2'}">selected="true"</c:if>>2</option>
                            <option value="3" <c:if test="${priority=='3'}">selected="true"</c:if>>3</option>
                        </select>
                    </span>
                </div>
                <div class="formItem">
                    <span class="label"><label for="desc">Poznamka</label></span>
                    <span class="input">
                        <textarea name="desc" cols="80" rows="5">
                            
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
                        <span class="formItem"><input type="submit" value="Registrovat novou SPZ" onfocus="copy();"/></span>
        </form>
        </div>
    </body>
</html>
