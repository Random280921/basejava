SELECT r.full_name,
       c.c_type,
       c.c_value,
       c.c_url
FROM resume r
         LEFT JOIN contact C ON r.uuid = C.resume_uuid
WHERE r.uuid = ?