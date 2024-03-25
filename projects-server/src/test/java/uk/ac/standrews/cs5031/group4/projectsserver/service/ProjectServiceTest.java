package uk.ac.standrews.cs5031.group4.projectsserver.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.InterestedIn;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.Project;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.User;
import uk.ac.standrews.cs5031.group4.projectsserver.repository.InterestedInRepository;
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
    @Autowired
    private InterestedInRepository interestedInRepository;

    /**
     * This method tests the behavior of the registerStudentInterest() method in the ProjectService class
     * when the student interest is already registered for a project. It verifies that an IllegalStateException
     * is thrown with an appropriate error message.
     */
    @Test
    void registerStudentInterest_WhenInterestAlreadyRegistered_ThrowsException() {
        User staff = new User("staff", "password", "Staff", "staff");
        User student = new User("student1", "password", "Student One", "student");
        Project project = new Project("Project", "description", staff);

        User savedStaff = userRepository.save(staff);
        User savedStudent = userRepository.save(student);
        Project savedProject = projectRepository.save(project);

        InterestedIn existingInterest = new InterestedIn();
        existingInterest.setStudent(savedStudent);
        existingInterest.setProject(savedProject);
        InterestedIn savedInterest = interestedInRepository.save(existingInterest);

        assertThrows(IllegalStateException.class, () -> {
            projectService.registerStudentInterest(savedStudent.getUsername(), savedProject.getId());
        }, "Student has already registered interest in this project");
    }

    /**
     * This method tests the behavior of the acceptStudent() method in the ProjectService class
     * when a student is successfully accepted for a project. It verifies that the student
     * is assigned to the project.
     */
    @Test
    void acceptStudent_ShouldAcceptStudentSuccessfully() {
        User user = new User("student1", "password", "Student One", "student");
        User staff = new User("staff", "password", "Staff", "staff");
        Project project = new Project("Project", "description", staff);

        userRepository.save(user);
        userRepository.save(staff);
        projectRepository.save(project);
        projectService.registerStudentInterest("student1", project.getId());

        projectService.acceptStudent(project.getId(), "student1");

        project = projectRepository.findById(project.getId());
        assertNotNull(project.getAssignedStudent());
        assertEquals("student1", project.getAssignedStudent().getUsername());
        assertTrue(true);
    }

    /**
     * This method tests the behavior of the acceptStudent() method in the ProjectService class
     * when a student is accepted for a project successfully. It verifies that the student
     * is assigned to the project.
     */
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

        mockProject = projectRepository.findById(mockProject.getId());
        assertEquals("studentUsername",
                mockProject.getAssignedStudent().getUsername());
    }

    /**
     * This method tests the behavior of the proposeProject() method in the ProjectService class
     * when a user exists and proposes a project. It verifies that the project is created and saved.
     */
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

    /**
     * This method tests the behavior of the proposeProject() method in the ProjectService class
     * when a user does not exist. It verifies that an IllegalStateException is thrown.
     */
    @Test
    void proposeProject_ShouldThrowExceptionWhenUserNotFound() {
        String username = "nonExistingUser";

        assertThrows(IllegalStateException.class, () -> {
            projectService.proposeProject("Test Project", "Description", username);
        });
    }

    /**
     * This method tests the behavior of the acceptStudent() method in the ProjectService class
     * when a project already has a student assigned to it. It verifies that an IllegalStateException
     * is thrown with an appropriate error message.
     */
    @Test
    void acceptStudent_ShouldThrowExceptionWhenProjectAlreadyAssigned() {
        User staff = new User("staff", "password", "Staff", "staff");
        User student1 = new User("student1", "password", "Student One", "student");
        User student2 = new User("student2", "password", "Student Two", "student");
        Project project = new Project("Project", "description", staff);

        userRepository.save(staff);
        userRepository.save(student1);
        userRepository.save(student2);
        project.setAssignedStudent(student1);
        projectRepository.save(project);

        assertThrows(IllegalStateException.class, () -> {
            projectService.acceptStudent(project.getId(), "student2");
        });
    }

    /**
     * This method tests the behavior of the registerStudentInterest() method in the ProjectService class
     * when the project does not exist. It verifies that an IllegalStateException is thrown
     * with an appropriate error message.
     */
    @Test
    public void registerStudentInterest_ProjectDoesNotExist_ThrowsException() {
        String studentUsername = "student1";
        User student = new User(studentUsername, "password", "Student One", "student");
        userRepository.save(student);

        int nonExistingProjectId = 999;

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            projectService.registerStudentInterest(studentUsername, nonExistingProjectId);
        });
        assertTrue(exception.getMessage().contains("Project does not exist"));
    }

    /**
     * This method tests the behavior of the acceptStudent() method in the ProjectService class
     * when a project already has a student assigned to it. It verifies that an IllegalStateException
     * is thrown with an appropriate error message.
     */
    @Test
    public void acceptStudent_ProjectAlreadyAssigned_ThrowsException() {
        User staff = new User("staff", "password", "Staff", "staff");
        User student1 = new User("student1", "password", "Student One", "student");
        Project project = new Project("Project", "description", staff);

        userRepository.save(staff);
        userRepository.save(student1);
        project = projectRepository.save(project);
        project.setAssignedStudent(student1);
        project = projectRepository.save(project);

        User student2 = new User("student2", "password", "Student Two", "student");
        userRepository.save(student2);

        Project finalProject = project;
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            projectService.acceptStudent(finalProject.getId(), student2.getUsername());
        });
        assertTrue(exception.getMessage().contains("Project is already assigned"));
    }


}
