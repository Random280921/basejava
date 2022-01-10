package ru.javaonline.basejava.storage;

import ru.javaonline.basejava.exception.NotExistStorageException;
import ru.javaonline.basejava.model.Contact;
import ru.javaonline.basejava.model.ContactType;
import ru.javaonline.basejava.model.Resume;
import ru.javaonline.basejava.sql.SqlHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
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
        sqlHelper.execute("TRUNCATE TABLE resume CASCADE", LOG);
    }

    @Override
    public void save(Resume resume) {
        logCheckToNull("Save", resume, "Resume");
        sqlHelper.execute("INSERT INTO resume (uuid, full_name) VALUES (?,?)", LOG, ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
            return null;
        });
        for (Map.Entry<ContactType, Contact> e : resume.getHeader().entrySet()) {
            sqlHelper.execute("INSERT INTO contact (resume_uuid, type, value, url) VALUES (?,?,?,?)", LOG, ps -> {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue().getValue());
                ps.setString(4, e.getValue().getUrl());
                return null;
            });
        }
    }

    @Override
    public void update(Resume resume) {
        logCheckToNull("Update", resume, "Resume");
        sqlHelper.execute("UPDATE resume SET full_name=? WHERE uuid = ?", LOG, ps -> {
            String uuid = resume.getUuid();
            ps.setString(1, resume.getFullName());
            ps.setString(2, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        logCheckToNull("Get", uuid, "uuid");
        return sqlHelper.execute(" SELECT r.full_name, c.type, c.value, c.url" +
                " FROM resume r" +
                " LEFT JOIN contact c ON r.uuid = c.resume_uuid" +
                " WHERE r.uuid= ?", LOG, ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, rs.getString("full_name"));
            do {
                ContactType type = ContactType.valueOf(rs.getString("type"));
                resume.addContact(type,
                        new Contact(rs.getString("value"),
                                rs.getString("url")));
            } while (rs.next());
            return resume;
        });
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
        return sqlHelper.execute("SELECT * FROM resume ORDER BY full_name, uuid", LOG, ps -> {
            List<Resume> resumeList = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resumeList.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return resumeList;
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
}
