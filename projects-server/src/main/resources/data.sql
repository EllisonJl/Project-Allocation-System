-- Seeds the database with initial data.

-- All passwords are "password123"
INSERT INTO users (username, password_hash, name, role) VALUES
    ('jbloggs', '$2a$12$mtYLOQ1rWZxMjni99wxi4uUYvEK1ASDUm5Jm2RRXrfQT3TYCwlnKy', 'Joe Bloggs', 'staff'),
    ('zsw', '$2a$12$mtYLOQ1rWZxMjni99wxi4uUYvEK1ASDUm5Jm2RRXrfQT3TYCwlnKy', 'zzssww', 'staff'),
    ('mm20', '$2a$12$mtYLOQ1rWZxMjni99wxi4uUYvEK1ASDUm5Jm2RRXrfQT3TYCwlnKy', 'Max Mustermann', 'student'),
    ('jappleseed', '$2a$12$mtYLOQ1rWZxMjni99wxi4uUYvEK1ASDUm5Jm2RRXrfQT3TYCwlnKy', 'Jane Appleseed', 'student');

INSERT INTO projects (name, description, proposed_by) VALUES
    ('Foobar Gigatron', 'This project aims to extend the Foobar project to add Gigatron features. This will include supporting new requests to the Gizmobot.', 'jbloggs'),
    ('Advanced Widgetry', 'This project involves developing advanced features for the Widget application, including new user interfaces and enhanced performance metrics.', 'jbloggs'),
    ('Virtual Reality Space', 'Aims to create a new virtual reality environment for educational purposes, focusing on immersive learning experiences in science.', 'zsw'),
    ('Eco-Friendly Solutions', 'The project is dedicated to creating eco-friendly, sustainable energy solutions for small to medium-sized enterprises.', 'zsw');

INSERT INTO projects (name, description, proposed_by, assigned_to) VALUES
    ('SkyNet', 'Revolutionary AI-driven cloud management platform.', 'jbloggs', 'mm20');

INSERT INTO interested_in (student_username, project_id) VALUES
    ('mm20', (SELECT id FROM projects WHERE name = 'Foobar Gigatron'));
