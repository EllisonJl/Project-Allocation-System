package uk.ac.standrews.cs5031.group4.projectsserver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.InterestedIn;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.Project;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.User;
import uk.ac.standrews.cs5031.group4.projectsserver.repository.InterestedInRepository;
import uk.ac.standrews.cs5031.group4.projectsserver.repository.ProjectRepository;
import uk.ac.standrews.cs5031.group4.projectsserver.repository.UserRepository;
import uk.ac.standrews.cs5031.group4.projectsserver.service.ProjectService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

    @MockBean
    private ProjectRepository projectRepository;

    @MockBean
    private UserRepository userRepository;

    @Mock
    private InterestedInRepository interestedInRepository;

    @Test
    void acceptStudent_ShouldAcceptStudentSuccessfully() {
        // Setup
        User user = new User("student1", "Student One", "student");
        Project project = new Project();

        // 确保参数完全匹配
        when(projectRepository.findById(ArgumentMatchers.eq(String.valueOf(1)))).thenReturn(Optional.of(project));
        when(userRepository.findById(ArgumentMatchers.eq("student1"))).thenReturn(Optional.of(user));
        when(interestedInRepository.findByStudentUsernameAndProjectId(ArgumentMatchers.eq("student1"), ArgumentMatchers.eq(1)))
                .thenReturn(Optional.of(new InterestedIn()));

        // Execute
        projectService.acceptStudent(1, "student1");

        // Verify
        verify(projectRepository).save(project);
        assertNotNull(project.getAssignedStudent());
        assertEquals("student1", project.getAssignedStudent().getUsername());
    }

    @Test
    public void acceptStudent_ShouldAssignStudentToProject() {
        Project mockProject = new Project();
        User mockStudent = new User();
        mockStudent.setUsername("studentUsername");

        when(projectRepository.findById(anyString())).thenReturn(Optional.of(mockProject));
        when(interestedInRepository.findByStudentUsernameAndProjectId(anyString(), anyInt())).thenReturn(Optional.of(new InterestedIn()));
        when(userRepository.findById(anyString())).thenReturn(Optional.of(mockStudent));

        projectService.acceptStudent(1, "studentUsername");

        verify(projectRepository, times(1)).save(mockProject);
        assertEquals("studentUsername", mockProject.getAssignedStudent().getUsername());
    }

    @Test
    public void proposeProject_ShouldCreateAndSaveProjectWhenUserExists() {
        String username = "existingUser";
        User mockUser = new User(username, "Test User", "staff");
        when(userRepository.findById(username)).thenReturn(Optional.of(mockUser));

        Project mockProject = new Project("Test Project", "Description", mockUser);
        when(projectRepository.save(any(Project.class))).thenReturn(mockProject);

        Project result = projectService.proposeProject("Test Project", "Description", username);

        assertNotNull(result);
        assertEquals("Test Project", result.getName());
        assertEquals("Description", result.getDescription());
        assertEquals(mockProject.getId(), result.getId());

        verify(userRepository, times(1)).findById(username);
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    void proposeProject_ShouldThrowExceptionWhenUserNotFound() {
        // Given
        String username = "nonExistingUser";
        when(userRepository.findById(username)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(IllegalStateException.class, () -> {
            projectService.proposeProject("Test Project", "Description", username);
        });

        verify(userRepository, times(1)).findById(username);
        verify(projectRepository, never()).save(any(Project.class));
    }
}
