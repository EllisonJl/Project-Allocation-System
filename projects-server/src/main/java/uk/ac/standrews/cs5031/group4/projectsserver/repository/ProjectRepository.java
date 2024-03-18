package uk.ac.standrews.cs5031.group4.projectsserver.repository;

import org.springframework.data.repository.CrudRepository;

import uk.ac.standrews.cs5031.group4.projectsserver.entities.Project;

public interface ProjectRepository extends CrudRepository<Project, String> {
    // JPA automatically instantiates this interface to interact with the database
}
