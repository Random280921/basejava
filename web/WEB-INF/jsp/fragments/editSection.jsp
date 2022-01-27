<%--
  Created by IntelliJ IDEA.
  User: KAIvanov
  Date: 23.01.2022
  Time: 18:12
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="ru.javaonline.basejava.model.TextBlockSection" %>
<%@ page import="ru.javaonline.basejava.model.TextListSection" %>
<%@ page import="ru.javaonline.basejava.model.CompanySection" %>
<%@ page import="ru.javaonline.basejava.web.ResumeUtil" %>
<%@ page import="ru.javaonline.basejava.model.Company" %>
<c:set var="sectionName" value="${requestScope[param.sectionName]}"/>
<c:set var="section" value="${requestScope[param.section]}"/>
<jsp:useBean id="sectionName" scope="request" type="java.lang.String"/>
<jsp:useBean id="section" scope="request" type="ru.javaonline.basejava.model.AbstractSection"/>
<c:choose>
    <c:when test="${sectionName == \"OBJECTIVE\" || sectionName == \"PERSONAL\"}">
        <input type="text" name="${sectionName}" size=160
               placeholder="Введите описание"
               value='<%=(section==null) ? "": ((TextBlockSection) section).getBlockPosition()%>'>
    </c:when>
    <c:when test="${sectionName == \"ACHIEVEMENT\" || sectionName == \"QUALIFICATIONS\"}">
        <c:set var="listText"
               value='<%=(section==null) ? "" : String.join(System.lineSeparator(), ((TextListSection) section).getListPosition())%>'/>
        <textarea name="${sectionName}" wrap="soft" rows="10" cols="150"
                  placeholder="Введите список позиций (разделение переводом строки)">${listText}</textarea>
    </c:when>
    <c:when test="${sectionName == \"EXPERIENCE\" || sectionName == \"EDUCATION\"}">
        <c:set var="companyList"
               value='<%=(section==null) ? new CompanySection(Company.EMPTY) : ((CompanySection) section).getListPosition()%>'/>
        <c:forEach var="company" items="${companyList}" varStatus="iterator">
            <jsp:useBean id="company" type="ru.javaonline.basejava.model.Company"/>
            <h4><%=(company.getCompanyName().getValue().length() != 0) ? "Организация" : "Добавить организацию"%>
            </h4>
            <table frame="hsides">
                <tr>
                    <td><input type="text" placeholder="Введите название компании"
                               name="${sectionName}_companyName" size=50
                               value="${company.companyName.value}">
                    </td>
                    <td><input type="text" placeholder="Введите ссылку на сайт, если есть"
                               name="${sectionName}_companyUrl" size=50
                               value="${company.companyName.url}">
                    </td>
                </tr>
                <c:set var="prefix" scope="request" value="${sectionName}${iterator.index}"/>
                <jsp:useBean id="prefix" scope="request" type="java.lang.String"/>
                <c:forEach var="position" items="<%=company.getExperienceList()%>" varStatus="posIter">
                    <jsp:useBean id="position" type="ru.javaonline.basejava.model.Company.Experience"/>
                    <tr>
                        <td>
                            <b><%=(ResumeUtil.getWebDate(position.getDateFrom()).length() != 0) ? "Опыт" : "Добавить опыт"%>
                            </b>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table>
                                <tr>
                                    <td><input type="text" placeholder="Дата c MM/YYYY"
                                               name="${prefix}DtB"
                                               size=20
                                               value="<%=ResumeUtil.getWebDate(position.getDateFrom())%>">
                                    </td>
                                    <td><input type="text" placeholder="Дата до MM/YYYY"
                                               name="${prefix}DtE"
                                               size=20
                                               value="<%=ResumeUtil.getWebDate(position.getDateTo())%>">
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td><input type="text" placeholder="Должность/Курс"
                                   name="${prefix}Title" size=100
                                   value="${fn:escapeXml(position.positionTitle)}">
                        </td>
                    </tr>
                    <c:if test="${sectionName != \"EDUCATION\"}">
                        <tr>
                            <td width="50"></td>
                            <td>
                            <textarea name="${prefix}Text"
                                      wrap="soft" rows="3" cols="100"
                                      placeholder="Введите описание выполняемой работы">${position.positionText}</textarea>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </table>
        </c:forEach>
    </c:when>
</c:choose>
