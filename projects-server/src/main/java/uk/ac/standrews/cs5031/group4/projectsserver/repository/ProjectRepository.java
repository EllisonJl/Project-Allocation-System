package uk.ac.standrews.cs5031.group4.projectsserver.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import uk.ac.standrews.cs5031.group4.projectsserver.entities.Project;

import java.util.List;

/**
 * Repository interface for managing Project entities.
 * Extends CrudRepository which provides basic CRUD operations.
 */
public interface ProjectRepository extends CrudRepository<Project, String> {
    // JPA automatically instantiates this interface to interact with the database

    /**
     * Find all projects that are not assigned to any student.
     *
     * @return A list of projects that are not assigned to any student.
     */
    List<Project> findByAssignedStudentIsNull();

    /**
     * Find a project by its ID.
     *
     * @param id The ID of the project.
     * @return The project with the specified ID, or null if not found.
     */
    Project findById(int id);

    /**
     * Find all projects proposed by a staff member with the specified username.
     *
     * @param username The username of the staff member.
     * @return A list of projects proposed by the staff member with the specified username.
     */
    List<Project> findByProposedByStaffUsername(String username);

    //List<Project> findByAssignedStudentIsEmpty();

//    @Query(value = "SELECT * FROM projects WHERE assigned_to IS NULL OR assigned_to = ''", nativeQuery = true)
//    List<Project> findByAssignedStudentIsEmptyUser();
}
