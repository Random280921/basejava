package ru.javaonline.basejava;

import org.junit.Assert;
import org.junit.Test;
import ru.javaonline.basejava.model.AbstractSection;
import ru.javaonline.basejava.model.Resume;
import ru.javaonline.basejava.model.TextBlockSection;
import ru.javaonline.basejava.util.JsonParser;

import static ru.javaonline.basejava.TestData.RESUME_1;

/**
 * Класс - сверка по ДЗ 15
 *
 * @author KAIvanov
 * created by 18.01.2022 11:18
 * @version 1.0
 */
public class JsonParserTest {
    @Test
    public void testResume() {
        String json = JsonParser.write(RESUME_1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(RESUME_1, resume);
    }

    @Test
    public void write() {
        AbstractSection section1 = new TextBlockSection("Objective1");
        String json = JsonParser.write(section1, AbstractSection.class);
        System.out.println(json);
        AbstractSection section2 = JsonParser.read(json, AbstractSection.class);
        Assert.assertEquals(section1, section2);
    }
}
