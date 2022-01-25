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
<h4>Добавить организацию</h4>
<table frame="hsides">
    <tr>
        <td><input type="text" placeholder="Введите название компании"
                   name="${sectionName}_companyName" size=50>
        </td>
        <td><input type="text" placeholder="Введите ссылку на сайт, если есть"
                   name="${sectionName}_companyUrl" size=50>
        </td>
    </tr>
    <c:set var="prefix" value="${sectionName}New"/>
    <jsp:useBean id="prefix" scope="request" type="java.lang.String"/>
    <jsp:include page="/WEB-INF/jsp/fragments/newExperience.jsp">
        <jsp:param name="sectionName" value="sectionName"/>
        <jsp:param name="prefix" value="prefix"/>
    </jsp:include>
</table>