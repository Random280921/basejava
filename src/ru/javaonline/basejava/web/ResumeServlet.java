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
import java.time.LocalDate;
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
            String typeName = type.name();
            String contactValue = request.getParameter(String.format("%s_value", typeName));
            String contactUrl = request.getParameter(String.format("%s_url", typeName));
            if (contactValue != null && contactValue.trim().length() != 0) {
                resume.addContact(type, new Contact(contactValue.trim(), ResumeUtil.checkUrl(contactUrl.trim(), typeName)));
            } else {
                resume.getHeader().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    editBlockText(resume, request.getParameter(type.name()), type);
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    editListText(resume, request.getParameter(type.name()), type);
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    CompanySection companySection = new CompanySection();
                    String typeName = type.name();
                    String[] companies = request.getParameterValues(String.format("%s_companyName", typeName));
                    String[] companyUrls = request.getParameterValues(String.format("%s_companyUrl", typeName));
                    Company company;
                    if (companies != null)
                        for (int i = 0; i < companies.length; i++) {
                            if (companies[i].trim().length() != 0)
                                company = new Company(companies[i], companyUrls[i]);
                            else break;
                            String partName = String.format("%s%s%d", typeName, companies[i], i);
                            String[] dateFrom = request.getParameterValues(String.format("%sDtB", partName));
                            String[] dateTo = request.getParameterValues(String.format("%sDtE", partName));
                            String[] positionTitle = request.getParameterValues(String.format("%sTitle", partName));
                            String[] positionText = request.getParameterValues(String.format("%sText", partName));

                            if (dateFrom != null && positionTitle != null) {
                                for (int j = 0; j < dateFrom.length; j++) {
                                    LocalDate localDate = ResumeUtil.getWebDate(dateFrom[j]);
                                    if (localDate != null && positionTitle[j] != null)
                                        company.addExperience(localDate, ResumeUtil.getWebDate(dateTo[j]),
                                                positionTitle[j], (positionText != null) ? positionText[j] : null);
                                }
                            }
                            companySection.addListPosition(company);
                        }

                    String partNameNew = String.format("%sNew", typeName);
                    String NameNew = request.getParameter(String.format("%sName", partNameNew));
                    String UrlNew = request.getParameter(String.format("%sUrl", partNameNew));
                    company = new Company(NameNew, UrlNew);
                    String dateFromNew = request.getParameter(String.format("%sDtB", partNameNew));
                    String dateToNew = request.getParameter(String.format("%sDtE", partNameNew));
                    String positionTitleNew = request.getParameter(String.format("%sTitle", partNameNew));
                    String positionTextNew = request.getParameter(String.format("%sText", partNameNew));
                    LocalDate localDateNew = ResumeUtil.getWebDate(dateFromNew);
                    if (localDateNew != null && positionTitleNew != null)
                        company.addExperience(ResumeUtil.getWebDate(dateFromNew),
                                ResumeUtil.getWebDate(dateToNew),
                                positionTitleNew, positionTextNew);

                    companySection.addListPosition(company);

                    if (companySection.getListPosition().size() != 0)
                        resume.addSection(type, companySection);
                    else
                        resume.getBody().remove(type);
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
                x -> new TextBlockSection(paramValue.trim()));
    }

    private void editListText(Resume resume, String paramValue, SectionType type) {
        editSectionText(resume, paramValue, type,
                x -> {
                    List<String> listPosition = new LinkedList<>();
                    for (String s : paramValue.split("\n")) {
                        if (s.trim().length() != 0) listPosition.add(s.trim());
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
