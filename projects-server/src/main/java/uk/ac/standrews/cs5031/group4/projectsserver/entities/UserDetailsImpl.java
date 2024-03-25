package uk.ac.standrews.cs5031.group4.projectsserver.entities;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Concrete implementation of the UserDetails interface. This is used for
 * retrieving user details in the JwtAuthFilter.
 */
public class UserDetailsImpl extends User implements UserDetails {
    // Username of the user
    private String username;

    // Password of the user
    private String password;

    // Collection of authorities (roles) granted to the user
    Collection<? extends GrantedAuthority> authorities;

    /**
     * Constructor to create UserDetailsImpl from a User object.
     *
     * @param user The User object.
     */
    public UserDetailsImpl(User user) {
        username = user.getUsername();
        password = user.getPassword();

        // Grant the "student" or "staff" role
        GrantedAuthority role = new SimpleGrantedAuthority(user.getRole());
        ArrayList<GrantedAuthority> auths = new ArrayList<>();
        auths.add(role);
        authorities = auths;
    }

    /**
     * Get the username of the user.
     *
     * @return The username.
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Get the password of the user.
     *
     * @return The password.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Get the collection of authorities (roles) granted to the user.
     *
     * @return The collection of authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Check if the user's account is non-expired.
     *
     * @return Always returns true.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Check if the user's account is non-locked.
     *
     * @return Always returns true.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Check if the user's credentials are non-expired.
     *
     * @return Always returns true.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Check if the user's account is enabled.
     *
     * @return Always returns true.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
