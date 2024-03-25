package uk.ac.standrews.cs5031.group4.projectsserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.Project;
import uk.ac.standrews.cs5031.group4.projectsserver.repository.ProjectRepository;
import uk.ac.standrews.cs5031.group4.projectsserver.service.ProjectService;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
// prevents CORS errors when accessing the API
@CrossOrigin(origins = "*")
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/available-projects")
    public ResponseEntity<List<Project>> getAvailableProjects() {
        // fetches all the projects which hasn't been assigned to anyone.
        List<Project> availableProjects = projectRepository.findByAssignedStudentIsNull();
        //List<Project> availableProjects = projectRepository.findByAssignedStudentIsEmptyUser();
        return ResponseEntity.ok(availableProjects);
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable String id) {
        Optional<Project> requiredProject = projectRepository.findById(id);

        if (requiredProject.isPresent()) {
            return ResponseEntity.ok(requiredProject.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register-interest")
    @Secured("student")
    public ResponseEntity<?> registerInterest(@RequestBody Map<String, Object> payload, Authentication auth) {
        // Get the username of the currently logged in user from the SecurityContext.
        String username = auth.getName();
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
    @Secured("staff")
    public ResponseEntity<List<Project>> getProposedProjects(Authentication auth) {
        String username = auth.getName();
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

    @PostMapping("projects/{id}/accept-student")
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
