package uk.ac.standrews.cs5031.group4.projectsserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(
                (requests) -> requests
                        // allow access to the H2 database console without logging in
                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                        // allow access to all routes without auth
                        // NOTE: this is enabled temporarily before login is implemented.
                        .anyRequest().permitAll());

        httpSecurity.headers((headers) -> headers.frameOptions((opts) -> opts.disable()));

        httpSecurity.csrf(csrf -> csrf.disable());

        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("admin").password("password").build();

        return new InMemoryUserDetailsManager(user);
    }
}
