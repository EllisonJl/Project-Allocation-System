package uk.ac.standrews.cs5031.group4.projectsserver.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import uk.ac.standrews.cs5031.group4.projectsserver.entities.User;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.UserDetailsImpl;


import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {
    /**
     * The service we are testing, which gets re-initialised before each test.
     */
    private JwtService jwtService;
    /**
     * A user pre-initialised before each test
     */
    private User user;

    /**
     * Initialises the JwtService and a User for use in the tests.
     */
    @BeforeEach
    public void setup() {
        jwtService = new JwtService();
        user = new User("joebloggs", "Joe Bloggs", "student");
    }

    /**
     * Tests that the created JWT is valid.
     */
    @Test
    public void testCreatedTokenIsValid() {
        String token = jwtService.generateToken(user.getUsername());

        UserDetails userDetails = new UserDetailsImpl(user);

        assertTrue(jwtService.validateToken(token, userDetails));
    }

    /**
     * Tests that the username can be retrieved from the JWT.
     */
    @Test
    public void testGetUsernameFromCreatedToken() {
        String token = jwtService.generateToken(user.getUsername());

        String username = jwtService.extractUsername(token);

        assertEquals(user.getUsername(), username);
    }

    @Test
    public void validateToken_UsernameDoesNotMatch_ReturnsFalse() {
        String username = "testUser";
        String wrongUsername = "wrongUser";
        String token = jwtService.generateToken(username);
        UserDetails userDetails = new UserDetailsImpl(new User(wrongUsername, "Joe tony", "student"));
        boolean isValid = jwtService.validateToken(token, userDetails);

        assertFalse(isValid);
    }

}
