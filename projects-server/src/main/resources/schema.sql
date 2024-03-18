-- Initialises the database schema, creating the tables
DROP TABLE IF EXISTS interested_in;
DROP TABLE IF EXISTS projects;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    username VARCHAR(250) PRIMARY KEY,
    password_hash VARCHAR(250) NOT NULL,
    name VARCHAR(250) NOT NULL,
    role VARCHAR(10) NOT NULL
);

CREATE TABLE projects (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    description VARCHAR(2000) NOT NULL,
    proposed_by VARCHAR(250) NOT NULL,
    assigned_to VARCHAR(250) DEFAULT NULL,
    FOREIGN KEY (proposed_by) REFERENCES users(username),
    FOREIGN KEY (assigned_to) REFERENCES users(username)
);

CREATE TABLE interested_in (
    student_username VARCHAR(250) NOT NULL,
    project_id INT NOT NULL,
    PRIMARY KEY (student_username, project_id),
    FOREIGN KEY (student_username) REFERENCES users(username),
    FOREIGN KEY (project_id) REFERENCES projects(id)
)

