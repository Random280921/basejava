package ru.javaonline.basejava.web;

import ru.javaonline.basejava.exception.NotExistStorageException;
import ru.javaonline.basejava.model.*;
import ru.javaonline.basejava.storage.Storage;
import ru.javaonline.basejava.util.Config;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

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
                resume = getResume(uuid);
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
        Resume resume = getResume(uuid);
        if (fullName.trim().length() == 0) {
            response.sendRedirect("resume");
            return;
        }
        resume.setFullName(fullName.trim());
        for (ContactType type : ContactType.values()) {
            String contactValue = request.getParameter(String.format("%s_value", type.name()));
            String contactUrl = request.getParameter(String.format("%s_url", type.name()));
            if (contactValue != null && contactValue.trim().length() != 0) {
                resume.addContact(type, new Contact(contactValue.trim(), contactUrl));
            } else {
                resume.getHeader().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            switch (type) {
                case OBJECTIVE:
                    editBlockText(resume, request.getParameter("sectionOBJECTIVE"), type);
                    break;
                case PERSONAL:
                    editBlockText(resume, request.getParameter("sectionPERSONAL"), type);
                    break;
                case ACHIEVEMENT:
                    editListText(resume, request.getParameter("sectionACHIEVEMENT"), type);
                    break;
                case QUALIFICATIONS:
                    editListText(resume, request.getParameter("sectionQUALIFICATIONS"), type);
                    break;
            }
        }
        editResume(resume);
        response.sendRedirect("resume");
    }

    private Resume getResume(String uuid) {
        try {
            return storage.get(uuid);
        } catch (NotExistStorageException e) {
            return new Resume(uuid, "");
        }
    }

    private void editResume(Resume resume) {
        try {
            storage.update(resume);
        } catch (NotExistStorageException e) {
            storage.save(resume);
        }
    }

    private void editBlockText(Resume resume, String paramValue, SectionType type) {
        editSectionText(resume, paramValue, type,
                x -> new TextBlockSection(paramValue));
    }

    private void editListText(Resume resume, String paramValue, SectionType type) {
        editSectionText(resume, paramValue, type,
                x -> {
                    List<String> listPosition = new LinkedList<>();
                    for (String s : paramValue.split("\n")) {
                        if (s.trim().length() != 0) listPosition.add(s);
                    }
                    return new TextListSection(listPosition);
                });
    }

    private void editSectionText(Resume resume,
                                 String paramValue,
                                 SectionType type,
                                 Function<String, AbstractSection> function) {
        if (paramValue.trim().length() != 0) {
            resume.addSection(type, function.apply(paramValue));
        } else {
            resume.getBody().remove(type);
        }
    }
}
