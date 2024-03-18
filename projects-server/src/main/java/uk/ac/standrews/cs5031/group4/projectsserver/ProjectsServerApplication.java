package uk.ac.standrews.cs5031.group4.projectsserver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.standrews.cs5031.group4.projectsserver.entities.User;
import uk.ac.standrews.cs5031.group4.projectsserver.repository.ProjectRepository;
import uk.ac.standrews.cs5031.group4.projectsserver.repository.UserRepository;

@SpringBootApplication
@RestController
public class ProjectsServerApplication {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public static void main(String[] args) {
        SpringApplication.run(ProjectsServerApplication.class, args);
    }

    @GetMapping("/")
    public String helloWorld() {
        return "Hello world!";
    }

    /**
     * A sample request that gets data from the user repository.
     * TODO: remove this once we write the actual queries we need.
     */
    @GetMapping("/users")
    public List<User> users() {
        List<User> result = new ArrayList<>();
        userRepository.findAll().forEach(result::add);
        return result;
    }
}
