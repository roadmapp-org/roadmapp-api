INSERT INTO users VALUES
    ('tobiolea',1,'$2a$10$DJSezd2kgaLZ1MKHSzC7KOHi8.IQLNE5KG4lPY9IYE5Wlu.4kpeKq','USER'),
    ('smartineza',1,'$2a$10$DJSezd2kgaLZ1MKHSzC7KOHi8.IQLNE5KG4lPY9IYE5Wlu.4kpeKq','USER');

INSERT INTO projects (active, name, owner) VALUES (1, 'Cumpleaños de Leo Messi', 'tobiolea');
    INSERT INTO tasks (active, name, project_id) VALUES (1, 'Audio', 1);
        INSERT INTO logs (project_id, task_id, date, log, active) VALUES (1, 1, '2024-04-01 13:45', 'Se solicitó presupuesto a Strato.\\nTel: 11 6920 3645', 1);
        INSERT INTO logs (project_id, task_id, date, log, active) VALUES (1, 1, '2024-04-01 13:50', 'Se solicitó presupuesto a FaMusic.\\nTel:47484146', 1);
        INSERT INTO logs (project_id, task_id, date, log, active) VALUES (1, 1, '2024-04-02 12:34', 'Presupuesto FaMusic: $150.000 (sin consola)', 1);
        INSERT INTO logs (project_id, task_id, date, log, active) VALUES (1, 1, '2024-04-03 17:18', 'Se recibió presupuesto de Strato: $120.000 ', 1);
        INSERT INTO logs (project_id, task_id, date, log, active) VALUES (1, 1, '2024-04-05 19:10', 'Se confirma presupuesto de Strato.', 1);
    INSERT INTO tasks (active, name, project_id) VALUES (1, 'Recepcion', 1);
        INSERT INTO logs (project_id, task_id, date, log, active) VALUES (1, 2, '2024-04-05 13:45', 'Se solicito presupuesto a Platos del Sur.\\nTel: 11 6920 3645', 1);
        INSERT INTO logs (project_id, task_id, date, log, active) VALUES (1, 2, '2024-04-05 14:07', 'Se solicito presupuesto a MST SA.\\nTel: 11 6920 3645', 1);
        INSERT INTO logs (project_id, task_id, date, log, active) VALUES (1, 2, '2024-04-05 14:15', 'Se solicito presupuesto a Events SA.\\nTel: 11 6920 3645', 1);
        INSERT INTO logs (project_id, task_id, date, log, active) VALUES (1, 2, '2024-04-06 09:15', 'Se coordino una reunion con Nico de Platos del sur para detalles.', 1);
        INSERT INTO logs (project_id, task_id, date, log, active) VALUES (1, 2, '2024-04-06 09:18', 'Presupuesto MST: $200.000.', 1);
    INSERT INTO tasks (active, name, project_id) VALUES (1, 'Barra', 1);
    INSERT INTO tasks (active, name, project_id) VALUES (1, 'Comida', 1);
    INSERT INTO tasks (active, name, project_id) VALUES (1, 'Salon de eventos', 1);

INSERT INTO projects (active, name, owner) VALUES (1, 'Despedida de soltera de Mirta Legrand', 'tobiolea');
INSERT INTO projects (active, name, owner) VALUES (1, 'Fiesta de fin de año del BBVA', 'tobiolea');


--
--INSERT INTO projects (active, name, owner) VALUES (1, 'Instagram', 'tobiolea');
--    INSERT INTO tasks (active, name, project_id) VALUES (1, 'Stories', 1);
--        INSERT INTO logs (project_id, task_id, date, log, active) VALUES (1, 1, '2023-12-07 13:45:28', 'Stories panel has been defined to be placed at the top of the screen.', 1);
--    INSERT INTO tasks (active, name, project_id) VALUES (1, 'Direct Message', 1);
--    INSERT INTO tasks (active, name, project_id) VALUES (1, 'Feed', 1);
--    INSERT INTO tasks (active, name, project_id) VALUES (1, 'Likes', 1);
--
--INSERT INTO projects (active, name, owner) VALUES (1, 'Meta', 'tobiolea');
--INSERT INTO tasks (active, name, project_id) VALUES (1, 'Marketplace', 2);
--INSERT INTO tasks (active, name, project_id) VALUES (1, 'Direct Message', 2);
--INSERT INTO tasks (active, name, project_id) VALUES (1, 'Adds', 2);
--
--INSERT INTO projects (active, name, owner) VALUES (1, 'Spotify', 'tobiolea');
--INSERT INTO tasks (active, name, project_id) VALUES (1, 'Player', 3);
--INSERT INTO tasks (active, name, project_id) VALUES (1, 'Playlist creation', 3);
--INSERT INTO tasks (active, name, project_id) VALUES (1, 'Library', 3);
--
--
