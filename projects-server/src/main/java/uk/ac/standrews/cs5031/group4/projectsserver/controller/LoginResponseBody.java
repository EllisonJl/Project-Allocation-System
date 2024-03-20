package uk.ac.standrews.cs5031.group4.projectsserver.controller;

public class LoginResponseBody {
    private String token;
    private String name;
    private String role;

    public LoginResponseBody(String token, String name, String role) {
        this.token = token;
        this.name = name;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
