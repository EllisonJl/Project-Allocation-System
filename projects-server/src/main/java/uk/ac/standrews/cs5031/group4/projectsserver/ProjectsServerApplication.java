package uk.ac.standrews.cs5031.group4.projectsserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * Main entry point for the Projects Server application.
 */
@SpringBootApplication // Indicates that this class is the entry point for Spring Boot application.
@RestController // Indicates that this class contains RESTful endpoints.
// prevents CORS errors when accessing the API
@CrossOrigin(origins = "*")
public class ProjectsServerApplication {
    /**
     * The main method to start the Spring Boot application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(ProjectsServerApplication.class, args); // Launches the Spring Boot application.
    }
}
