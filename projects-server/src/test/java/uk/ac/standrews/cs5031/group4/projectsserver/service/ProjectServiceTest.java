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

    @Test
    void registerStudentInterest_WhenInterestAlreadyRegistered_ThrowsException() {
        // 创建和保存用户和项目
        User staff = new User("staff", "password", "Staff", "staff");
        User student = new User("student1", "password", "Student One", "student");
        Project project = new Project("Project", "description", staff);

        User savedStaff = userRepository.save(staff);
        User savedStudent = userRepository.save(student);
        Project savedProject = projectRepository.save(project);

        // 模拟已存在的兴趣记录
        InterestedIn existingInterest = new InterestedIn();
        existingInterest.setStudent(savedStudent);
        existingInterest.setProject(savedProject);
        InterestedIn savedInterest = interestedInRepository.save(existingInterest); // 假设这个方法保存兴趣并返回保存后的实体

        // 尝试为相同的学生和项目注册兴趣，应该抛出异常
        assertThrows(IllegalStateException.class, () -> {
            projectService.registerStudentInterest(savedStudent.getUsername(), savedProject.getId());
        }, "Student has already registered interest in this project");
    }
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
        String username = "nonExistingUser";

        assertThrows(IllegalStateException.class, () -> {
            projectService.proposeProject("Test Project", "Description", username);
        });
    }
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

    @Test
    public void registerStudentInterest_ProjectDoesNotExist_ThrowsException() {
        // 设置初始条件
        String studentUsername = "student1";
        User student = new User(studentUsername, "password", "Student One", "student");
        userRepository.save(student);

        // 尝试使用一个不存在的projectId
        int nonExistingProjectId = 999;

        // 验证执行registerStudentInterest时因为项目不存在而抛出异常
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            projectService.registerStudentInterest(studentUsername, nonExistingProjectId);
        });
        assertTrue(exception.getMessage().contains("Project does not exist"));
    }

    @Test
    public void acceptStudent_ProjectAlreadyAssigned_ThrowsException() {
        // 创建并保存项目及学生
        User staff = new User("staff", "password", "Staff", "staff");
        User student1 = new User("student1", "password", "Student One", "student");
        Project project = new Project("Project", "description", staff);

        userRepository.save(staff);
        userRepository.save(student1);
        project = projectRepository.save(project);
        project.setAssignedStudent(student1);
        project = projectRepository.save(project);

        // 再创建一个新的学生尝试分配同一个项目
        User student2 = new User("student2", "password", "Student Two", "student");
        userRepository.save(student2);

        // 验证执行acceptStudent时因为项目已分配而抛出异常
        Project finalProject = project;
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            projectService.acceptStudent(finalProject.getId(), student2.getUsername());
        });
        assertTrue(exception.getMessage().contains("Project is already assigned"));
    }


}
