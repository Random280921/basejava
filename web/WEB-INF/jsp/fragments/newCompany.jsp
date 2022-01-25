<%--
  Created by IntelliJ IDEA.
  User: KAIvanov
  Date: 24.01.2022
  Time: 20:22
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="sectionName" value="${requestScope[param.sectionName]}"/>
<jsp:useBean id="sectionName" type="java.lang.String"/>
<c:set var="partName" value="${sectionName}New"/>
<jsp:useBean id="partName" type="java.lang.String"/>
<h4>Добавить организацию</h4>
<table frame="hsides">
    <tr>
        <td><input type="text" placeholder="Введите название компании"
                   name="${partName}Name" size=50>
        </td>
        <td><input type="text" placeholder="Введите ссылку на сайт, если есть"
                   name="${partName}Url" size=50>
        </td>
    </tr>
    <tr>
        <td><h5>Добавить опыт</h5></td>
        <td></td>
    </tr>
    <tr>
        <td>
            <table>
                <tr>
                    <td><input type="text" placeholder="Дата c MM/YYYY"
                               name="${partName}DtB" size=20>
                    </td>
                    <td><input type="text" placeholder="Дата до MM/YYYY"
                               name="${partName}DtE" size=20>
                    </td>
            </table>
        </td>
        <td><input type="text" placeholder="Должность/Курс"
                   name="${partName}Title" size=100>
        </td>
    </tr>
    </tr>
    <c:if test="${sectionName != \"EDUCATION\"}">
        <tr>
            <td></td>
            <td>
        <textarea name="${partName}Text"
                  wrap="soft" rows="3" cols="100"
                  placeholder="Введите описание выполняемой работы"></textarea>
            </td>
        </tr>
    </c:if>
</table>