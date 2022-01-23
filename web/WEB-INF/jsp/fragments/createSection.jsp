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
    <c:when test="${sectionName == \"OBJECTIVE\"}">
        <input type="text" name="sectionOBJECTIVE" size=160
               placeholder="Введите описание">
    </c:when>
    <c:when test="${sectionName == \"PERSONAL\"}">
        <input type="text" name="sectionPERSONAL" size=160
               placeholder="Введите описание">
    </c:when>
    <c:when test="${sectionName == \"ACHIEVEMENT\"}">
        <textarea name="sectionACHIEVEMENT" wrap="soft" rows="10" cols="150"
                  placeholder="Введите список позиций (разделение переводом строки)"></textarea>
    </c:when>
    <c:when test="${sectionName == \"QUALIFICATIONS\"}">
        <textarea name="sectionQUALIFICATIONS" wrap="soft" rows="10" cols="150"
                  placeholder="Введите список позиций (разделение переводом строки)"></textarea>
    </c:when>
    <c:when test="${sectionName == \"EXPERIENCE\" || sectionName == \"EDUCATION\"}">
        <p>Потому что пока не нужно и неизвестно, как редактировать компании, поэтому просто такой текст для новых резюме<p>
    </c:when>
</c:choose>
