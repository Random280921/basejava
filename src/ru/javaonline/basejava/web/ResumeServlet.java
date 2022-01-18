package ru.javaonline.basejava.web;

import ru.javaonline.basejava.exception.NotExistStorageException;
import ru.javaonline.basejava.model.ContactType;
import ru.javaonline.basejava.model.Resume;
import ru.javaonline.basejava.storage.Storage;
import ru.javaonline.basejava.util.Config;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private static Storage sqlStorage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
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
                "<th>Имя</th>" +
                "<th>Email</th>" +
                "</tr>");
        for (Resume resume : resumeList) {
            builder.append("<tr><td>")
                    .append("<a href=\"resume?uuid=" + resume.getUuid() + "\">" + resume.getFullName() + "</a>")
                    .append("</td><td>")
                    .append(resume.getHeader().get(ContactType.EMAIL).getValue())
                    .append("</td></tr>");
        }
        builder.append("</table>" +
                "</td></tr>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
