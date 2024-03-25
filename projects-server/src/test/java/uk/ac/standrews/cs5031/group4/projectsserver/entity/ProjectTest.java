package uk.ac.standrews.cs5031.group4.projectsserver.entity;

import org.junit.jupiter.api.Test;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.Project;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.User;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {

    @Test
    public void createProject_ValidConstructor() {
        User user = new User("testUser", "password", "Test User", "staff");
        Project project = new Project("Project Name", "Project Description", user);

        assertNotNull(project);
        assertEquals("Project Name", project.getName());
        assertEquals("Project Description", project.getDescription());
        assertEquals(user, project.getProposedByStaff());
    }

    @Test
    public void setAndGetProjectName() {
        Project project = new Project();
        project.setName("New Project Name");

        assertEquals("New Project Name", project.getName());
    }

    @Test
    public void setAndGetProjectDescription() {
        Project project = new Project();
        project.setDescription("New Project Description");

        assertEquals("New Project Description", project.getDescription());
    }

    @Test
    public void setAndGetProposedByStaff() {
        Project project = new Project();
        User user = new User("staffUser", "password", "Staff User", "staff");
        project.setProposedByStaff(user);

        assertEquals(user, project.getProposedByStaff());
        assertEquals("staffUser", project.getProposedByStaff().getUsername());
    }

    @Test
    public void projectEquality() {
        User user = new User("user", "password", "User Name", "staff");
        Project project1 = new Project("Project", "Description", user);
        Project project2 = new Project("Project", "Description", user);

        assertNotSame(project1, project2); // Check they are not the same instance
        assertEquals(project1, project2); // But they are considered equal based on your business logic
    }


}
