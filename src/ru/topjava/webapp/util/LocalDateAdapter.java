package ru.topjava.webapp.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

import static ru.topjava.webapp.util.DateUtil.NOW;

/**
 * Класс  LocalDateAdapter -- вспомогательный класс для конертации дат в/из xml
 *
 * @author KAIvanov
 * created by 13.12.2021 21:36
 * @version 1.0
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    @Override
    public LocalDate unmarshal(String str) {
        return ("Сейчас".equals(str)) ? NOW : LocalDate.parse(str);
    }

    @Override
    public String marshal(LocalDate ld) {
        return (ld.equals(NOW)) ? "Сейчас" : ld.toString();
    }
}