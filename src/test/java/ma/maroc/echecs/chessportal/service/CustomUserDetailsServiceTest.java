package ma.maroc.echecs.chessportal.service;

import ma.maroc.echecs.chessportal.model.RegistrationRequest;
import ma.maroc.echecs.chessportal.model.User;
import ma.maroc.echecs.chessportal.repository.UserRepository;
import ma.maroc.echecs.chessportal.service.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    private User mockUser;

    @BeforeEach
    public void setUp() {
        // Create a mock user
        mockUser = new User();
        mockUser.setUsername("newUser");
        mockUser.setPassword("password123");
        mockUser.setRoles(Set.of("ROLE_USER"));
    }

    @Test
    public void testLoadUserByUsername() {
        // Mock repository behavior
        when(userRepository.findByUsername("newUser")).thenReturn(Optional.of(mockUser));

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("newUser");

        // Assert
        assertEquals("newUser", userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER")));
    }

    @Test
    public void testSaveUser() {
        // Mock the request
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("testUser");
        request.setPassword("plainPassword");
        request.setRoles(Set.of("ROLE_USER"));

        // Mock encoder behavior
        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");

        // Mock repository behavior
        when(userRepository.existsByUsername("testUser")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(new User());

        // Call the method to test
        User savedUser = customUserDetailsService.saveUser(request);

        // Verifying interactions and assertions
        assertNotNull(savedUser);
        verify(userRepository, times(1)).save(any(User.class));
    }
}
