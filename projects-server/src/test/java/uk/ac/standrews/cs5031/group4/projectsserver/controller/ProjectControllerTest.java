package uk.ac.standrews.cs5031.group4.projectsserver.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import uk.ac.standrews.cs5031.group4.projectsserver.entities.Project;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.User;
import uk.ac.standrews.cs5031.group4.projectsserver.repository.ProjectRepository;
import uk.ac.standrews.cs5031.group4.projectsserver.repository.UserRepository;
import uk.ac.standrews.cs5031.group4.projectsserver.service.ProjectService;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectController projectController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectService projectService;

    @Test
    void getAvailableProjectsWhenNoProjectsAreAvailable() {
        // ensure there are no stored projects
        projectRepository.deleteAll();

        ResponseEntity<List<Project>> responseEntity = projectController.getAvailableProjects();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.hasBody());
        assertTrue(responseEntity.getBody().isEmpty());
        // verify(projectRepository, Mockito.times(1)).findByAssignedStudentIsNull();
    }

    /**
     * This method tests the behavior of the getAvailableProjects() method in the ProjectController class
     * when projects are available. It verifies that the response entity contains the expected project list
     * with HTTP status code OK.
     */
    @Test
    void getAvailableProjectsWhenProjectsAreAvailable() {
        // Given
        User user = new User("jbloggs", "", "Joe Bloggs", "staff");
        Project project = new Project("Foobar",
                "This project aims to extend the Foobar project to add Gigatron features.", user, null);

        userRepository.save(user);
        projectRepository.save(project);

        // When
        ResponseEntity<List<Project>> responseEntity = projectController.getAvailableProjects();

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.hasBody());
        assertTrue(responseEntity.getBody().contains(project));
    }

    /**
     * This method tests the behavior of the getProjectById() method in the ProjectController class
     * when the project is present. It verifies that the response entity contains the expected project
     * with HTTP status code OK.
     */
    @Test
    void getProjectByIdWhenProjectIsPresent() {
        // Given
        User user = new User("jbloggs", "", "Joe Bloggs", "staff");
        Project expectedProject = new Project("Foobar",
                "This project aims to extend the Foobar project to add Gigatron features.",
                user, null);

        userRepository.save(user);
        projectRepository.save(expectedProject);

        // When
        ResponseEntity<Project> responseEntity = projectController.getProjectById(Integer.toString(expectedProject.getId()));

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedProject, responseEntity.getBody());
    }

    /**
     * This method tests the behavior of the getProjectById() method in the ProjectController class
     * when the project is not present. It verifies that the response entity has HTTP status code
     * NOT_FOUND and the body is null.
     */
    @Test
    void getProjectByIdWhenProjectIsNotPresent() {
        // Given
        int invalidId = 21;

        // When
        ResponseEntity<Project> responseEntity = projectController.getProjectById(Integer.toString(invalidId));

        // Then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    /**
     * This method tests the behavior of the proposeProject() method in the ProjectController class
     * when a staff user proposes a new project. It verifies that the created project is returned
     * with HTTP status code CREATED.
     *
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    @WithMockUser(username = "username", authorities = "staff")
    public void proposeProject_ShouldReturnCreatedProject() throws Exception {
        // Given
        User mockUser = new User("username", "", "Name", "staff");
        userRepository.save(mockUser);

        // When/Then
        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Project\", \"description\":\"Description\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Project"))
                .andExpect(jsonPath("$.description").value("Description"));
    }

    /**
     * This method tests the behavior of the acceptStudent() method in the ProjectController class
     * when a staff user accepts a student for a project successfully. It verifies that the HTTP status
     * code is OK.
     *
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    @WithMockUser(authorities = "staff")
    public void acceptStudent_WhenSuccess_ReturnsOk() throws Exception {
        // Given
        User user = new User("user1", "", "User One", "staff");
        Project mockProject = new Project("Test Project", "Description", user);

        userRepository.save(user);
        projectRepository.save(mockProject);

        User mockUser = new User("studentUsername", "", "Student", "student");
        userRepository.save(mockUser);

        projectService.registerStudentInterest(mockUser.getUsername(), mockProject.getId());

        // When/Then
        mockMvc.perform(post("/projects/" + mockProject.getId() + "/accept-student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"student_username\":\"studentUsername\"}"))
                .andExpect(status().isOk());
    }

    /**
     * This method tests the behavior of the acceptStudent() method in the ProjectController class
     * when a staff user fails to accept a student for a project. It verifies that the HTTP status
     * code is BAD_REQUEST.
     *
     * @throws Exception if an error occurs during the test execution
     */
    @Test
    @WithMockUser(authorities = "staff")
    public void acceptStudent_WhenFailed_ReturnsBadRequest() throws Exception {
        User user = new User("user1", "", "User One", "staff");
        Project mockProject = new Project("Test Project", "Description", user);

        userRepository.save(user);
        projectRepository.save(mockProject);

        mockMvc.perform(post("/projects/" + mockProject.getId() + "/accept-student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"student_username\":\"studentUsername\"}"))
                .andExpect(status().isBadRequest());
    }

}
