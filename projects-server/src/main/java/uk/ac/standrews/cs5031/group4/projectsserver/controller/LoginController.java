package uk.ac.standrews.cs5031.group4.projectsserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import uk.ac.standrews.cs5031.group4.projectsserver.entities.User;
import uk.ac.standrews.cs5031.group4.projectsserver.repository.UserRepository;
import uk.ac.standrews.cs5031.group4.projectsserver.service.JwtService;

@RestController
// prevents CORS errors when accessing the API
@CrossOrigin(origins = "*")
public class LoginController {
    /**
     * Authentication manager used for checking the username and password.
     * This bean is automatically injected by Spring.
     */
    @Autowired
    private AuthenticationManager authManager;

    /**
     * Repository for accessing user data.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * The JWT service used to generate an auth token for the user.
     */
    @Autowired
    private JwtService jwtService;

    /**
     * Endpoint for user authentication.
     *
     * @param requestBody The request body containing username and password.
     * @return ResponseEntity with login response body and status.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseBody> authenticate(@RequestBody LoginRequestBody requestBody) {
        // Authenticate the user by the username and password in the request body
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestBody.getUsername(), requestBody.getPassword()));

        if (auth.isAuthenticated()) {
            // Get the User object for the user who just authenticated
            User user = userRepository.findById(auth.getName()).orElseThrow(
                    () -> new IllegalStateException("The user must exist since we authenticated successfully."));

            // Generate JWT token for the authenticated user
            String token = jwtService.generateToken(requestBody.getUsername());

            // Create login response body with token, user name, and role
            LoginResponseBody response = new LoginResponseBody(token, user.getName(), user.getRole());

            // Return OK response with login response body
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // Return UNAUTHORIZED response if authentication failed
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
