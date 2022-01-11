package ru.javaonline.basejava.util;

import ru.javaonline.basejava.storage.SqlStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Класс  ... description
 *
 * @author KAIvanov
 * created by 06.01.2022 18:08
 * @version 1.0
 */
public class Config {
    private static final File PROPS = new File("config/resumes.properties");
    private static final File SQL_COMMAND_DIR = new File("config/sql_command");
    private static final Config INSTANCE = new Config();

    private final File storageDir;
    private final SqlStorage sqlStorage;
    private final Map<String, String> sqlCommands = new HashMap<>();

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            Properties properties = new Properties();
            properties.load(is);
            storageDir = new File(properties.getProperty("storage.dir"));
            sqlStorage = new SqlStorage(properties.getProperty("db.url"),
                    properties.getProperty("db.user"),
                    properties.getProperty("db.password"));
            File[] listFiles = SQL_COMMAND_DIR.listFiles();
            if (listFiles != null) {
                for (File file : listFiles) {
                    sqlCommands.put(file.getName().replace(".sql", ""),
                            new String(Files.readAllBytes(file.toPath())));
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }

    public SqlStorage getSqlStorage() {
        return sqlStorage;
    }

    public String getSqlCommand(String command) {
        return sqlCommands.get(command);
    }
}
