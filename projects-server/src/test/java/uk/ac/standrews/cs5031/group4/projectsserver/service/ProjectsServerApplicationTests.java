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

    public ProjectsServerApplicationTests() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHelloWorld() {
        assertEquals("Hello world!", projectsServerApplication.helloWorld());
    }

    @Test
    public void testSecret() {
        assertEquals("Top secret!", projectsServerApplication.secret());
    }

    @Test
    public void testUsers() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(new User()));
        List<User> users = projectsServerApplication.users();
        assertEquals(1, users.size());
        verify(userRepository).findAll();
    }

    @Test
    public void testRole() {
        Authentication auth = new UsernamePasswordAuthenticationToken("user", "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        List<String> roles = projectsServerApplication.role(auth);
        assertEquals(1, roles.size());
        assertEquals("ROLE_USER", roles.get(0));
    }
}
