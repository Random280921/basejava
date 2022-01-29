package ru.javaonline.basejava;

import ru.javaonline.basejava.storage.SqlStorage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Класс  ... description
 *
 * @author KAIvanov
 * created by 06.01.2022 18:08
 * @version 1.0
 */
public class Config {
    //    Для запуска Tomcat нужно указать переменную для запуска -DhomeDir="полный путь к проекту до /config"
//    private static final File PROPS = new File(getHomeDir(), "config/resumes.properties");
    private static final String PROPS = "/resumes.properties";
    private static final Config INSTANCE = new Config();

    private final File storageDir;
    private final SqlStorage sqlStorage;

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = Config.class.getResourceAsStream(PROPS)
//                InputStream is = new FileInputStream(PROPS)
        ) {
            Properties properties = new Properties();
            properties.load(is);
            storageDir = new File(properties.getProperty("storage.dir"));
            sqlStorage = new SqlStorage(properties.getProperty("db.url"),
                    properties.getProperty("db.user"),
                    properties.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS);
        }
    }

    public File getStorageDir() {
        return storageDir;
    }

    public SqlStorage getSqlStorage() {
        return sqlStorage;
    }

//    private static File getHomeDir() {
//        String prop = System.getProperty("homeDir");
//        File homeDir = new File(prop == null ? "." : prop);
//        if (!homeDir.isDirectory()) {
//            throw new IllegalStateException(homeDir + " is not directory");
//        }
//        return homeDir;
//    }
}
