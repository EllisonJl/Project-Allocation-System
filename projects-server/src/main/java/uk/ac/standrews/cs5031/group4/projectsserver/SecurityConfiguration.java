package uk.ac.standrews.cs5031.group4.projectsserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import uk.ac.standrews.cs5031.group4.projectsserver.filters.JwtAuthFilter;
import uk.ac.standrews.cs5031.group4.projectsserver.service.UserDetailsServiceImpl;

/**
 * Configuration class for Spring Security.
 */
@Configuration
@EnableWebSecurity // Enables Spring Security for the application.
// Enables securing API routes with the @Secured("role-name") annotation.
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    @Autowired // Automatically injects an instance of JwtAuthFilter.
    JwtAuthFilter jwtAuthFilter;/**
     * Configures the security filter chain.
     *
     * @param httpSecurity The HttpSecurity object to configure security settings.
     * @return The configured SecurityFilterChain.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(
                        (requests) -> requests
                                // Allow access to the H2 database console without logging in.
                                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                                // Permit access to the login endpoint without authentication.
                                .requestMatchers("/login").permitAll()
                                // Require authentication for all other requests.
                                .anyRequest().authenticated())
                // Set session management policy to stateless to avoid creating sessions.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Set the authentication provider to use.
                .authenticationProvider(authenticationProvider())
                // Add JwtAuthFilter before UsernamePasswordAuthenticationFilter in the filter chain.
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                // Set the exception handler for unauthorized access.
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));

        // Disable frame options for security reasons.
        httpSecurity.headers((headers) -> headers.frameOptions((opts) -> opts.disable()));

        // Disable CSRF protection.
        httpSecurity.csrf(csrf -> csrf.disable());

        return httpSecurity.build(); // Return the configured SecurityFilterChain.
    }

    /**
     * Creates and returns an instance of UserDetailsService.
     *
     * @return An instance of UserDetailsServiceImpl.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    /**
     * Creates and returns an instance of PasswordEncoder.
     *
     * @return An instance of BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates and returns an instance of AuthenticationProvider.
     *
     * @return An instance of DaoAuthenticationProvider.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Retrieves the AuthenticationManager from AuthenticationConfiguration.
     *
     * @param config The AuthenticationConfiguration object.
     * @return The AuthenticationManager.
     * @throws Exception If an error occurs while retrieving the AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
