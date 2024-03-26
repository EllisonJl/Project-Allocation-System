/**
 * Test class for Project equals method.
 * <p>
 * This class contains test methods to verify the behavior of the equals method in the Project entity class.
 * It tests various scenarios such as equality with self, null, different class, different attributes,
 * and same attributes but different objects.
 */
package uk.ac.standrews.cs5031.group4.projectsserver.entity;

import org.junit.jupiter.api.Test;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.Project;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.User;

import static org.junit.jupiter.api.Assertions.*;

class ProjectEqualsTest {

    /**
     * Helper method to create a User instance.
     *
     * @param username the username of the user
     * @param name     the name of the user
     * @param role     the role of the user
     * @return a User instance with the provided attributes
     */
    private User createUser(String username, String name, String role) {
        return new User(username, "hashedPassword", name, role);
    }

    /**
     * Test method to verify equality of a Project instance with itself.
     * <p>
     * It creates a Project instance and asserts that it is equal to itself.
     */
    @Test
    public void testEqualsWithSelf() {
        User staff = createUser("staffName", "Staff User", "staff");
        Project project = new Project("Project1", "Description1", staff);
        assertTrue(project.equals(project), "A project should be equal to itself.");
    }

    /**
     * Test method to verify equality of a Project instance with null.
     * <p>
     * It creates a Project instance and asserts that it is not equal to null.
     */
    @Test
    public void testEqualsWithNull() {
        User staff = createUser("staffName", "Staff User", "staff");
        Project project = new Project("Project1", "Description1", staff);
        assertFalse(project.equals(null), "A project should not be equal to null.");
    }

    /**
     * Test method to verify equality of a Project instance with an object of a different class.
     * <p>
     * It creates a Project instance and asserts that it is not equal to an object of a different class.
     */
    @Test
    public void testEqualsWithDifferentClass() {
        User staff = createUser("staffName", "Staff User", "staff");
        Project project = new Project("Project1", "Description1", staff);
        Object other = new Object();
        assertFalse(project.equals(other), "A project should not be equal to an object of a different type.");
    }

    /**
     * Test method to verify equality of two Project instances with different attributes.
     * <p>
     * It creates two Project instances with different attributes and asserts that they are not equal.
     */
    @Test
    public void testEqualsWithDifferentAttributes() {
        User staff1 = createUser("staffName1", "Staff User 1", "staff");
        User staff2 = createUser("staffName2", "Staff User 2", "staff");
        Project project1 = new Project("Project1", "Description1", staff1);
        Project project2 = new Project("Project2", "Description2", staff2);
        assertFalse(project1.equals(project2), "Projects with different names, descriptions, or proposing staff should not be equal.");
    }

    /**
     * Test method to verify equality of two Project instances with the same attributes but different objects.
     * <p>
     * It creates two Project instances with the same attributes but different objects and asserts that they are equal.
     */
    @Test
    public void testEqualsWithSameAttributesDifferentObjects() {
        User staff = createUser("staffName", "Staff User", "staff");
        Project project1 = new Project("Project1", "Description1", staff);
        Project project2 = new Project("Project1", "Description1", staff);
        assertTrue(project1.equals(project2), "Projects with the same name, description, and proposing staff should be considered equal.");
    }
}
