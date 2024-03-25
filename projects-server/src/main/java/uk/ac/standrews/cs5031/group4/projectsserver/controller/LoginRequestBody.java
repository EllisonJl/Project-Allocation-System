package uk.ac.standrews.cs5031.group4.projectsserver.controller;

/**
 * Request body class for login requests.
 * Contains username and password fields.
 */
public class LoginRequestBody {
    // Username provided in the login request
    private String username;

    // Password provided in the login request
    private String password;

    /**
     * Constructor to initialize LoginRequestBody with username and password.
     *
     * @param username The username provided in the login request.
     * @param password The password provided in the login request.
     */
    public LoginRequestBody(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Get the username from the login request body.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the password from the login request body.
     *
     * @return The password.
     */
    public String getPassword() {
        return password;
    }
}
