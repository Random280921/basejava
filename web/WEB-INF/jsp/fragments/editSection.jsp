<%--
  Created by IntelliJ IDEA.
  User: KAIvanov
  Date: 23.01.2022
  Time: 18:12
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="ru.javaonline.basejava.model.TextBlockSection" %>
<%@ page import="ru.javaonline.basejava.model.TextListSection" %>
<%@ page import="ru.javaonline.basejava.model.CompanySection" %>
<c:set var="sectionName" value="${requestScope[param.sectionName]}"/>
<c:set var="section" value="${requestScope[param.section]}"/>
<jsp:useBean id="section" type="ru.javaonline.basejava.model.AbstractSection"/>
<c:choose>
    <c:when test="${sectionName == \"OBJECTIVE\" || sectionName == \"PERSONAL\"}">
        <input type="text" name="sectionBlockText" size=160
               placeholder="Введите описание"
               value="<%=((TextBlockSection) section).getBlockPosition()%>">
    </c:when>
    <c:when test="${sectionName == \"ACHIEVEMENT\" || sectionName == \"QUALIFICATIONS\"}">
        <textarea name="sectionListText" wrap="soft" rows="10" cols="150"
                  placeholder="Введите список позиций (разделение переводом строки)">
            <%=String.join("\n", ((TextListSection) section).getListPosition())%>
        </textarea>
    </c:when>
    <c:when test="${sectionName == \"EXPERIENCE\" || sectionName == \"EDUCATION\"}">
        <c:set var="companyList"
               value="<%=((CompanySection) section).getListPosition()%>"
               scope="request"/>
        <jsp:include page="/WEB-INF/jsp/fragments/company.jsp">
            <jsp:param name="companyList" value="companyList"/>
        </jsp:include>
    </c:when>
</c:choose>
