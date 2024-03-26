/**
 * Test class for UserController.
 * <p>
 * This class contains test methods to verify the functionality of the UserController class.
 * It uses MockMvc to simulate HTTP requests and verify the responses.
 */
package uk.ac.standrews.cs5031.group4.projectsserver.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.User;
import uk.ac.standrews.cs5031.group4.projectsserver.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    /**
     * Test method to get logged-in user details when the user does not exist.
     * <p>
     * This method sends a GET request to "/me" endpoint to retrieve details of the logged-in user.
     * It expects the HTTP status code to be INTERNAL_SERVER_ERROR (500) as the user does not exist.
     *
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    @WithMockUser(username = "testUser")
    void getLoggedInUserDetails_WhenUserDoesNotExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/me")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    /**
     * Test method to get logged-in user details when the user exists.
     * <p>
     * This method sets up test data by creating a mock user, saves it to the database,
     * and then sends a GET request to "/me" endpoint to retrieve details of the logged-in user.
     * It expects the HTTP status code to be OK (200) and verifies the details of the user.
     *
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    @WithMockUser(username = "testUser")
    void getLoggedInUserDetails_WhenUserExists() throws Exception {
        User mockUser = new User("testUser", "Test User", "USER_ROLE");
        mockUser.setPassword_hash("exampleHash");
        userRepository.save(mockUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/me")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("testUser"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test User"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value("USER_ROLE"));
    }
}
