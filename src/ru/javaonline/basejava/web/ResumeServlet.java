package ru.javaonline.basejava.web;

import ru.javaonline.basejava.exception.NotExistStorageException;
import ru.javaonline.basejava.model.Resume;
import ru.javaonline.basejava.storage.SqlStorage;
import ru.javaonline.basejava.util.Config;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private static SqlStorage sqlStorage;

    @Override
    public void init() throws ServletException {
        sqlStorage = Config.get().getSqlStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");
        List<Resume> resumeList = new ArrayList<>();
        String info;
        if (uuid != null) {
            try {
                resumeList.add(sqlStorage.get(uuid));
                info = String.format("Запрошено резюме по uuid=%s", uuid);
            } catch (NotExistStorageException e) {
                info = String.format("В хранилище отсутствует резюме по uuid=%s", uuid);
            }
        } else {
            resumeList.addAll(sqlStorage.getAllSorted());
            info = String.format("Запрошены все резюме. Всего %d", resumeList.size());
        }
        StringBuilder htmlPage = new StringBuilder("<html>" +
                "<body>" +
                "<table align=\"left\">" +
                "<tr><td>" +
                info +
                "</td></tr>");
        if (resumeList.size() != 0) buildTableResume(htmlPage, resumeList);
        htmlPage.append(
                "</table>" +
                        "</body>" +
                        "</html>");
        response.getWriter().write(htmlPage.toString());
    }

    private void buildTableResume(StringBuilder builder, List<Resume> resumeList) {
        builder.append("<tr><td>" +
                "<table align=\"left\" border=\"1\">" +
                "<tr>" +
                "<th>uuid</th>" +
                "<th>FullName</th>" +
                "</tr>");
        for (Resume resume : resumeList) {
            builder.append("<tr><td>")
                    .append(resume.getUuid())
                    .append("</td><td>")
                    .append(resume.getFullName())
                    .append("</td></tr>");
        }
        builder.append("</table>" +
                "</td></tr>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
