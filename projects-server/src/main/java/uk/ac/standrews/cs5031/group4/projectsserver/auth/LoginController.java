package uk.ac.standrews.cs5031.group4.projectsserver.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public LoginResponseBody authenticate(@RequestBody LoginRequestBody requestBody) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestBody.getUsername(), requestBody.getPassword()));

        if (auth.isAuthenticated()) {
            return new LoginResponseBody(jwtService.generateToken(requestBody.getUsername()));
        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }
}
