package uk.ac.standrews.cs5031.group4.projectsserver.repository;

import org.springframework.data.repository.CrudRepository;

import uk.ac.standrews.cs5031.group4.projectsserver.entities.User;

/**
 * Repository interface for managing User entities.
 * Extends CrudRepository which provides basic CRUD operations.
 */
public interface UserRepository extends CrudRepository<User, String> {
    // JPA automatically instantiates this interface to interact with the database
}
