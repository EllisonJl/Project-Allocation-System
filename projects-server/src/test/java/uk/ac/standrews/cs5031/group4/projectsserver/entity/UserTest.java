package uk.ac.standrews.cs5031.group4.projectsserver.entity;

import org.junit.jupiter.api.Test;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.User;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void createUser_WithAllParamsConstructor() {
        User user = new User("username", "passwordHash", "User Name", "role");

        assertNotNull(user);
        assertEquals("username", user.getUsername());
        assertEquals("passwordHash", user.getPassword());
        assertEquals("User Name", user.getName());
        assertEquals("role", user.getRole());
    }

    @Test
    public void setAndGetUsername() {
        User user = new User();
        user.setUsername("newUsername");

        assertEquals("newUsername", user.getUsername());
    }


    @Test
    public void setAndGetName() {
        User user = new User();
        user.setName("New Name");

        assertEquals("New Name", user.getName());
    }

    @Test
    public void setAndGetRole() {
        User user = new User();
        user.setRole("newRole");

        assertEquals("newRole", user.getRole());
    }

    @Test
    public void userEquality() {
        User user1 = new User("username", "passwordHash", "User Name", "role");
        User user2 = new User("username", "passwordHash", "User Name", "role");

        assertNotSame(user1, user2); // They are not the same instance
        assertEquals(user1, user2); // But they are considered equal based on business logic (if equals is overridden)
    }

}
