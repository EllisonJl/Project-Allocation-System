/**
 * This class contains unit tests for the functionality provided by the ProjectsServerApplication class.
 */
package uk.ac.standrews.cs5031.group4.projectsserver.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.List;

import uk.ac.standrews.cs5031.group4.projectsserver.ProjectsServerApplication;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.User;
import uk.ac.standrews.cs5031.group4.projectsserver.repository.ProjectRepository;
import uk.ac.standrews.cs5031.group4.projectsserver.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProjectsServerApplicationTests {

    @InjectMocks
    private ProjectsServerApplication projectsServerApplication;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectRepository projectRepository;

    /**
     * Initializes mock objects before each test method execution.
     */
    public ProjectsServerApplicationTests() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test case to verify the behavior of the helloWorld() method.
     */
    @Test
    public void testHelloWorld() {
        assertEquals("Hello world!", projectsServerApplication.helloWorld());
    }

    /**
     * Test case to verify the behavior of the secret() method.
     */
    @Test
    public void testSecret() {
        assertEquals("Top secret!", projectsServerApplication.secret());
    }

    /**
     * Test case to verify the behavior of the users() method.
     */
    @Test
    public void testUsers() {
        // Given
        when(userRepository.findAll()).thenReturn(Collections.singletonList(new User()));

        // When
        List<User> users = projectsServerApplication.users();

        // Then
        assertEquals(1, users.size());
        verify(userRepository).findAll();
    }

    /**
     * Test case to verify the behavior of the role() method.
     */
    @Test
    public void testRole() {
        // Given
        Authentication auth = new UsernamePasswordAuthenticationToken("user", "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        // When
        List<String> roles = projectsServerApplication.role(auth);

        // Then
        assertEquals(1, roles.size());
        assertEquals("ROLE_USER", roles.get(0));
    }
}
