package uk.ac.standrews.cs5031.group4.projectsserver.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import uk.ac.standrews.cs5031.group4.projectsserver.repository.ProjectRepository;
import uk.ac.standrews.cs5031.group4.projectsserver.repository.UserRepository;
import uk.ac.standrews.cs5031.group4.projectsserver.service.JwtService;
import uk.ac.standrews.cs5031.group4.projectsserver.service.ProjectService;
import uk.ac.standrews.cs5031.group4.projectsserver.service.UserDetailsServiceImpl;

@WebMvcTest({ LoginController.class, ProjectController.class })
public class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AuthenticationManager authManager;
    @MockBean
    JwtService jwtService;
    @MockBean
    UserRepository userRepository;
    @MockBean
    ProjectRepository projectRepository;
    @MockBean
    UserDetailsServiceImpl userDetailsService;
    @MockBean
    ProjectService projectService;

    /**
     * Tests that users cannot access API routes when they aren't logged in.
     */
    @Test
    public void requestProtectedRouteWithoutUser() throws Exception {
        mockMvc.perform(get("/proposed-projects")).andExpect(status().isUnauthorized());
    }

    /**
     * Tests that users can access API routes when they are logged in.
     */
    @Test
    @WithMockUser
    public void requestProtectedRouteWithUser() throws Exception {
        mockMvc.perform(get("/proposed-projects")).andExpect(status().isOk());
    }
}
