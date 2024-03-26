/**
 * Test class for InterestedIn entity.
 * <p>
 * This class contains test methods to verify the functionality of the InterestedIn entity class.
 * It tests the constructor to ensure that it correctly initializes the fields.
 */
package uk.ac.standrews.cs5031.group4.projectsserver.entity;

import org.junit.jupiter.api.Test;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.InterestedIn;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.Project;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.User;

import static org.junit.jupiter.api.Assertions.*;

public class InterestedInTest {

    /**
     * Test method to verify that the constructor correctly initializes fields.
     * <p>
     * It creates mock instances of User and Project, uses the constructor to create an InterestedIn instance,
     * and then asserts that the InterestedIn instance's student and project match the ones provided to the constructor.
     */
    @Test
    public void constructorCorrectlyInitializesFields() {
        // Mock or create real instances of User and Project
        User mockStudent = new User(); // Adjust with actual constructor if needed
        Project mockProject = new Project(); // Adjust with actual constructor if needed

        // Use the constructor to create an InterestedIn instance
        InterestedIn interestedIn = new InterestedIn(mockStudent, mockProject);

        // Assert that the InterestedIn instance's student and project match the ones used for construction
        assertEquals(mockStudent, interestedIn.getStudent(), "The InterestedIn student should match the one provided to the constructor");
        assertEquals(mockProject, interestedIn.getProject(), "The InterestedIn project should match the one provided to the constructor");
    }
}
