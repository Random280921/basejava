package ru.javaonline.basejava.web;

import ru.javaonline.basejava.exception.NotExistStorageException;
import ru.javaonline.basejava.model.Contact;
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

public class ResumeServlet extends HttpServlet {
    private static Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getSqlStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                try {
                    resume = storage.get(uuid);
                } catch (NotExistStorageException e) {
                    resume = new Resume(uuid, "");
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume resume = storage.get(uuid);
        if (fullName != null && fullName.length() != 0) resume.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String contactValue = request.getParameter(String.format("%s_value", type.name()));
            String contactUrl = request.getParameter(String.format("%s_url", type.name()));
            if (contactValue != null && contactValue.trim().length() != 0) {
                resume.addContact(type, new Contact(contactValue.trim(), contactUrl));
            } else {
                resume.getHeader().remove(type);
            }
        }
        storage.update(resume);
        response.sendRedirect("resume");
    }
}
