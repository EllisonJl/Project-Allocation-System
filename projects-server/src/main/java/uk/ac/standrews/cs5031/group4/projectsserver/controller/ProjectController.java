package uk.ac.standrews.cs5031.group4.projectsserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.Project;
import uk.ac.standrews.cs5031.group4.projectsserver.service.ProjectService;

import java.util.List;
import java.util.Map;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/register-interest")
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
    public ResponseEntity<List<Project>> getProposedProjects(Authentication auth) {
        String username = auth.getName();
        try {
            List<Project> projects = projectService.getProjectsProposedByStaff(username);
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
