-- Seeds the database with initial data.

INSERT INTO users (username, password_hash, name, role) VALUES
    ('jbloggs', '', 'Joe Bloggs', 'staff'),
    ('mm20', '', 'Max Mustermann', 'student'),
    ('jappleseed', '', 'Jane Appleseed', 'student');

INSERT INTO projects (name, description, proposed_by) VALUES
    ('Foobar Gigatron', 'This project aims to extend the Foobar project to add Gigatron features. This will include supporting new requests to the Gizmobot.', 'jbloggs');

INSERT INTO interested_in (student_username, project_id) VALUES
    ('mm20', (SELECT id FROM projects WHERE name = 'Foobar Gigatron'));
