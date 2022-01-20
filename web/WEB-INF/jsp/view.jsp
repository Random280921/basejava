<%--
  Created by IntelliJ IDEA.
  User: KAIvanov
  Date: 20.01.2022
  Time: 14:00
--%>
<%@ page import="ru.javaonline.basejava.web.ResumeUtil" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"
                                                                                      alt="edit"></a></h2>
    <p>
        <c:forEach var="contacts" items="${resume.header}">
            ${ResumeUtil.getListWebContacts(contacts.key,contacts.value)}<br/>
        </c:forEach>
    <p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
