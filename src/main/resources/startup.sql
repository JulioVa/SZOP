--CREATE USER
create table "user"(
  id serial not null
    constraint user_pkey
    primary key,
  name varchar(50) not null,
  password varchar(50) not null,
  mail varchar(50) not null
);
create unique index user_id_uindex on "user" (id);
create unique index user_name_uindex on "user" (name);
create unique index user_mail_uindex on "user" (mail);
--CREATE SYSTEM
create table system(
  id serial not null
    constraint system_pkey
    primary key,
  user_id integer not null
    constraint system_user_id_fk
    references "user"
    on update cascade on delete cascade
    deferrable,
  name varchar(50) not null
);
create unique index system_id_uindex on system (id);
--CREATE SCHEMA
create table schema(
  name varchar(50) not null,
  img bytea not null,
  id serial not null
    constraint schema_id_pk
    primary key,
  user_id integer not null
    constraint system_user_id_fk
    references "user"
    on update cascade on delete cascade
      deferrable
);
create unique index schema_id_uindex on schema (id);
--CREATE SENSOR
create table sensor(
  id serial not null
    constraint sensor_pkey
    primary key,
  sensor_id varchar(50),
  name varchar(50) not null,
  type integer not null,
  last_update TIMESTAMP,
  is_active boolean not null,
  system_id integer not null
    constraint sensor_system_id_fk
    references system
    on update cascade on delete cascade,
  schema_id integer
    constraint sensor_schema_id_fk
    references schema,
  schema_x integer,
  schema_y integer
);
create unique index sensor_id_uindex on sensor (id);
--CREATE ALERT
create table alert(
  id serial not null
    constraint alert_pkey
    primary key,
  user_id integer not null
    constraint alert_user_id_fk
    references "user"
    on update cascade on delete cascade,
  sensor_id integer not null
    constraint alert_sensor_id_fk
    references sensor
    on update cascade on delete cascade,
  greater_lower varchar(50),
  value double precision
);
create unique index alert_id_uindex on alert (id);



