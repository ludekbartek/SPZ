<%-- 
    Document   : editSPZ
    Created on : Sep 1, 2015, 3:12:20 PM
    Author     : bar
--%>

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
        <div id="form" style="width: 100%;">
            <form action="${pageContext.request.contextPath}/SPZServlet/add" method="post">
                <div class="formItem">
                    <span class="label"><label class="label" for="spzName">Nazev</label></span>
                    <span class="input"><input type="text" name="name" value="<c:if test="!empty ${spz.name}">${spz.name}</c:if>"></span>
                </div>
                <div class="formItem">
                    <span class="label"><label for="contact">Kontakt</label></span>
                    <span class="input"><input type="text" name="contact" value="<c:if test="!empty ${spz.contact}">${spz.contact}</c:if>"/></span>
                </div>
                <div class="formItem">
                    <span class="label"><label class="label" for="description">Zadani</label></span>
                    <span class="input"><textarea name="description" rows="5" cols="80"></textarea></span>
                </div>
                <div class="formItem">
                    <span class="label"><label for="type">Typ:</label></span>
                    <span class="input">
                        <select name="type">
                            <option selected="true" value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
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
                <span class="formItem"><input type="submit" value="Registrovat novou SPZ"/></span>
        </form>
        </div>
    </body>
</html>
