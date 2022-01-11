package ru.javaonline.basejava.storage;

import ru.javaonline.basejava.exception.NotExistStorageException;
import ru.javaonline.basejava.model.Contact;
import ru.javaonline.basejava.model.ContactType;
import ru.javaonline.basejava.model.Resume;
import ru.javaonline.basejava.sql.SqlHelper;
import ru.javaonline.basejava.util.Config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        sqlHelper.execute(Config.get().getSqlCommand("clear"), LOG);
    }

    @Override
    public void save(Resume resume) {
        logCheckToNull("Save", resume, "Resume");
        sqlHelper.transactionalExecute(LOG, conn -> {
            modifyResume(conn, Config.get().getSqlCommand("insert_resume"), resume);
            modifyContact(conn, resume);
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        logCheckToNull("Update", resume, "Resume");
        sqlHelper.transactionalExecute(LOG, conn -> {
            modifyResume(conn, Config.get().getSqlCommand("update_resume"), resume);
            modifyContact(conn, resume);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        logCheckToNull("Get", uuid, "uuid");
        return sqlHelper.execute(Config.get().getSqlCommand("get"), LOG, ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, rs.getString("full_name"));
            do {
                readContact(rs.getString("c_type"),
                        rs.getString("c_value"),
                        rs.getString("c_url"),
                        resume);
            } while (rs.next());
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        logCheckToNull("Delete", uuid, "uuid");
        sqlHelper.execute(Config.get().getSqlCommand("delete"), LOG, ps -> {
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
            try (PreparedStatement psResume = conn.prepareStatement(Config.get().getSqlCommand("get_all_resume"));
                 PreparedStatement psContact = conn.prepareStatement(Config.get().getSqlCommand("get_all_contact"))) {
                Map<String, Resume> resumeMap = new HashMap<>();
                ResultSet rsResume = psResume.executeQuery();
                ResultSet rsContact = psContact.executeQuery();
                while (rsResume.next()) {
                    String uuid = rsResume.getString("uuid");
                    resumeMap.put(uuid, new Resume(uuid, rsResume.getString("full_name")));
                }
                while (rsContact.next()) {
                    readContact(rsContact.getString("c_type"),
                            rsContact.getString("c_value"),
                            rsContact.getString("c_url"),
                            resumeMap.get(rsContact.getString("resume_uuid")));
                }
                List<Resume> resumeList = new ArrayList<>(resumeMap.values());
                resumeList.sort(Resume::compareTo);
                return resumeList;
            }
        });
    }

    @Override
    public int size() {
        LOG.info("size");
        return sqlHelper.execute(Config.get().getSqlCommand("size"), LOG, ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    /**
     * вспомогательный метод для сокращения кода
     * модификация таблицы resume
     */
    private void modifyResume(Connection connection, String sql, Resume resume) throws SQLException {
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
     * модификация таблицы contact
     */
    private void modifyContact(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement psDel = connection.prepareStatement(Config.get().getSqlCommand("delete_contact"));
             PreparedStatement psIns = connection.prepareStatement(Config.get().getSqlCommand("insert_contact"))) {
            psDel.setString(1, resume.getUuid());
            psDel.execute();
            for (Map.Entry<ContactType, Contact> e : resume.getHeader().entrySet()) {
                psIns.setString(1, e.getKey().name());
                psIns.setString(2, e.getValue().getValue());
                psIns.setString(3, e.getValue().getUrl());
                psIns.setString(4, resume.getUuid());
                psIns.addBatch();
            }
            psIns.executeBatch();
        }
    }

    /**
     * вспомогательный метод для сокращения кода
     * чтение и наполнение контактов
     */
    private void readContact(String c_type, String c_value, String c_url, Resume resume) {
        resume.addContact(ContactType.valueOf(c_type), new Contact(c_value, c_url));
    }
}
