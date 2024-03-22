package uk.ac.standrews.cs5031.group4.projectsserver.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.InterestedIn;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.InterestedInId;

import java.util.Optional;

public interface InterestedInRepository extends CrudRepository<InterestedIn, InterestedInId> {

    @Query("SELECT i FROM InterestedIn i WHERE i.student.username = :studentUsername AND i.project.id = :projectId")
    Optional<InterestedIn> findByStudentUsernameAndProjectId(@Param("studentUsername") String studentUsername, @Param("projectId") int projectId);
}
