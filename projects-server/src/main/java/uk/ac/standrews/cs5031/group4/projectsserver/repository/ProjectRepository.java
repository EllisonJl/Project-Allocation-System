package uk.ac.standrews.cs5031.group4.projectsserver.repository;

import org.springframework.data.repository.CrudRepository;

import uk.ac.standrews.cs5031.group4.projectsserver.entities.Project;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, String> {
    // JPA automatically instantiates this interface to interact with the database
    List<Project> findByProposedByStaffUsername(String username);
}
