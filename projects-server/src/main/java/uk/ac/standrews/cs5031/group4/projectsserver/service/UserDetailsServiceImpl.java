package uk.ac.standrews.cs5031.group4.projectsserver.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import uk.ac.standrews.cs5031.group4.projectsserver.entities.UserDetailsImpl;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.User;
import uk.ac.standrews.cs5031.group4.projectsserver.repository.UserRepository;

/**
 * Service for loading user details for authentication.
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Username is invalid.");
        }

        return new UserDetailsImpl(user.get());
    }
}
