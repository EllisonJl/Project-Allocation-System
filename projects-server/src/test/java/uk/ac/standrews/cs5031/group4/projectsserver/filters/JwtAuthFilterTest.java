package uk.ac.standrews.cs5031.group4.projectsserver.filters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import uk.ac.standrews.cs5031.group4.projectsserver.repository.UserRepository;
import uk.ac.standrews.cs5031.group4.projectsserver.service.JwtService;
import uk.ac.standrews.cs5031.group4.projectsserver.service.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JwtAuthFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @InjectMocks
    private JwtAuthFilter jwtAuthFilter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext(); // Ensure security context is clean before each test
    }

    @Test
    public void testDoFilterInternal_ValidToken_SetsAuthentication() throws Exception {
        String username = "testUser";
        String token = "validToken";
        UserDetails userDetails = new User(username, "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtService.extractUsername(token)).thenReturn(username);
        when(jwtService.validateToken(token, userDetails)).thenReturn(true);
        when(userDetailsServiceImpl.loadUserByUsername(username)).thenReturn(userDetails);

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response); // Verify that filter chain is continued
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(username, SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Test
    public void testDoFilterInternal_InvalidToken_DoesNotSetAuthentication() throws Exception {
        String token = "invalidToken";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtService.extractUsername(token)).thenReturn(null); // Simulate invalid token

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response); // Verify that filter chain is continued
        assertNull(SecurityContextHolder.getContext().getAuthentication()); // Authentication should not be set
    }

    @Test
    public void loadUserByUsername_UserNotFound_ShouldThrowUsernameNotFoundException() {
        String nonExistingUsername = "nonExistingUser";
        when(userRepository.findById(nonExistingUsername)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(nonExistingUsername);
        });
    }

    @Test
    public void testDoFilterInternal_NoAuthorizationHeader_DoesNotSetAuthentication() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null); // 模拟没有Authorization头

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response); // 确认过滤链继续执行
        assertNull(SecurityContextHolder.getContext().getAuthentication()); // 确认没有设置Authentication
    }

    @Test
    public void testDoFilterInternal_InvalidAuthorizationHeaderFormat_DoesNotSetAuthentication() throws Exception {
        String invalidHeader = "Invalid " + "token";

        when(request.getHeader("Authorization")).thenReturn(invalidHeader);

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void testDoFilterInternal_AlreadyAuthenticated_DoesNotOverrideAuthentication() throws Exception {
        String username = "testUser";
        String token = "validToken";
        UserDetails userDetails = new User(username, "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        UsernamePasswordAuthenticationToken existingAuth = new UsernamePasswordAuthenticationToken("existingUser", null);

        SecurityContextHolder.getContext().setAuthentication(existingAuth); // Set existing authentication
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtService.extractUsername(token)).thenReturn(username);
        when(userDetailsServiceImpl.loadUserByUsername(username)).thenReturn(userDetails);

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response); // Verify filter chain continues
        assertEquals(existingAuth, SecurityContextHolder.getContext().getAuthentication()); // Existing authentication remains
    }

    @Test
    public void testDoFilterInternal_TokenValidationFails_DoesNotSetAuthentication() throws Exception {
        String username = "testUser";
        String token = "validFormatButInvalidToken";
        UserDetails userDetails = new User(username, "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtService.extractUsername(token)).thenReturn(username);
        when(userDetailsServiceImpl.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtService.validateToken(token, userDetails)).thenReturn(false); // Token validation fails

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response); // Verify filter chain continues
        assertNull(SecurityContextHolder.getContext().getAuthentication()); // Authentication should not be set
    }


}
