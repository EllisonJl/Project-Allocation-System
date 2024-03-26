/**
 * Test class for the Project entity.
 * <p>
 * This class contains test methods to verify the behavior of the Project entity class.
 * It tests the constructors, getter and setter methods, and equality of Project instances.
 */
package uk.ac.standrews.cs5031.group4.projectsserver.entity;

import org.junit.jupiter.api.Test;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.Project;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.User;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {

    /**
     * Test method to create a Project instance with a valid constructor.
     * <p>
     * It creates a User instance, then creates a Project instance using the constructor and asserts that
     * the project is not null and its attributes are correctly set.
     */
    @Test
    public void createProject_ValidConstructor() {
        User user = new User("testUser", "password", "Test User", "staff");
        Project project = new Project("Project Name", "Project Description", user);

        assertNotNull(project);
        assertEquals("Project Name", project.getName());
        assertEquals("Project Description", project.getDescription());
        assertEquals(user, project.getProposedByStaff());
    }

    /**
     * Test method to set and get the name of a Project.
     * <p>
     * It creates a Project instance, sets a new name, and asserts that the name is correctly retrieved.
     */
    @Test
    public void setAndGetProjectName() {
        Project project = new Project();
        project.setName("New Project Name");

        assertEquals("New Project Name", project.getName());
    }

    /**
     * Test method to set and get the description of a Project.
     * <p>
     * It creates a Project instance, sets a new description, and asserts that the description is correctly retrieved.
     */
    @Test
    public void setAndGetProjectDescription() {
        Project project = new Project();
        project.setDescription("New Project Description");

        assertEquals("New Project Description", project.getDescription());
    }

    /**
     * Test method to set and get the staff proposing a Project.
     * <p>
     * It creates a Project instance, sets a staff user proposing the project, and asserts that the staff user
     * is correctly retrieved along with its username.
     */
    @Test
    public void setAndGetProposedByStaff() {
        Project project = new Project();
        User user = new User("staffUser", "password", "Staff User", "staff");
        project.setProposedByStaff(user);

        assertEquals(user, project.getProposedByStaff());
        assertEquals("staffUser", project.getProposedByStaff().getUsername());
    }

    /**
     * Test method to verify the equality of two Project instances.
     * <p>
     * It creates two Project instances with the same attributes and asserts that they are considered equal
     * based on your business logic.
     */
    @Test
    public void projectEquality() {
        User user = new User("user", "password", "User Name", "staff");
        Project project1 = new Project("Project", "Description", user);
        Project project2 = new Project("Project", "Description", user);

        assertNotSame(project1, project2); // Check they are not the same instance
        assertEquals(project1, project2); // But they are considered equal based on your business logic
    }

}
