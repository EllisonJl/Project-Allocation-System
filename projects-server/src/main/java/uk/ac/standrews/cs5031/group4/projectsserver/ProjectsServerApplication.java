package uk.ac.standrews.cs5031.group4.projectsserver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.standrews.cs5031.group4.projectsserver.entities.User;
import uk.ac.standrews.cs5031.group4.projectsserver.repository.ProjectRepository;
import uk.ac.standrews.cs5031.group4.projectsserver.repository.UserRepository;

/**
 * Main entry point for the Projects Server application.
 */
@SpringBootApplication // Indicates that this class is the entry point for Spring Boot application.
@RestController // Indicates that this class contains RESTful endpoints.
public class ProjectsServerApplication {

    @Autowired // Automatically injects an instance of UserRepository.
    private UserRepository userRepository;

    @Autowired // Automatically injects an instance of ProjectRepository.
    private ProjectRepository projectRepository;

    /**
     * The main method to start the Spring Boot application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(ProjectsServerApplication.class, args); // Launches the Spring Boot application.
    }

    /**
     * Simple endpoint to return "Hello world!".
     *
     * @return A greeting message.
     */
    @GetMapping("/") // Maps HTTP GET requests for the root path ("/") to this method.
    public String helloWorld() {
        return "Hello world!"; // Returns a greeting message.
    }

    /**
     * A sample endpoint returning "Top secret!".
     * This is just for demonstration purposes.
     *
     * @return A top secret message.
     */
    @GetMapping("/secret") // Maps HTTP GET requests for the "/secret" path to this method.
    public String secret() {
        return "Top secret!"; // Returns a top secret message.
    }

    /**
     * A sample request that retrieves data from the user repository.
     * TODO: Remove this once the actual queries are implemented.
     *
     * @return A list of users.
     */
    @GetMapping("/users") // Maps HTTP GET requests for the "/users" path to this method.
    public List<User> users() {
        List<User> result = new ArrayList<>(); // Initializes a list to store user objects.
        userRepository.findAll().forEach(result::add); // Retrieves all users from the repository and adds them to the list.
        return result; // Returns the list of users.
    }

    /**
     * Endpoint to retrieve the roles of the authenticated user.
     *
     * @param auth The authentication object containing user details.
     * @return A list of roles assigned to the user.
     */
    @GetMapping("/role") // Maps HTTP GET requests for the "/role" path to this method.
    public List<String> role(Authentication auth) {
        List<String> result = new ArrayList<>(); // Initializes a list to store user roles.
        auth.getAuthorities().forEach(a -> result.add(a.toString())); // Retrieves roles from authentication object and adds them to the list.
        return result; // Returns the list of roles.
    }
}
