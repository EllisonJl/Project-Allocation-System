package uk.ac.standrews.cs5031.group4.projectsserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.Project;
import uk.ac.standrews.cs5031.group4.projectsserver.repository.ProjectRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ProjectController {

    @Autowired
    ProjectRepository projectRepository;

    @GetMapping("/available-projects")
    public ResponseEntity<List<Project>> getAvailableProjects(){
        List<Project> availableProjects = new ArrayList<>();

        if (projectRepository.findByAssignedStudentIsNull().isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            availableProjects = projectRepository.findByAssignedStudentIsNull(); //fetches all the projects which hasn't been assigned to anyone.
            return ResponseEntity.ok(availableProjects);
        }
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable String id){
        Optional<Project> requiredProject = projectRepository.findById(id);

        if (requiredProject.isPresent()){
            return ResponseEntity.ok(requiredProject.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
