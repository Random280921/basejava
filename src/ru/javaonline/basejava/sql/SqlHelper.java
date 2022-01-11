package ru.javaonline.basejava.sql;

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
    public interface SqlExecutor<T> {
        T execute(PreparedStatement statement) throws SQLException;
    }

    @FunctionalInterface
    public interface SqlTransaction<T> {
        T execute(Connection conn) throws SQLException;
    }

    public void execute(String sql, Logger logger) {
        execute(sql, logger, PreparedStatement::execute);
    }

    public <T> T execute(String sql, Logger logger, SqlExecutor<T> sqlExecutor) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            return sqlExecutor.execute(statement);
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw ExceptionUtil.convertException(e);
        }
    }

    public <T> T transactionalExecute(Logger logger, SqlTransaction<T> sqlTransaction) {
        try (Connection connection = connectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                T result = sqlTransaction.execute(connection);
                connection.commit();
                return result;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw ExceptionUtil.convertException(e);
        }
    }
}
