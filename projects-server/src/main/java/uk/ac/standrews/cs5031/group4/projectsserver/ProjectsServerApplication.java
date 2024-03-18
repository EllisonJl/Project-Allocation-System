package uk.ac.standrews.cs5031.group4.projectsserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ProjectsServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectsServerApplication.class, args);
    }

    @GetMapping("/")
    public String helloWorld() {
        return "Hello world!";
    }
}
