package uk.ac.standrews.cs5031.group4.projectsserver.auth;

public class LoginResponseBody {
    private String token;

    public LoginResponseBody(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
