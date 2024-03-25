package uk.ac.standrews.cs5031.group4.projectsserver.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import uk.ac.standrews.cs5031.group4.projectsserver.service.JwtService;
import uk.ac.standrews.cs5031.group4.projectsserver.service.UserDetailsServiceImpl;

import java.io.IOException;

/**
 * Request filter that checks for the Authorization header, to ensure the user is logged in.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    // Service for handling JWT operations
    @Autowired
    private JwtService jwtService;

    // Service for loading user details
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    /**
     * Method to filter incoming requests and process JWT authentication.
     *
     * @param request  The incoming HTTP request.
     * @param response The HTTP response to be sent back.
     * @param filterChain The filter chain.
     * @throws ServletException If there's an error during the servlet process.
     * @throws IOException      If there's an I/O error.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Retrieve the value of the Authorization header from the incoming request
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // Check if the Authorization header is present and starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Extract the JWT token from the Authorization header
            token = authHeader.substring(7);
            // Extract the username from the JWT token
            username = jwtService.extractUsername(token);
        }

        // If the username is not null and there is no existing authentication in the SecurityContextHolder
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load the UserDetails for the user based on the username
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
            // Validate the JWT token
            if (jwtService.validateToken(token, userDetails)) {
                // Create an authentication token based on the UserDetails
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                // Set the details of the authentication token
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Set the authentication token in the SecurityContextHolder
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
