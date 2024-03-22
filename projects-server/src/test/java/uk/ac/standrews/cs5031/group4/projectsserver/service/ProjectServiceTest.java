package uk.ac.standrews.cs5031.group4.projectsserver.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.Project;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.User;
import uk.ac.standrews.cs5031.group4.projectsserver.repository.ProjectRepository;
import uk.ac.standrews.cs5031.group4.projectsserver.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void acceptStudent_ShouldAcceptStudentSuccessfully() {
        // Setup
        User user = new User("student1", "password", "Student One", "student");
        User staff = new User("staff", "password", "Staff", "staff");
        Project project = new Project("Project", "description", staff);

        userRepository.save(user);
        userRepository.save(staff);

        projectRepository.save(project);
        projectService.registerStudentInterest("student1", project.getId());

        // Execute
        projectService.acceptStudent(project.getId(), "student1");

        // reload the project from the repository
        project = projectRepository.findById(project.getId());

        // Verify
        assertNotNull(project.getAssignedStudent());
        assertEquals("student1", project.getAssignedStudent().getUsername());
        assertTrue(true);
    }

    @Test
    public void acceptStudent_ShouldAssignStudentToProject() {
        User staff = new User("staff", "password", "Staff", "staff");
        Project mockProject = new Project("Project", "description", staff);
        User mockStudent = new User("studentUsername", "password", "Student",
                "student");

        userRepository.save(mockStudent);
        userRepository.save(staff);
        projectRepository.save(mockProject);

        projectService.registerStudentInterest("studentUsername",
                mockProject.getId());

        projectService.acceptStudent(mockProject.getId(), "studentUsername");

        // reload the project from the repository
        mockProject = projectRepository.findById(mockProject.getId());

        assertEquals("studentUsername",
                mockProject.getAssignedStudent().getUsername());
    }

    @Test
    public void proposeProject_ShouldCreateAndSaveProjectWhenUserExists() {
        String username = "existingUser";
        User mockUser = new User(username, "password", "Test User", "staff");
        userRepository.save(mockUser);

        Project result = projectService.proposeProject("Test Project", "Description",
                username);

        assertNotNull(result);
        assertEquals("Test Project", result.getName());
        assertEquals("Description", result.getDescription());
    }

    @Test
    void proposeProject_ShouldThrowExceptionWhenUserNotFound() {
        // Given
        String username = "nonExistingUser";

        // When & Then
        assertThrows(IllegalStateException.class, () -> {
            projectService.proposeProject("Test Project", "Description", username);
        });
    }
}
