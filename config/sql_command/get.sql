SELECT r.full_name,
       c.type,
       c.value,
       c.url
FROM resume r
         LEFT JOIN contact C
                   ON r.uuid = C.resume_uuid
WHERE r.uuid = ?