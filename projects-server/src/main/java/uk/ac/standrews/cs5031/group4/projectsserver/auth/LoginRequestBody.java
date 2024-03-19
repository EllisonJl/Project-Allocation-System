package uk.ac.standrews.cs5031.group4.projectsserver.auth;

public class LoginRequestBody {
    private String username;
    private String password;

    public LoginRequestBody(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
