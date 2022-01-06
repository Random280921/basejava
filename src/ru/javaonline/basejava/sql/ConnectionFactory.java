package ru.javaonline.basejava.sql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Интерфейс  - фабрика соединений с БД
 *
 * @author KAIvanov
 * created by 06.01.2022 21:35
 * @version 1.0
 */
public interface ConnectionFactory {
    Connection getConnection() throws SQLException;
}
