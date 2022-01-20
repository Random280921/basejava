<%--
  Created by IntelliJ IDEA.
  User: KAIvanov
  Date: 20.01.2022
  Time: 15:09
--%>
<%@ page import="ru.javaonline.basejava.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <table border="1" cellpadding="8" cellspacing="0">
            <tr>
                <th>Тип</th>
                <th>Значение</th>
                <th>URL/NICKNAME</th>
            </tr>
            <c:forEach var="contactType" items="<%=ContactType.values()%>">
                <tr>
                    <td>${contactType.title}</td>
                        <%--                        <dl>--%>
                        <%--                            <dt>${type.title}</dt>--%>
                        <%--                            <dd><input type="text" name="${type.name()}" size=30--%>
                        <%--                                       value="${resume.header.get(type).value}"></dd>--%>
                        <%--                        </dl>--%>
                    <td><input type="text" name="${contactType.name()}_value" size=30 value="${resume.header.get(contactType).value}">
                    </td>
                        <%-- Список для выбора - нужно подпилить под типы (отдельно м и с) и подставлять значение выбора, если есть--%>
                        <%--                    <td><select name="contactValue">--%>
                        <%--                        <option value="s1">skype</option>--%>
                        <%--                        <option value="s2">telegram</option>--%>
                        <%--                        <option value="s3">viber</option>--%>
                        <%--                        <option value="s3">whatsapp</option>--%>
                        <%--                    </select></td>--%>
                    <td><input type="text" name="${contactType.name()}_url" size=50 value="${resume.header.get(contactType).url}"></td>
                </tr>
            </c:forEach>
        </table>
        <h3>Секции:</h3>
        <input type="text" name="section" size=30 value="1"><br/>
        <input type="text" name="section" size=30 value="2"><br/>
        <input type="text" name="section" size=30 value="3"><br/>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
