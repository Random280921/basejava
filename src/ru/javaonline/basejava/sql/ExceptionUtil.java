package ru.javaonline.basejava.sql;

import org.postgresql.util.PSQLException;
import ru.javaonline.basejava.exception.ExistStorageException;
import ru.javaonline.basejava.exception.StorageException;

import java.sql.SQLException;

/**
 * Класс обработка специфических ошибок БД
 *
 * @author KAIvanov
 * created by 10.01.2022 18:57
 * @version 1.0
 */
public class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static StorageException convertException(SQLException sqlException) {
        if (sqlException instanceof PSQLException) {
            if ("23505".equals(sqlException.getSQLState())) {
                return new ExistStorageException(sqlException);
            }
        }
        return new StorageException(sqlException);
    }
}
