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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    void getAvailableProjectsWhenProjectsAreAvailable() {
        User user = new User("jbloggs", "", "Joe Bloggs", "staff");
        Project project = new Project("Foobar",
                "This project aims to extend the Foobar project to add Gigatron features.", user, null);

        userRepository.save(user);
        projectRepository.save(project);

        ResponseEntity<List<Project>> responseEntity = projectController.getAvailableProjects();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.hasBody());
        assertTrue(responseEntity.getBody().contains(project));
    }

    @Test
    void getProjectByIdWhenProjectIsPresent() {
        User user = new User("jbloggs", "", "Joe Bloggs", "staff");
        Project expectedProject = new Project("Foobar",
                "This project aims to extend the Foobar project to add Gigatron features.",
                user, null);

        userRepository.save(user);
        projectRepository.save(expectedProject);

        ResponseEntity<Project> responseEntity = projectController.getProjectById(Integer.toString(expectedProject.getId()));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedProject, responseEntity.getBody());
    }

    @Test
    void getProjectByIdWhenProjectIsNotPresent() {
        int invalidId = 21;

        ResponseEntity<Project> responseEntity = projectController.getProjectById(Integer.toString(invalidId));
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    @WithMockUser(username = "username", authorities = "staff")
    public void proposeProject_ShouldReturnCreatedProject() throws Exception {
        User mockUser = new User("username", "", "Name", "staff");
        userRepository.save(mockUser);

        mockMvc.perform(post("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Test Project\", \"description\":\"Description\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Project"))
                .andExpect(jsonPath("$.description").value("Description"));
    }

    @Test
    @WithMockUser(authorities = "staff")
    public void acceptStudent_WhenSuccess_ReturnsOk() throws Exception {
        User user = new User("user1", "", "User One", "staff");
        Project mockProject = new Project("Test Project", "Description", user);

        userRepository.save(user);
        projectRepository.save(mockProject);

        User mockUser = new User("studentUsername", "", "Student", "student");
        userRepository.save(mockUser);

        projectService.registerStudentInterest(mockUser.getUsername(), mockProject.getId());

        mockMvc.perform(post("/projects/" + mockProject.getId() + "/accept-student")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"student_username\":\"studentUsername\"}"))
                .andExpect(status().isOk());
    }

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

    @Test
    @WithMockUser(username = "studentUser", authorities = "student")
    public void registerInterest_WhenProjectExists_AndUserIsStudent() throws Exception {
        // Set up test data
        User user = new User("studentUser", "", "Student Name", "student");
        Project project = new Project("Project Name", "Project Description", user);
        userRepository.save(user);
        projectRepository.save(project);

        // Perform test action
        mockMvc.perform(post("/register-interest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"project_id\":" + project.getId() + "}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "staffUser", authorities = "staff")
    public void getProposedProjects_WhenProjectsExist() throws Exception {
        // Set up test data
        User staffUser = new User("staffUser", "", "Staff Name", "staff");
        Project project1 = new Project("Project 1", "Description 1", staffUser);
        Project project2 = new Project("Project 2", "Description 2", staffUser);
        userRepository.save(staffUser);
        projectRepository.save(project1);
        projectRepository.save(project2);

        // Perform test action
        mockMvc.perform(get("/proposed-projects")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value("Project 1"))
                .andExpect(jsonPath("$.[1].name").value("Project 2"));
    }

    @Test
    public void registerInterest_WhenNotLoggedIn_ShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(post("/register-interest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"project_id\":1}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "studentUser", authorities = "student")
    public void registerInterest_WhenAlreadyRegistered_ShouldReturnForbidden() throws Exception {
        // 假设学生已经对项目ID为1的项目注册过兴趣
        mockMvc.perform(post("/register-interest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"project_id\":1}")) // 确保这设置反映在你的测试数据库中
                .andExpect(status().isForbidden()); // 期待403禁止状态
    }



    @Test
    public void getProposedProjects_WhenNotLoggedIn_ShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/proposed-projects"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "studentUser", authorities = "student")
    public void getProposedProjects_WhenUserIsStudent_ShouldReturnForbidden() throws Exception {
        mockMvc.perform(get("/proposed-projects"))
                .andExpect(status().isForbidden());
    }

}
