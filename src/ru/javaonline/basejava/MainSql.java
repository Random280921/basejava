package ru.javaonline.basejava;

import ru.javaonline.basejava.model.Resume;
import ru.javaonline.basejava.storage.SqlStorage;
import ru.javaonline.basejava.util.Config;

import java.util.List;
import java.util.UUID;

/**
 * Класс для ручных проверок SQl
 *
 * @author KAIvanov
 * created by 07.01.2022 17:53
 * @version 1.0
 */
public class MainSql {
    static SqlStorage sqlStorage = Config.get().getSqlStorage();

    public static void main(String[] args) {
        Resume resume = ResumeTestData.createResumeTest(UUID.randomUUID().toString(), "Пётр Петров");
        sqlStorage.clear();
        sqlStorage.save(resume);
        Resume resume1 = sqlStorage.get(resume.getUuid());
        System.out.println(resume.equals(resume1));
        ResumeTestData.printResumeTest(resume);
        System.out.println(sqlStorage.size());
        ResumeTestData.printResumeTest(resume1);
        System.out.println("-------------");
        List<Resume> resumeList = sqlStorage.getAllSorted();
        for (Resume r : resumeList) {
            ResumeTestData.printResumeTest(r);
        }
//
//        for (Map.Entry<String,String> e:
//                Config.get().getSqlCommands().entrySet()) {
//            System.out.println(e.getKey());
//            System.out.println(e.getValue());
//        }
//        System.out.println(Config.get().getSqlCommand("clear"));
//        sqlStorage.clear();
    }
}
