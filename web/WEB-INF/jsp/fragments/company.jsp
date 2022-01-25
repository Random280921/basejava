<%--
  Created by IntelliJ IDEA.
  User: KAIvanov
  Date: 23.01.2022
  Time: 0:53
--%>
<%@ page import="ru.javaonline.basejava.web.ResumeUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<table>
    <c:forEach var="company" items="${requestScope[param.companyList]}">
        <jsp:useBean id="company" type="ru.javaonline.basejava.model.Company"/>
        <tr>
            <td><%=ResumeUtil.getWebContact(company.getCompanyName())%>
            </td>
        </tr>
        <c:if test="${company.experienceList.size() != 0}">
            <tr>
                <td>
                    <c:set var="expList" value="<%=company.getExperienceList()%>" scope="request"/>
                    <jsp:include page="/WEB-INF/jsp/fragments/experience.jsp">
                        <jsp:param name="expList" value="expList"/>
                    </jsp:include>
                </td>
            </tr>
        </c:if>
    </c:forEach>
</table>
