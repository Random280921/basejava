drop table if exists contact;
drop table if exists resume;

create table resume
(
    uuid      varchar(36) not null
        constraint resume_pk
            primary key,
    full_name text     not null
);

alter table resume
    owner to postgres;

create table contact
(
    id          serial
        constraint contact_pk
            primary key,
    resume_uuid varchar(36)    not null
        constraint contact_resume_uuid_fk
            references resume (uuid)
            on delete cascade,
    c_type        varchar(32) not null,
    c_value       text        not null,
    c_url         text
);

create unique index contact_resume_uuid_type_uq
    on contact (resume_uuid, c_type);

alter table contact
    owner to postgres;