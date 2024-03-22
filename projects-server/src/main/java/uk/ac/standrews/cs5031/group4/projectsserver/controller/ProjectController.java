package uk.ac.standrews.cs5031.group4.projectsserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.Project;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.User;
import uk.ac.standrews.cs5031.group4.projectsserver.repository.ProjectRepository;
import uk.ac.standrews.cs5031.group4.projectsserver.repository.UserRepository;
import uk.ac.standrews.cs5031.group4.projectsserver.service.ProjectService;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api") // Base path for all API endpoints in this controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/register-interest")
    public ResponseEntity<?> registerInterest(@RequestBody Map<String, Object> payload, Principal principal) {
        // Get the username of the currently logged in user from the SecurityContext.
//        String username = principal.getName();
        String username = "mm20";
        // Get project_id from request body
        int projectId = (Integer) payload.get("project_id");
        try {
            // Calling Service layer methods to handle student interest in the project
            projectService.registerStudentInterest(username, projectId);
            // If there are no exceptions, return 200 OK
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            // Returns 403 Forbidden if the item has already been assigned.
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            // For other exceptions, return 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/proposed-projects")
    public ResponseEntity<List<Project>> getProposedProjects(Principal principal) {
        //String username = principal.getName();
        String username = "jbloggs";
        try {
            List<Project> projects = projectService.getProjectsProposedByStaff(username);
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PostMapping("/projects")
    public ResponseEntity<?> proposeProject(@RequestBody Map<String, String> payload, Principal principal) {
        String name = payload.get("name");
        String description = payload.get("description");
        String username = principal.getName();

        try {
            Project project = projectService.proposeProject(name, description, username);
            return ResponseEntity.status(HttpStatus.CREATED).body(project);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/{id}/accept-student")
    public ResponseEntity<?> acceptStudent(@PathVariable int id, @RequestBody Map<String, String> payload) {
        try {
            String studentUsername = payload.get("student_username");
            projectService.acceptStudent(id, studentUsername);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
