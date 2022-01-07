package ru.javaonline.basejava.sql;

import ru.javaonline.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Класс - утилита для обслуживания SQL
 *
 * @author KAIvanov
 * created by 06.01.2022 21:50
 * @version 1.0
 */
public class SqlHelper {

    @FunctionalInterface
    public interface SqlSupplier<T> {
        T get(PreparedStatement ps) throws SQLException;
    }

    public <T> T sqlExecutor(String sql, ConnectionFactory connectionFactory, String checkNull, SqlSupplier<T> sqlSupplier) {
        if (checkNull == null) throw new NullPointerException();
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return sqlSupplier.get(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
