<%--
  Created by IntelliJ IDEA.
  User: KAIvanov
  Date: 23.01.2022
  Time: 18:36
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:set var="sectionName" value="${requestScope[param.sectionName]}"/>
<c:choose>
    <c:when test="${sectionName == \"OBJECTIVE\" || sectionName == \"PERSONAL\"}">
        <input type="text" name="${sectionName}" size=160
               placeholder="Введите описание">
    </c:when>
    <c:when test="${sectionName == \"ACHIEVEMENT\" || sectionName == \"QUALIFICATIONS\"}">
        <textarea name="${sectionName}" wrap="soft" rows="10" cols="150"
                  placeholder="Введите список позиций (разделение переводом строки)"></textarea>
    </c:when>
</c:choose>
