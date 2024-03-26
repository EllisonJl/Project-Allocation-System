/**
 * Test class for the User entity.
 * <p>
 * This class contains test methods to verify the behavior of the User entity class.
 */
package uk.ac.standrews.cs5031.group4.projectsserver.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.InterestedIn;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.User;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private User user;
    private Set<InterestedIn> initialProjects;
    private Set<InterestedIn> newProjects;

    /**
     * Method to set up the test environment before each test method execution.
     */
    @BeforeEach
    public void setUp() {
        user = new User();
        initialProjects = new HashSet<>();
        initialProjects.add(new InterestedIn(/* parameters if needed */));
        initialProjects.add(new InterestedIn(/* parameters if needed */));

        newProjects = new HashSet<>();
        newProjects.add(new InterestedIn(/* parameters if needed */));
    }

    /**
     * Test method to verify setting and getting interested projects for a user.
     */
    @Test
    public void testSetAndGetInterestedInProjects() {
        // Set the initial set of projects
        user.setInterestedInProjects(initialProjects);

        // Verify that the getter returns the set that was set
        assertEquals(initialProjects, user.getInterestedInProjects(), "The returned set of projects should match the set that was provided");
    }

    /**
     * Test method to verify updating interested projects for a user.
     */
    @Test
    public void testUpdatingInterestedInProjects() {
        // Set the initial set of projects
        user.setInterestedInProjects(initialProjects);

        // Change the set of interested projects
        user.setInterestedInProjects(newProjects);

        // Verify that the updated set is returned
        assertEquals(newProjects, user.getInterestedInProjects(), "The returned set of projects should be updated to the new set");
        assertNotEquals(initialProjects, user.getInterestedInProjects(), "The returned set of projects should no longer match the initial set");
    }

    /**
     * Test method to verify creating a user with the all parameters constructor.
     */
    @Test
    public void createUser_WithAllParamsConstructor() {
        User user = new User("username", "passwordHash", "User Name", "role");

        assertNotNull(user);
        assertEquals("username", user.getUsername());
        assertEquals("passwordHash", user.getPassword());
        assertEquals("User Name", user.getName());
        assertEquals("role", user.getRole());
    }

    /**
     * Test method to verify setting and getting username for a user.
     */
    @Test
    public void setAndGetUsername() {
        User user = new User();
        user.setUsername("newUsername");

        assertEquals("newUsername", user.getUsername());
    }

    /**
     * Test method to verify setting and getting name for a user.
     */
    @Test
    public void setAndGetName() {
        User user = new User();
        user.setName("New Name");

        assertEquals("New Name", user.getName());
    }

    /**
     * Test method to verify setting and getting role for a user.
     */
    @Test
    public void setAndGetRole() {
        User user = new User();
        user.setRole("newRole");

        assertEquals("newRole", user.getRole());
    }

    /**
     * Test method to verify equality of User instances.
     */
    @Test
    public void userEquality() {
        User user1 = new User("username", "passwordHash", "User Name", "role");
        User user2 = new User("username", "passwordHash", "User Name", "role");

        assertNotSame(user1, user2); // They are not the same instance
        assertEquals(user1, user2); // But they are considered equal based on business logic (if equals is overridden)
    }
}
