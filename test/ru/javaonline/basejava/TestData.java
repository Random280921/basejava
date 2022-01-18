package ru.javaonline.basejava;

import ru.javaonline.basejava.model.Resume;

import java.util.UUID;

import static ru.javaonline.basejava.ResumeTestData.createResumeTest;

/**
 * Класс - сборка тестовых резюме
 *
 * @author KAIvanov
 * created by 18.01.2022 9:25
 * @version 1.0
 */
public class TestData {
    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();

    public static final Resume RESUME_1;
    public static final Resume RESUME_2;
    public static final Resume RESUME_3;
    public static final Resume RESUME_4;

    static {
        RESUME_1 = createResumeTest(UUID_1, "Пётр Петров");
        RESUME_2 = createResumeTest(UUID_2, "Иван Иванов");
        RESUME_3 = createResumeTest(UUID_3, "Николай Николаев");
        RESUME_4 = createResumeTest(UUID_4, "Армен Арменов");
    }

}
