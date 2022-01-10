package ru.javaonline.basejava.sql;

import ru.javaonline.basejava.exception.ExistStorageException;
import ru.javaonline.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Класс - утилита для обслуживания SQL
 *
 * @author KAIvanov
 * created by 06.01.2022 21:50
 * @version 1.0
 */
public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @FunctionalInterface
    public interface SqlSupplier<T> {
        T get(PreparedStatement statement) throws SQLException;
    }

    public <T> T sqlExecutor(String sql, Logger logger, SqlSupplier<T> sqlSupplier) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            return sqlSupplier.get(statement);
        } catch (SQLException sqlException) {
            logger.severe(sqlException.getMessage());
            throw ("23505".equals(sqlException.getSQLState()))
                    ? new ExistStorageException(sqlException)
                    : new StorageException(sqlException);
        }
    }
}
