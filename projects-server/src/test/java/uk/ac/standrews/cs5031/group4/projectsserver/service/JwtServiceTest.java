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

    /**
     * This method tests the behavior of the validateToken() method in the JwtService class
     * when the provided username in the UserDetails object does not match the username
     * used to generate the JWT token. It verifies that the validateToken() method returns
     * false in this scenario.
     */
    @Test
    public void validateToken_UsernameDoesNotMatch_ReturnsFalse() {
        // Initialize the test data
        String username = "testUser"; // Correct username used to generate the token
        String wrongUsername = "wrongUser"; // Incorrect username in the UserDetails object
        String token = jwtService.generateToken(username); // Generate token using correct username
        UserDetails userDetails = new UserDetailsImpl(new User(wrongUsername, "Joe tony", "student")); // UserDetails with incorrect username

        // Invoke the validateToken() method with the generated token and UserDetails
        boolean isValid = jwtService.validateToken(token, userDetails);

        // Assert that the token is not valid when the username does not match
        assertFalse(isValid);
    }
}
