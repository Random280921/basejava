<%--
  Created by IntelliJ IDEA.
  User: KAIvanov
  Date: 20.01.2022
  Time: 14:00
--%>
<%@ page import="ru.javaonline.basejava.web.ResumeUtil" %>
<%@ page import="ru.javaonline.basejava.model.TextBlockSection" %>
<%@ page import="ru.javaonline.basejava.model.TextListSection" %>
<%@ page import="ru.javaonline.basejava.model.CompanySection" %>
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
    <h2>${resume.fullName} <a href="resume?uuid=${resume.uuid}&action=edit">
        <img src="img/pencil.png"
             alt="edit"></a></h2>
    <h3>Контакты <a href="resume?uuid=${resume.uuid}&action=edit">
        <img src="img/pencil.png"
             alt="edit"></a></h3>
    <p>
        <c:forEach var="contacts" items="${resume.header}">
            <jsp:useBean id="contacts"
                         type="java.util.Map.Entry<ru.javaonline.basejava.model.ContactType,
                             ru.javaonline.basejava.model.Contact>"/>
            <%=ResumeUtil.getWebContact(contacts.getKey(), contacts.getValue())%><br/>
        </c:forEach>
    </p>
    <hr>
    <table>
        <c:forEach var="sections" items="${resume.body}">
            <jsp:useBean id="sections"
                         type="java.util.Map.Entry<ru.javaonline.basejava.model.SectionType,
                                     ru.javaonline.basejava.model.AbstractSection>"/>
            <tr>
                <td><h3>${sections.key.title} <a href="resume?uuid=${resume.uuid}&action=edit">
                    <img src="img/pencil.png"
                         alt="edit"></a></h3></td>
            </tr>
            <tr>
                <td>
                    <c:set var="sectionName" value="${sections.key.name()}"/>
                    <c:choose>
                        <c:when test="${sectionName == \"OBJECTIVE\" || sectionName == \"PERSONAL\"}">
                            <%=((TextBlockSection) sections.getValue()).getBlockPosition()%><br/>
                        </c:when>
                        <c:when test="${sectionName == \"ACHIEVEMENT\" || sectionName == \"QUALIFICATIONS\"}">
                            <ul>
                                <c:forEach var="position"
                                           items="<%=((TextListSection) sections.getValue()).getListPosition()%>">
                                    <li>${position}</li>
                                </c:forEach>
                            </ul>
                        </c:when>
                        <c:when test="${sectionName == \"EXPERIENCE\" || sectionName == \"EDUCATION\"}">
                            <table>
                                <c:forEach var="company"
                                           items="<%=((CompanySection) sections.getValue()).getListPosition()%>">
                                    <jsp:useBean id="company" type="ru.javaonline.basejava.model.Company"/>
                                    <tr>
                                        <td><%=ResumeUtil.getWebContact(company.getCompanyName())%>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <table>
                                                <c:forEach var="exp" items="<%=company.getExperienceList()%>">
                                                    <jsp:useBean id="exp"
                                                                 type="ru.javaonline.basejava.model.Company.Experience"/>
                                                    <tr>
                                                        <td width="150" valign="top">
                                                            <%=exp.getPeriod()%>
                                                        </td>
                                                        <td>
                                                            <b>${exp.positionTitle}</b><br/>
                                                                ${exp.positionText}
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </table>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:when>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
