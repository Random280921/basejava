package ru.javaonline.basejava.web;

import ru.javaonline.basejava.model.Contact;
import ru.javaonline.basejava.model.ContactType;

/**
 * Класс - сервисный класс для web
 *
 * @author KAIvanov
 * created by 20.01.2022 22:02
 * @version 1.0
 */
public class ResumeUtil {
    public static String getWebContact(ContactType contactType, Contact contact) {
        String webContact = null;
        String typeName = contactType.name();
        String contactValue = contact.getValue();
        String contactUrl = contact.getUrl();
        if (typeName.contains("PHONE")) webContact = contactValue;
        if (typeName.contains("MESSENGER"))
            webContact = toLink(String.format("%s:%s", contactValue, contactUrl), contactValue);
        if ("EMAIL".equals(typeName))
            webContact = toLink(String.format("mailto:%s", contactValue), contactValue);
        if (webContact == null)
            webContact = getWebContact(contact);
        return webContact;
    }

    public static String getWebContact(Contact contact) {
        String contactValue = contact.getValue();
        String contactUrl = contact.getUrl();
        if (contactUrl == null) return "<b>" + contactValue + "</b>";
        return toLink((!"http".equals(contactUrl.substring(0, 4))) ? "http://" + contactUrl : contactUrl, contactValue);
    }

    public static String[] getExampleContact(ContactType contactType) {
        String[] exampleContact=new String[2];
        String typeName = contactType.name();
        if (typeName.contains("PHONE")) {
            exampleContact[0]=("пример: +7(111) 111-1111");
            exampleContact[1]=("url не заполняется");
        }
        if (typeName.contains("MESSENGER")) {
            exampleContact[0]=("пример: skype, telegram и т.п.");
            exampleContact[1]=("пример: guest.guest, @guest и т.п.");
        }
        if ("EMAIL".equals(typeName)) {
            exampleContact[0]=("пример: mail@mail.ru");
            exampleContact[1]=("url не заполняется");
        }
        if ("SITE".equals(typeName)) {
            exampleContact[0]=("пример: Домашняя страница и т.п.");
            exampleContact[1]=("пример: www.homepage.ru");
        }
        if (exampleContact[0] == null) {
            exampleContact[0]=("пример: LinkedIn, GitHub и т.п.");
            exampleContact[1]=("пример: https://www.linkedin.com/in/guest");
        }
        return exampleContact;
    }

    private static String toLink(String href, String title) {
        return "<a href='" + href + "'>" + title + "</a>";
    }

}
