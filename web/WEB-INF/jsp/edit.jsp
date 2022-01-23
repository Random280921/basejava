<%--
  Created by IntelliJ IDEA.
  User: KAIvanov
  Date: 20.01.2022
  Time: 15:09
--%>
<%@ page import="ru.javaonline.basejava.web.ResumeUtil" %>
<%@ page import="ru.javaonline.basejava.model.*" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javaonline.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <table>
            <tr>
                <th>Тип</th>
                <th>Значение/Название</th>
                <th>Url/Nickname</th>
            </tr>
            <c:forEach var="contactType" items="<%=ContactType.values()%>">
                <jsp:useBean id="contactType" type="ru.javaonline.basejava.model.ContactType"/>
                <tr>
                    <td>${contactType.title}</td>
                    <td><input type="text" placeholder="<%=ResumeUtil.getExampleContact(contactType)[0]%>"
                               name="${contactType.name()}_value" size=30
                               value="${resume.header.get(contactType).value}">
                    </td>
                    <td><input type="text" placeholder="<%=ResumeUtil.getExampleContact(contactType)[1]%>"
                               name="${contactType.name()}_url" size=50
                               value="${resume.header.get(contactType).url}"></td>
                </tr>
            </c:forEach>
        </table>
        <hr>
        <h3>Разделы резюме:</h3>
        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <jsp:useBean id="sectionType" type="ru.javaonline.basejava.model.SectionType"/>
            <c:set var="sectionName" value="${sectionType.name()}"/>
            <c:set var="section" value="<%=(resume.getBody().get(sectionType))%>"/>
            <jsp:useBean id="section" type="ru.javaonline.basejava.model.AbstractSection"/>
            <c:choose>
                <c:when test="${sectionName == \"OBJECTIVE\" || sectionName == \"PERSONAL\"}">
                    <dl>
                        <dt><b>${sectionType.title}:</b></dt>
                        <dd><input type="text" name="sectionBlockText" size=130
                                   value="<%=((TextBlockSection) section).getBlockPosition()%>">
                        </dd>
                    </dl>
                </c:when>
                <c:when test="${sectionName == \"ACHIEVEMENT\" || sectionName == \"QUALIFICATIONS\"}">
                    <dl>
                        <dt><b>${sectionType.title}:</b></dt>
                        <dd>
                            <textarea name="sectionListText" wrap="soft" rows="10" cols="150">
                                <%=String.join("\n", ((TextListSection) section).getListPosition())%>
                            </textarea>
                        </dd>
                    </dl>
                </c:when>
                <c:when test="${sectionName == \"EXPERIENCE\" || sectionName == \"EDUCATION\"}">
                    <c:set var="companyList"
                           value="<%=((CompanySection) section).getListPosition()%>"
                           scope="request"/>
                    <jsp:include page="fragments/company.jsp">
                        <jsp:param name="companyList" value="companyList"/>
                    </jsp:include>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
