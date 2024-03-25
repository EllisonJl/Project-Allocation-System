package uk.ac.standrews.cs5031.group4.projectsserver.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import uk.ac.standrews.cs5031.group4.projectsserver.entities.Project;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, String> {
    // JPA automatically instantiates this interface to interact with the database

    List<Project> findByAssignedStudentIsNull();

    Project findById(int id);

    List<Project> findByProposedByStaffUsername(String username);

    //List<Project> findByAssignedStudentIsEmpty();

//    @Query(value = "SELECT * FROM projects WHERE assigned_to IS NULL OR assigned_to = ''", nativeQuery = true)
//    List<Project> findByAssignedStudentIsEmptyUser();
}
