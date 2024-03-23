package uk.ac.standrews.cs5031.group4.projectsserver.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.standrews.cs5031.group4.projectsserver.entities.User;
import uk.ac.standrews.cs5031.group4.projectsserver.repository.UserRepository;

@RestController
// prevents CORS errors when accessing the API
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    /**
     * API route to fetch the details of the currently logged-in user.
     */
    @GetMapping("/me")
    public ResponseEntity<User> getLoggedInUserDetails(Authentication auth) {
        String username = auth.getName();
        Optional<User> user = userRepository.findById(username);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            // We know the user is logged in, so we expect them to exist in the database...
            // If not, an unknown error has occurred.
            return ResponseEntity.internalServerError().build();
        }
    }
}
