package uk.ac.standrews.cs5031.group4.projectsserver.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import uk.ac.standrews.cs5031.group4.projectsserver.entities.User;
import uk.ac.standrews.cs5031.group4.projectsserver.repository.UserRepository;


@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    /**
     * Used for writing JSON for requests.
     */
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Tests that a user who is registered on the system can successfully log in.
     */
    @Test
    public void userLoginSuccess() throws Exception {
        User user = new User("joebloggs", passwordEncoder.encode("password1"), "Joe Bloggs", "student");
        userRepository.save(user);

        LoginRequestBody request = new LoginRequestBody("joebloggs", "password1");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    /**
     * When attempting to log in with invalid credentials, the login should fail
     * and return Unauthorized.
     */
    @Test
    public void userLoginInvalidCredentials() throws Exception {
        LoginRequestBody request = new LoginRequestBody("username", "password");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    /**
     * Tests that users cannot access API routes when they aren't logged in.
     */
    @Test
    public void requestProtectedRouteWithoutUser() throws Exception {
        mockMvc.perform(get("/proposed-projects")).andExpect(status().isUnauthorized());
    }

    /**
     * Tests that users cannot access API routes when they don't have the correct
     * role.
     */
    @Test
    @WithMockUser
    public void requestProtectedRouteWithUserButNotCorrectRole() throws Exception {
        mockMvc.perform(get("/proposed-projects")).andExpect(status().isForbidden());
    }

    /**
     * Tests that users can access API routes when they are logged in and have the
     * correct role for the route.
     */
    @Test
    @WithMockUser(authorities = "staff") // The staff authority is required for this route
    public void requestProtectedRouteWithUserWithCorrectRole() throws Exception {
        mockMvc.perform(get("/proposed-projects")).andExpect(status().isOk());
    }

    @Test
    public void unregisteredUserCannotLogin() throws Exception {
        LoginRequestBody request = new LoginRequestBody("unregistered", "password");
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    /**
     * Tests that the server handles incorrect JSON format gracefully.
     */
    @Test
    public void loginWithMalformedJson() throws Exception {
        String malformedJson = "{\"username\":\"user\", \"password\":";
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malformedJson))
                .andExpect(status().isBadRequest());
    }

}
