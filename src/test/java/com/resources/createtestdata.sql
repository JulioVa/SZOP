INSERT INTO "user" (id, user_id, name, profile_pic, email)
VALUES (1, '111', 'name1', 'https://www.cbdzoe.pl/img/artykuly/rzeczy-ktore-wie-twoj-pies-artykuly-cbdzoe-06.jpg', 'mail@name1.com');

INSERT INTO system (id, user_id, name)
VALUES (1, 1, 'system1'),
(2, 1, 'system2');

INSERT INTO schema (id, name, img, user_id)
VALUES (1, 'schema1', 'dgfdsghfdthf',1),
(2, 'schema2', 'dgfdsghfdthf',1);

INSERT INTO sensor (id, sensor_id, name, type, last_update, is_active, system_id, schema_id, schema_x, schema_y)
VALUES (1, '1', 'sensor1', 1, '2017-08-01 10:23:54', true, 1, 1, 50, 50),
(2, '2', 'sensor2', 1, '2017-08-01 10:23:54', true, 1, 1, 50, 50),
(3, '3', 'sensor3', 2, '2017-09-01 10:23:54', true, 1, 1, 50, 50);

INSERT INTO alert (id, user_id, sensor_id, greater_lower, value)
VALUES (1, 1, 1, 'greater', 30.5);