package ru.javaonline.basejava.storage;

import ru.javaonline.basejava.exception.NotExistStorageException;
import ru.javaonline.basejava.model.*;
import ru.javaonline.basejava.sql.SqlHelper;
import ru.javaonline.basejava.util.JsonParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Класс - реализация хранилища в БД
 *
 * @author KAIvanov
 * created by 06.01.2022 21:39
 * @version 1.0
 */
public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        LOG.info("clear");
        sqlHelper.execute("TRUNCATE TABLE resume CASCADE", LOG);
    }

    @Override
    public void save(Resume resume) {
        logCheckToNull("Save", resume, "Resume");
        sqlHelper.transactionalExecute(LOG, conn -> {
            modifyResume(conn, "INSERT INTO resume (full_name, uuid) VALUES (?,?)", resume);
            insertContact(conn, resume);
            insertSection(conn, resume);
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        logCheckToNull("Update", resume, "Resume");
        sqlHelper.transactionalExecute(LOG, conn -> {
            modifyResume(conn, "UPDATE resume SET full_name=? WHERE uuid = ?", resume);
            deleteTable(conn, resume.getUuid(), "DELETE FROM contact WHERE resume_uuid=?");
            insertContact(conn, resume);
            deleteTable(conn, resume.getUuid(), "DELETE FROM section WHERE resume_uuid=?");
            insertSection(conn, resume);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        logCheckToNull("Get", uuid, "uuid");
        Map<String, Resume> resumeMap = sqlHelper.transactionalExecute(LOG, conn -> {
            try (PreparedStatement psResume = conn.prepareStatement("SELECT * FROM resume WHERE uuid = ?");
                 PreparedStatement psContact = conn.prepareStatement(
                         "SELECT resume_uuid AS uuid, c_type, c_value, c_url FROM contact WHERE resume_uuid=?");
                 PreparedStatement psSectionText = conn.prepareStatement(
                         "SELECT resume_uuid AS uuid, t_type, t_content FROM section WHERE resume_uuid=?")) {
                psResume.setString(1, uuid);
                psContact.setString(1, uuid);
                psSectionText.setString(1, uuid);
                return buildResumes(1, psResume, psContact, psSectionText);
            }
        });
        return resumeMap.get(uuid);
    }

    @Override
    public void delete(String uuid) {
        logCheckToNull("Delete", uuid, "uuid");
        sqlHelper.execute("DELETE FROM resume r WHERE r.uuid =?", LOG, ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        return sqlHelper.transactionalExecute(LOG, conn -> {
            try (PreparedStatement psResume = conn.prepareStatement(
                    "SELECT * FROM resume ORDER BY full_name, uuid");
                 PreparedStatement psContact = conn.prepareStatement(
                         "SELECT resume_uuid AS uuid, c_type, c_value, c_url FROM contact");
                 PreparedStatement psSectionText = conn.prepareStatement(
                         "SELECT resume_uuid AS uuid, t_type, t_content FROM section")) {
                return new ArrayList<>(buildResumes(0, psResume, psContact, psSectionText).values());
            }
        });
    }

    @Override
    public int size() {
        LOG.info("size");
        return sqlHelper.execute("SELECT COUNT(uuid) AS CNT FROM resume", LOG, ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    /**
     * вспомогательный метод для сокращения кода
     * сборка резюме из всех таблиц
     */
    private Map<String, Resume> buildResumes(int checkExist,
                                             PreparedStatement psResume,
                                             PreparedStatement psContact,
                                             PreparedStatement psSectionText) throws SQLException {
        Map<String, Resume> resumeMap = new LinkedHashMap<>();
        ResultSet rsResume = psResume.executeQuery();
        ResultSet rsContact = psContact.executeQuery();
        ResultSet rsSectionText = psSectionText.executeQuery();
        while (rsResume.next()) {
            String uuid = rsResume.getString("uuid");
            resumeMap.put(uuid, new Resume(uuid, rsResume.getString("full_name")));
        }
        if (resumeMap.size() == 0 & checkExist == 1) {
            throw new NotExistStorageException("");
        }
        readContact(rsContact, resumeMap);
        readSection(rsSectionText, resumeMap);
        return resumeMap;
    }

    /**
     * вспомогательный метод для сокращения кода
     * модификация таблицы resume
     */
    private void modifyResume(Connection connection,
                              String sql,
                              Resume resume) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            String uuid = resume.getUuid();
            ps.setString(1, resume.getFullName());
            ps.setString(2, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
        }
    }

    /**
     * вспомогательный метод для сокращения кода
     * insert в таблицу параметров резюме
     */
    private <K, V> void insertTable(Connection connection,
                                    Map<K, V> map,
                                    String sql,
                                    SqlHelper.SqlConsumer<K, V> sqlConsumer) throws SQLException {
        try (PreparedStatement psIns = connection.prepareStatement(sql)) {
            for (Map.Entry<K, V> e : map.entrySet()) {
                sqlConsumer.accept(psIns, e.getKey(), e.getValue());
                psIns.addBatch();
            }
            psIns.executeBatch();
        }
    }

    /**
     * вспомогательный метод для сокращения кода
     * delete из таблицы параметров резюме
     */
    private void deleteTable(Connection connection, String uuid, String sql) throws SQLException {
        try (PreparedStatement psDel = connection.prepareStatement(sql)) {
            psDel.setString(1, uuid);
            psDel.execute();
        }
    }

    /**
     * вспомогательный метод для сокращения кода
     * insert в таблицу контактов
     */
    private void insertContact(Connection conn, Resume resume) throws SQLException {
        insertTable(conn,
                resume.getHeader(),
                "INSERT INTO contact (c_type, c_value, c_url, resume_uuid) VALUES (?,?,?,?)",
                (p, k, v) -> {
                    p.setString(1, k.name());
                    p.setString(2, v.getValue());
                    p.setString(3, v.getUrl());
                    p.setString(4, resume.getUuid());
                });
    }

    /**
     * вспомогательный метод для сокращения кода
     * чтение и наполнение контактов
     */
    private void readContact(ResultSet resultSet,
                             Map<String, Resume> map) throws SQLException {
        while (resultSet.next()) {
            String cType = resultSet.getString("c_type");
            if (cType != null)
                map.get(resultSet.getString("uuid"))
                        .addContact(ContactType.valueOf(cType),
                                new Contact(resultSet.getString("c_value"),
                                        resultSet.getString("c_url")));
        }
    }

    /**
     * вспомогательный метод для сокращения кода
     * insert в таблицу текстовых секций
     */
    private void insertSection(Connection conn, Resume resume) throws SQLException {
        insertTable(conn,
                resume.getBody(),
                "INSERT INTO section (t_type, t_content, resume_uuid) VALUES (?,?,?)",
                (p, k, v) -> {
                    p.setString(1, k.name());
                    p.setString(2, JsonParser.write(v, AbstractSection.class));
                    p.setString(3, resume.getUuid());
                });
    }

    /**
     * вспомогательный метод для сокращения кода
     * чтение и наполнение текстовых секций
     */
    private void readSection(ResultSet resultSet,
                             Map<String, Resume> map) throws SQLException {
        while (resultSet.next()) {
            String tType = resultSet.getString("t_type");
            if (tType != null) {
                SectionType sectionType = SectionType.valueOf(tType);
                map.get(resultSet.getString("uuid")).addSection(sectionType,
                        JsonParser.read(resultSet.getString("t_content"), AbstractSection.class));
            }
        }
    }
}
