package uk.ac.standrews.cs5031.group4.projectsserver.controller;

/**
 * Response body class for login requests.
 * Contains token, name, and role fields.
 */
public class LoginResponseBody {
    // Authentication token generated for the user
    private String token;

    // Name of the authenticated user
    private String name;

    // Role of the authenticated user
    private String role;

    /**
     * Constructor to initialize LoginResponseBody with token, name, and role.
     *
     * @param token The authentication token generated for the user.
     * @param name The name of the authenticated user.
     * @param role The role of the authenticated user.
     */
    public LoginResponseBody(String token, String name, String role) {
        this.token = token;
        this.name = name;
        this.role = role;
    }

    /**
     * Get the authentication token from the response body.
     *
     * @return The authentication token.
     */
    public String getToken() {
        return token;
    }

    /**
     * Get the name of the authenticated user from the response body.
     *
     * @return The name of the authenticated user.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the role of the authenticated user from the response body.
     *
     * @return The role of the authenticated user.
     */
    public String getRole() {
        return role;
    }
}
