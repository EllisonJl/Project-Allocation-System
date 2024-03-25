package uk.ac.standrews.cs5031.group4.projectsserver.repository;

import org.springframework.data.repository.CrudRepository;

import uk.ac.standrews.cs5031.group4.projectsserver.entities.InterestedIn;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.InterestedInId;

/**
 * Repository interface for managing InterestedIn entities.
 * Extends CrudRepository which provides basic CRUD operations.
 */
public interface InterestedInRepository extends CrudRepository<InterestedIn, InterestedInId> {
    // JPA automatically instantiates this interface to interact with the database
}
