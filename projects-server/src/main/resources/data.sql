-- Seeds the database with initial data.

INSERT INTO users (username, password_hash, name, role) VALUES
    ('jbloggs', '', 'Joe Bloggs', 'staff'),
    ('zsw', '', 'zzssww', 'staff'),
    ('mm20', '', 'Max Mustermann', 'student'),
    ('jappleseed', '', 'Jane Appleseed', 'student');

INSERT INTO projects (name, description, proposed_by) VALUES
    ('Foobar Gigatron', 'This project aims to extend the Foobar project to add Gigatron features. This will include supporting new requests to the Gizmobot.', 'jbloggs');
INSERT INTO projects (name, description, proposed_by) VALUES
    ('Advanced Widgetry', 'This project involves developing advanced features for the Widget application, including new user interfaces and enhanced performance metrics.', 'jbloggs');
INSERT INTO projects (name, description, proposed_by) VALUES
    ('Virtual Reality Space', 'Aims to create a new virtual reality environment for educational purposes, focusing on immersive learning experiences in science.', 'zsw');
INSERT INTO projects (name, description, proposed_by) VALUES
    ('Eco-Friendly Solutions', 'The project is dedicated to creating eco-friendly, sustainable energy solutions for small to medium-sized enterprises.', 'zsw');


INSERT INTO interested_in (student_username, project_id) VALUES
    ('mm20', (SELECT id FROM projects WHERE name = 'Foobar Gigatron'));
