<%--
  Created by IntelliJ IDEA.
  User: KAIvanov
  Date: 22.01.2022
  Time: 23:52
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<table>
    <c:forEach var="exp" items="${requestScope[param.expList]}">
        <jsp:useBean id="exp" type="ru.javaonline.basejava.model.Company.Experience"/>
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

