package uk.ac.standrews.cs5031.group4.projectsserver.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import uk.ac.standrews.cs5031.group4.projectsserver.entities.User;

public class UserDetailsImpl extends User implements UserDetails {
    private String username;
    private String password;
    Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(User user) {
        username = user.getUsername();
        password = user.getPassword();
        authorities = new ArrayList<>();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
