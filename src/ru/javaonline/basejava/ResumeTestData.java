package ru.javaonline.basejava;

import ru.javaonline.basejava.model.*;

import java.util.List;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        // Создаём, наполняем и выводим в консоль резюме
        printResumeTest(createResumeTest("testUuid", "Григорий Кислин"));
    }

    public static Resume createResumeTest(String uuid, String fullname) {
        Resume testResume = new Resume(uuid, fullname);
        writeResumeTest(testResume);
        return testResume;
    }

    public static void printResumeTest(Resume resumeModelTest) {
        StringBuilder printConsole = new StringBuilder("Resume.uuid= ").append(resumeModelTest.getUuid()).append("\n")
                .append(resumeModelTest.getFullName()).append("\n");
        int key;
        Contact contact;
        for (Map.Entry<ContactType, Contact> element :
                resumeModelTest.getHeader().entrySet()) {
            key = element.getKey().ordinal();
            contact = element.getValue();
            if (key < 3) printConsole.append("Тел.: ").append(contact.getValue()).append("\n");
            if ((key > 2 && key < 6) || (key == 10))
                printConsole.append(contact.getValue()).append(": ").append(contact.getUrl()).append("\n");
            if (key > 6 && key < 10)
                printConsole.append("Профиль ").append(contact.getValue()).append(": ").append(contact.getUrl()).append("\n");
            if (key == 6) printConsole.append(ContactType.EMAIL.getTitle()).append(contact.getValue()).append("\n");
        }
        List<String> listString;
        List<Company> listCompany;
        for (Map.Entry<SectionType, AbstractSection> element :
                resumeModelTest.getBody().entrySet()) {
            printConsole.append("\n").append(element.getKey().getTitle()).append("\n");
            switch (element.getKey()) {
                case OBJECTIVE:
                case PERSONAL:
                    printConsole.append("   ").append(((TextBlockSection) element.getValue()).getBlockPosition()).append("\n");
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    listString = ((TextListSection) element.getValue()).getListPosition();
                    for (String text : listString) {
                        printConsole.append("   * ").append(text).append("\n");
                    }
                    break;
                case EXPERIENCE:
                case EDUCATION: {
                    listCompany = ((CompanySection) element.getValue()).getListPosition();
                    for (Company company : listCompany) {
                        printConsole.append("\n   ").append(company.toString());
                    }
                    break;
                }
            }
        }
        System.out.println(printConsole);
    }

    public static void writeResumeTest(Resume resumeModelTest) {
        // Заполняем контакты
        resumeModelTest.addContact(ContactType.PHONE_DEFAULT, new Contact("+7(921) 855-0482"));
        resumeModelTest.addContact(ContactType.MESSENGER_DEFAULT, new Contact("Skype", "grigory.kislin"));
        resumeModelTest.addContact(ContactType.EMAIL, new Contact("gkislin@yandex.ru"));
        resumeModelTest.addContact(ContactType.NETWORK_DEFAULT, new Contact("LinkedIn", "https://www.linkedin.com/in/gkislin"));
        resumeModelTest.addContact(ContactType.NETWORK_ADD1, new Contact("GitHub", "https://github.com/gkislin"));
        resumeModelTest.addContact(ContactType.NETWORK_ADD2, new Contact("Stackoverflow", "https://stackoverflow.com/users/548473"));
        resumeModelTest.addContact(ContactType.SITE, new Contact("Домашняя страница", "gkislin.ru"));

        // Заполняем Позиция и Личные качества

       resumeModelTest.addSection(SectionType.OBJECTIVE, new TextBlockSection());
        ((TextBlockSection) resumeModelTest.getBody().get(SectionType.OBJECTIVE)).addBlockPosition("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resumeModelTest.addSection(SectionType.PERSONAL, new TextBlockSection());
        ((TextBlockSection) resumeModelTest.getBody().get(SectionType.PERSONAL)).addBlockPosition("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");

        // Заполняем Достижения и Квалификация
        resumeModelTest.addSection(SectionType.ACHIEVEMENT, new TextListSection());
        ((TextListSection) resumeModelTest.getBody().get(SectionType.ACHIEVEMENT)).addListPosition("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        ((TextListSection) resumeModelTest.getBody().get(SectionType.ACHIEVEMENT)).addListPosition("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        ((TextListSection) resumeModelTest.getBody().get(SectionType.ACHIEVEMENT)).addListPosition("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        ((TextListSection) resumeModelTest.getBody().get(SectionType.ACHIEVEMENT)).addListPosition("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        ((TextListSection) resumeModelTest.getBody().get(SectionType.ACHIEVEMENT)).addListPosition("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        ((TextListSection) resumeModelTest.getBody().get(SectionType.ACHIEVEMENT)).addListPosition("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");

        resumeModelTest.addSection(SectionType.QUALIFICATIONS, new TextListSection());
        ((TextListSection) resumeModelTest.getBody().get(SectionType.QUALIFICATIONS)).addListPosition("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        ((TextListSection) resumeModelTest.getBody().get(SectionType.QUALIFICATIONS)).addListPosition("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        ((TextListSection) resumeModelTest.getBody().get(SectionType.QUALIFICATIONS)).addListPosition("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        ((TextListSection) resumeModelTest.getBody().get(SectionType.QUALIFICATIONS)).addListPosition("MySQL, SQLite, MS SQL, HSQLDB");
        ((TextListSection) resumeModelTest.getBody().get(SectionType.QUALIFICATIONS)).addListPosition("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        ((TextListSection) resumeModelTest.getBody().get(SectionType.QUALIFICATIONS)).addListPosition("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        ((TextListSection) resumeModelTest.getBody().get(SectionType.QUALIFICATIONS)).addListPosition("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        ((TextListSection) resumeModelTest.getBody().get(SectionType.QUALIFICATIONS)).addListPosition("Python: Django.");
        ((TextListSection) resumeModelTest.getBody().get(SectionType.QUALIFICATIONS)).addListPosition("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        ((TextListSection) resumeModelTest.getBody().get(SectionType.QUALIFICATIONS)).addListPosition("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        ((TextListSection) resumeModelTest.getBody().get(SectionType.QUALIFICATIONS)).addListPosition("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        ((TextListSection) resumeModelTest.getBody().get(SectionType.QUALIFICATIONS)).addListPosition("Инструменты: Maven + plugin development, Gradle, настройка Ngnix,");
        ((TextListSection) resumeModelTest.getBody().get(SectionType.QUALIFICATIONS)).addListPosition("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.");
        ((TextListSection) resumeModelTest.getBody().get(SectionType.QUALIFICATIONS)).addListPosition("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
        ((TextListSection) resumeModelTest.getBody().get(SectionType.QUALIFICATIONS)).addListPosition("Родной русский, английский \"upper intermediate\"");
/*
        // Заполняем Опыт работы и Образование
        // Опыт работы
        resumeModelTest.addSection(SectionType.EXPERIENCE, new CompanySection());
        Company org = new Company("RIT Center");
//        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        org.addExperience(DateUtil.of(2012, Month.APRIL), DateUtil.of(2014, Month.OCTOBER),
                "Java архитектор",
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"
        );
        ((CompanySection) resumeModelTest.getBody().get(SectionType.EXPERIENCE)).addListPosition(org);

        org = new Company("Java Online Projects", "javaops.ru");
        org.addExperience(DateUtil.of(2013, Month.OCTOBER),
                "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок.");
        ((CompanySection) resumeModelTest.getBody().get(SectionType.EXPERIENCE)).addListPosition(org);

        org = new Company("Wrike", "www.wrike.com");
        org.addExperience(DateUtil.of(2014, Month.OCTOBER), DateUtil.of(2016, Month.JANUARY),
                "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."
        );
        ((CompanySection) resumeModelTest.getBody().get(SectionType.EXPERIENCE)).addListPosition(org);

        org = new Company("Luxoft (Deutsche Bank)", "www.luxoft.ru");
        org.addExperience(DateUtil.of(2010, Month.DECEMBER), DateUtil.of(2012, Month.APRIL),
                "Ведущий программист",
                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."
        );
        ((CompanySection) resumeModelTest.getBody().get(SectionType.EXPERIENCE)).addListPosition(org);

        org = new Company("Yota", "www.yota.ru");
        org.addExperience(DateUtil.of(2008, Month.JUNE), DateUtil.of(2010, Month.DECEMBER),
                "Ведущий специалист",
                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"
        );
        ((CompanySection) resumeModelTest.getBody().get(SectionType.EXPERIENCE)).addListPosition(org);

        org = new Company("Enkata", "enkata.com");
        org.addExperience(DateUtil.of(2007, Month.MARCH), DateUtil.of(2008, Month.JUNE),
                "Разработчик ПО",
                "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).");
        ((CompanySection) resumeModelTest.getBody().get(SectionType.EXPERIENCE)).addListPosition(org);

        org = new Company("Siemens AG", "www.siemens.com/ru/ru/home.html");
        org.addExperience(DateUtil.of(2005, Month.JANUARY), DateUtil.of(2007, Month.MARCH),
                "Разработчик ПО",
                "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).");
        ((CompanySection) resumeModelTest.getBody().get(SectionType.EXPERIENCE)).addListPosition(org);

        org = new Company("Alcatel", "www.alcatel.ru");
        org.addExperience(DateUtil.of(1997, Month.SEPTEMBER), DateUtil.of(2005, Month.JANUARY),
                "Инженер по аппаратному и программному тестированию",
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).");
        ((CompanySection) resumeModelTest.getBody().get(SectionType.EXPERIENCE)).addListPosition(org);

        // Образование
        resumeModelTest.addSection(SectionType.EDUCATION, new CompanySection());
        org = new Company("Coursera", "www.coursera.org/course/progfun");
        org.addExperience(DateUtil.of(2013, Month.MARCH), DateUtil.of(2013, Month.MAY),
                "\"Functional Programming Principles in Scala\" by Martin Odersky"
        );
        ((CompanySection) resumeModelTest.getBody().get(SectionType.EDUCATION)).addListPosition(org);

        org = new Company("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366");
        org.addExperience(DateUtil.of(2011, Month.MARCH), DateUtil.of(2011, Month.APRIL),
                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\""
        );
        ((CompanySection) resumeModelTest.getBody().get(SectionType.EDUCATION)).addListPosition(org);

        org = new Company("Siemens AG", "www.siemens.ru");
        org.addExperience(DateUtil.of(2005, Month.JANUARY), DateUtil.of(2005, Month.APRIL),
                "3 месяца обучения мобильным IN сетям (Берлин)"
        );
        ((CompanySection) resumeModelTest.getBody().get(SectionType.EDUCATION)).addListPosition(org);

        org = new Company("Alcatel", "www.alcatel.ru");
        org.addExperience(DateUtil.of(1997, Month.SEPTEMBER), DateUtil.of(1998, Month.MARCH),
                "6 месяцев обучения цифровым телефонным сетям (Москва)"
        );
        ((CompanySection) resumeModelTest.getBody().get(SectionType.EDUCATION)).addListPosition(org);

        org = new Company("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                "www.ifmo.ru");
        org.addExperience(DateUtil.of(1987, Month.SEPTEMBER), DateUtil.of(1993, Month.JULY),
                "Инженер (программист Fortran, C)"
        );
        org.addExperience(DateUtil.of(1993, Month.SEPTEMBER), DateUtil.of(1996, Month.JULY),
                "Аспирантура (программист С, С++)"
        );
        ((CompanySection) resumeModelTest.getBody().get(SectionType.EDUCATION)).addListPosition(org);

        org = new Company("Заочная физико-техническая школа при МФТИ",
                "www.school.mipt.ru/");
        org.addExperience(DateUtil.of(1984, Month.SEPTEMBER), DateUtil.of(1987, Month.JUNE),
                "Аспирантура (программист С, С++)"
        );
        ((CompanySection) resumeModelTest.getBody().get(SectionType.EDUCATION)).addListPosition(org);
        */
    }
}