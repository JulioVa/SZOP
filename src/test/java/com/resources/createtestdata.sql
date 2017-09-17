INSERT INTO "user" (id, name, password, mail)
VALUES (1, 'name1', 'dfsgf2376', 'mail@name1.com');

INSERT INTO system (id, user_id, name)
VALUES (1, 1, 'system1'),
(2, 1, 'system2');

INSERT INTO schema (id, name, img)
VALUES (1, 'schema1', 'dgfdsghfdthf'),
(2, 'schema2', 'dgfdsghfdthf');

INSERT INTO sensor (id, sensor_id, name, type, last_update, is_active, system_id, schema_id, schema_x, schema_y)
VALUES (1, 1, 'sensor1', 1, '2017-08-01 10:23:54', true, 1, 1, 50, 50),
(2, 2, 'sensor2', 1, '2017-08-01 10:23:54', true, 1, 1, 50, 50),
(3, 3, 'sensor3', 2, '2017-09-01 10:23:54', true, 1, 1, 50, 50);

INSERT INTO alert (id, user_id, sensor_id, greater_lower, value)
VALUES (1, 1, 1, 'greater', 30.5);