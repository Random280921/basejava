package ru.javaonline.basejava;

import ru.javaonline.basejava.storage.SqlStorage;
import ru.javaonline.basejava.util.Config;

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
//        Resume resume = new Resume("uuid0", "Пётр Петров");
//        sqlStorage.save(resume);
//        System.out.println(sqlStorage.size());
//        for (Map.Entry<String,String> e:
//                Config.get().getSqlCommands().entrySet()) {
//            System.out.println(e.getKey());
//            System.out.println(e.getValue());
//        }
        System.out.println(Config.get().getSqlCommand("clear"));
        sqlStorage.clear();
    }
}
