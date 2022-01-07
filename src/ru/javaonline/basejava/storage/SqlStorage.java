package ru.javaonline.basejava.storage;

import ru.javaonline.basejava.exception.ExistStorageException;
import ru.javaonline.basejava.exception.NotExistStorageException;
import ru.javaonline.basejava.model.Resume;
import ru.javaonline.basejava.sql.ConnectionFactory;
import ru.javaonline.basejava.sql.SqlHelper;
import ru.javaonline.basejava.util.Config;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс - реализация хранилища в БД
 *
 * @author KAIvanov
 * created by 06.01.2022 21:39
 * @version 1.0
 */
public class SqlStorage implements Storage {
    private final ConnectionFactory connectionFactory;
    private final SqlHelper sqlHelper = new SqlHelper();

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public SqlStorage() {
        this(Config.get().getDbUrl(),
                Config.get().getDbUser(),
                Config.get().getDbPassword());
    }

    @Override
    public void clear() {
        String sql = "TRUNCATE TABLE resume CASCADE";
        sqlHelper.sqlExecutor(sql, connectionFactory, "notnull", ps -> {
            ps.executeUpdate();
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        if (countSelect(connectionFactory, uuid) != 0) throw new ExistStorageException(uuid, uuid);
        String sql = "INSERT INTO resume (uuid, full_name) VALUES (?,?)";
        sqlHelper.sqlExecutor(sql, connectionFactory, uuid, ps -> {
            ps.setString(1, uuid);
            ps.setString(2, resume.getFullName());
            ps.execute();
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        String sql = "UPDATE resume SET full_name=? WHERE uuid = ?";
        String uuid = resume.getUuid();
        sqlHelper.sqlExecutor(sql, connectionFactory, uuid, ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, uuid);
            int result = ps.executeUpdate();
            if (result == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        String sql = "SELECT * FROM resume r WHERE r.uuid =?";
        return sqlHelper.sqlExecutor(sql, connectionFactory, uuid, ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        String sql = "DELETE FROM resume r WHERE r.uuid =?";
        sqlHelper.sqlExecutor(sql, connectionFactory, uuid, ps -> {
            ps.setString(1, uuid);
            int result = ps.executeUpdate();
            if (result == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        String sql = "SELECT * FROM resume";
        List<Resume> resumeList = new ArrayList<>();
        return sqlHelper.sqlExecutor(sql, connectionFactory, "notnull", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resumeList.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            resumeList.sort(Resume::compareTo);
            return resumeList;
        });
    }

    @Override
    public int size() {
        return countSelect(connectionFactory, null);
    }

    /**
     * вспомогательный метод для сокращения кода
     */
    private int countSelect(ConnectionFactory connectionFactory, String whereStatement) {
        String sql = "SELECT COUNT(uuid) as CNT FROM resume";
        if (whereStatement != null) sql = String.format("%s WHERE uuid = '%s'", sql, whereStatement);
        return sqlHelper.sqlExecutor(sql, connectionFactory, "notnull", ps -> {
            ResultSet rs = ps.executeQuery();
            int size = 0;
            if (rs.next()) size = rs.getInt("CNT");
            return size;
        });
    }
}
