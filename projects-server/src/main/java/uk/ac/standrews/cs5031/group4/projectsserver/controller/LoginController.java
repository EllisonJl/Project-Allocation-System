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

import uk.ac.standrews.cs5031.group4.projectsserver.service.JwtService;

@RestController
public class LoginController {
    /**
     * Authentication manager used for checking the username and password.
     */
    @Autowired
    private AuthenticationManager authManager;

    /**
     * The JWT service used to generate an auth token for the user.
     */
    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseBody> authenticate(@RequestBody LoginRequestBody requestBody) {
        // authenticate the user by the username and password in the request body
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestBody.getUsername(), requestBody.getPassword()));

        if (auth.isAuthenticated()) {
            LoginResponseBody response = new LoginResponseBody(jwtService.generateToken(requestBody.getUsername()));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
