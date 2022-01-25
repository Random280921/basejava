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
<tr><td><h5>Добавить опыт</h5></td><td></td></tr>
<tr>
    <td>
        <table>
            <tr>
                <td><input type="text" placeholder="Дата c MM/YYYY"
                           name="${sectionName}_companyNewDtB" size=20>
                </td>
                <td><input type="text" placeholder="Дата до MM/YYYY"
                           name="${sectionName}_companyNewDtE" size=20>
                </td>
        </table>
    </td>
    <td><input type="text" placeholder="Должность/Курс"
               name="${sectionName}_companyNewTitle" size=100>
    </td>
</tr>
</tr>
<c:if test="${sectionName != \"EDUCATION\"}">
    <tr>
        <td></td>
        <td>
        <textarea name="${sectionName}_companyNewText"
                  wrap="soft" rows="3" cols="100"
                  placeholder="Введите описание выполняемой работы"></textarea>
        </td>
    </tr>
</c:if>