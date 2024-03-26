/**
 * Test class for the User entity's equals() method.
 * <p>
 * This class contains test methods to verify the behavior of the equals() method in the User entity class.
 * It tests the equality of User instances based on different scenarios.
 */
package uk.ac.standrews.cs5031.group4.projectsserver.entity;

import org.junit.jupiter.api.Test;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.User;

import static org.junit.jupiter.api.Assertions.*;

class UserEqualTest {

    /**
     * Test method to verify if a User is equal to itself.
     */
    @Test
    void equalsSelf() {
        User user = new User("username", "hashedPassword", "User Name", "role");
        assertTrue(user.equals(user), "User should be equal to itself.");
    }

    /**
     * Test method to verify if a User is not equal to null.
     */
    @Test
    void equalsNull() {
        User user = new User("username", "hashedPassword", "User Name", "role");
        assertFalse(user.equals(null), "User should not be equal to null.");
    }

    /**
     * Test method to verify if a User is not equal to an object of a different type.
     */
    @Test
    void equalsDifferentType() {
        User user = new User("username", "hashedPassword", "User Name", "role");
        Object other = new Object();
        assertFalse(user.equals(other), "User should not be equal to an object of a different type.");
    }

    /**
     * Test method to verify if Users with different usernames are not equal.
     */
    @Test
    void equalsDifferentUsername() {
        User user1 = new User("username1", "hashedPassword", "User Name", "role");
        User user2 = new User("username2", "hashedPassword", "User Name", "role");
        assertFalse(user1.equals(user2), "Users with different usernames should not be equal.");
    }

    /**
     * Test method to verify if Users with different names are not equal.
     */
    @Test
    void equalsDifferentName() {
        User user1 = new User("username", "hashedPassword", "User Name 1", "role");
        User user2 = new User("username", "hashedPassword", "User Name 2", "role");
        assertFalse(user1.equals(user2), "Users with different names should not be equal.");
    }

    /**
     * Test method to verify if Users with different roles are not equal.
     */
    @Test
    void equalsDifferentRole() {
        User user1 = new User("username", "hashedPassword", "User Name", "role1");
        User user2 = new User("username", "hashedPassword", "User Name", "role2");
        assertFalse(user1.equals(user2), "Users with different roles should not be equal.");
    }

    /**
     * Test method to verify if Users with all attributes equal are considered equal.
     */
    @Test
    void equalsAllAttributesEqual() {
        User user1 = new User("username", "hashedPassword", "User Name", "role");
        User user2 = new User("username", "hashedPassword", "User Name", "role");
        assertTrue(user1.equals(user2), "Users with all attributes equal should be considered equal.");
    }
}
