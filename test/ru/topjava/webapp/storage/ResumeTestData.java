package ru.topjava.webapp.storage;

import ru.topjava.webapp.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resumeModelTest = new Resume("Григорий Кислин");
        // Наполняем резюме
        writeResumeTest(resumeModelTest);
        // Выводим в консоль резюме
        printResumeTest(resumeModelTest);
    }

    public static void printResumeTest(Resume resumeModelTest) {
        StringBuilder printConsole = new StringBuilder(resumeModelTest.getFullName()).append("\n");
        int key;
        Contact contact;
        for (Map.Entry<Integer, Contact> element :
                resumeModelTest.getHeader().entrySet()) {
            key = element.getKey();
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
        for (SectionType element :
                SectionType.values()) {
            key = element.ordinal();
            printConsole.append("\n").append(element.getTitle()).append("\n");
            if (key < 2)
                printConsole.append("   ").append(resumeModelTest.getBodyText(key)).append("\n");
            if (key == 2 || key == 3) {
                listString = resumeModelTest.getListText(key);
                for (String text : listString
                ) {
                    printConsole.append("   * ").append(text).append("\n");
                }
            }
            if (key > 3) {
                listCompany = resumeModelTest.getListCompany(key);
                for (Company company : listCompany
                ) {
                    printConsole.append("\n   ").append(company.toString());
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
        resumeModelTest.addBodyText(SectionType.OBJECTIVE.ordinal(), "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resumeModelTest.addBodyText(SectionType.PERSONAL.ordinal(), "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");

        // Заполняем Достижения и Квалификация
        resumeModelTest.addListText(SectionType.ACHIEVEMENT.ordinal(), "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        resumeModelTest.addListText(SectionType.ACHIEVEMENT.ordinal(), "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        resumeModelTest.addListText(SectionType.ACHIEVEMENT.ordinal(), "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        resumeModelTest.addListText(SectionType.ACHIEVEMENT.ordinal(), "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        resumeModelTest.addListText(SectionType.ACHIEVEMENT.ordinal(), "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        resumeModelTest.addListText(SectionType.ACHIEVEMENT.ordinal(), "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");

        resumeModelTest.addListText(SectionType.QUALIFICATIONS.ordinal(), "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        resumeModelTest.addListText(SectionType.QUALIFICATIONS.ordinal(), "Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        resumeModelTest.addListText(SectionType.QUALIFICATIONS.ordinal(), "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        resumeModelTest.addListText(SectionType.QUALIFICATIONS.ordinal(), "MySQL, SQLite, MS SQL, HSQLDB");
        resumeModelTest.addListText(SectionType.QUALIFICATIONS.ordinal(), "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        resumeModelTest.addListText(SectionType.QUALIFICATIONS.ordinal(), "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        resumeModelTest.addListText(SectionType.QUALIFICATIONS.ordinal(), "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        resumeModelTest.addListText(SectionType.QUALIFICATIONS.ordinal(), "Python: Django.");
        resumeModelTest.addListText(SectionType.QUALIFICATIONS.ordinal(), "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        resumeModelTest.addListText(SectionType.QUALIFICATIONS.ordinal(), "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        resumeModelTest.addListText(SectionType.QUALIFICATIONS.ordinal(), "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        resumeModelTest.addListText(SectionType.QUALIFICATIONS.ordinal(), "Инструменты: Maven + plugin development, Gradle, настройка Ngnix,");
        resumeModelTest.addListText(SectionType.QUALIFICATIONS.ordinal(), "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.");
        resumeModelTest.addListText(SectionType.QUALIFICATIONS.ordinal(), "Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
        resumeModelTest.addListText(SectionType.QUALIFICATIONS.ordinal(), "Родной русский, английский \"upper intermediate\"");

        // Заполняем Опыт работы и Образование
        // Опыт работы
        Company org = new Company("RIT Center");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        org.addExperience(LocalDate.parse("01.04.2012", format), LocalDate.parse("01.10.2014", format),
                "Java архитектор",
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"
        );
        resumeModelTest.addListCompany(SectionType.EXPERIENCE.ordinal(), org);

        org = new Company("Java Online Projects", "javaops.ru");
        org.addExperience(LocalDate.parse("01.10.2013", format), null,
                "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок.");
        resumeModelTest.addListCompany(SectionType.EXPERIENCE.ordinal(), org);

        org = new Company("Wrike", "www.wrike.com");
        org.addExperience(LocalDate.parse("01.10.2014", format), LocalDate.parse("01.01.2016", format),
                "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."
        );
        resumeModelTest.addListCompany(SectionType.EXPERIENCE.ordinal(), org);

        org = new Company("Luxoft (Deutsche Bank)", "www.luxoft.ru");
        org.addExperience(LocalDate.parse("01.12.2010", format), LocalDate.parse("01.04.2012", format),
                "Ведущий программист",
                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."
        );
        resumeModelTest.addListCompany(SectionType.EXPERIENCE.ordinal(), org);

        org = new Company("Yota", "www.yota.ru");
        org.addExperience(LocalDate.parse("01.06.2008", format), LocalDate.parse("01.12.2010", format),
                "Ведущий специалист",
                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"
        );
        resumeModelTest.addListCompany(SectionType.EXPERIENCE.ordinal(), org);

        org = new Company("Enkata", "enkata.com");
        org.addExperience(LocalDate.parse("01.03.2007", format), LocalDate.parse("01.06.2008", format),
                "Разработчик ПО",
                "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).");
        resumeModelTest.addListCompany(SectionType.EXPERIENCE.ordinal(), org);

        org = new Company("Siemens AG", "www.siemens.com/ru/ru/home.html");
        org.addExperience(LocalDate.parse("01.01.2005", format), LocalDate.parse("01.02.2007", format),
                "Разработчик ПО",
                "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).");
        resumeModelTest.addListCompany(SectionType.EXPERIENCE.ordinal(), org);

        org = new Company("Siemens AG", "www.siemens.com/ru/ru/home.html");
        org.addExperience(LocalDate.parse("01.01.2005", format), LocalDate.parse("01.02.2007", format),
                "Разработчик ПО",
                "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).");
        resumeModelTest.addListCompany(SectionType.EXPERIENCE.ordinal(), org);

        org = new Company("Alcatel", "www.alcatel.ru");
        org.addExperience(LocalDate.parse("01.09.1997", format), LocalDate.parse("01.01.2005", format),
                "Инженер по аппаратному и программному тестированию",
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).");
        resumeModelTest.addListCompany(SectionType.EXPERIENCE.ordinal(), org);

        // Образование
        org = new Company("Coursera", "www.coursera.org/course/progfun");
        org.addExperience(LocalDate.parse("01.03.2013", format), LocalDate.parse("01.05.2013", format),
                "\"Functional Programming Principles in Scala\" by Martin Odersky"
        );
        resumeModelTest.addListCompany(SectionType.EDUCATION.ordinal(), org);

        org = new Company("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366");
        org.addExperience(LocalDate.parse("01.03.2011", format), LocalDate.parse("01.04.2011", format),
                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\""
        );
        resumeModelTest.addListCompany(SectionType.EDUCATION.ordinal(), org);

        org = new Company("Siemens AG", "www.siemens.ru");
        org.addExperience(LocalDate.parse("01.01.2005", format), LocalDate.parse("01.04.2005", format),
                "3 месяца обучения мобильным IN сетям (Берлин)"
        );
        resumeModelTest.addListCompany(SectionType.EDUCATION.ordinal(), org);

        org = new Company("Alcatel", "www.alcatel.ru");
        org.addExperience(LocalDate.parse("01.09.1997", format), LocalDate.parse("01.03.1998", format),
                "6 месяцев обучения цифровым телефонным сетям (Москва)"
        );
        resumeModelTest.addListCompany(SectionType.EDUCATION.ordinal(), org);

        org = new Company("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                "www.ifmo.ru");
        org.addExperience(LocalDate.parse("01.09.1993", format), LocalDate.parse("01.07.1996", format),
                "Аспирантура (программист С, С++)"
        );
        org.addExperience(LocalDate.parse("01.09.1987", format), LocalDate.parse("01.07.1993", format),
                "Инженер (программист Fortran, C)"
        );
        resumeModelTest.addListCompany(SectionType.EDUCATION.ordinal(), org);

        org = new Company("Заочная физико-техническая школа при МФТИ",
                "www.school.mipt.ru/");
        org.addExperience(LocalDate.parse("01.09.1984", format), LocalDate.parse("01.06.1987", format),
                "Аспирантура (программист С, С++)"
        );
        resumeModelTest.addListCompany(SectionType.EDUCATION.ordinal(), org);
    }
}