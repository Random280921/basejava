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
        if (contactUrl == null) return contactValue;
        return toLink((!"http".equals(contactUrl.substring(0, 4))) ? "http://" + contactUrl : contactUrl, contactValue);
    }

    private static String toLink(String href, String title) {
        return "<a href='" + href + "'>" + title + "</a>";
    }

}
