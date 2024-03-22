package uk.ac.standrews.cs5031.group4.projectsserver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import uk.ac.standrews.cs5031.group4.projectsserver.controller.ProjectController;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.Project;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.User;
import uk.ac.standrews.cs5031.group4.projectsserver.repository.UserRepository;
import uk.ac.standrews.cs5031.group4.projectsserver.service.ProjectService;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService; // Mock ProjectService


    @Test
    public void proposeProject_ShouldReturnCreatedProject() throws Exception {
        Project mockProject = new Project("Test Project", "Description", new User("user1", "User One", "staff"));
        given(projectService.proposeProject("Test Project", "Description", "user1")).willReturn(mockProject);

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Project\", \"description\":\"Description\"}")
                        .principal(() -> "user1")) // Mock principal
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Project"))
                .andExpect(jsonPath("$.description").value("Description"));
    }

    @Test
    public void acceptStudent_WhenSuccess_ReturnsOk() throws Exception {
        doNothing().when(projectService).acceptStudent(anyInt(), anyString());

        mockMvc.perform(post("/api/projects/1/accept-student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"student_username\":\"studentUsername\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void acceptStudent_WhenFailed_ReturnsBadRequest() throws Exception {
        doThrow(new IllegalStateException("Student hasn't registered their interest for this project"))
                .when(projectService).acceptStudent(anyInt(), anyString());

        mockMvc.perform(post("/api/projects/1/accept-student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"student_username\":\"studentUsername\"}"))
                .andExpect(status().isBadRequest());
    }
}
