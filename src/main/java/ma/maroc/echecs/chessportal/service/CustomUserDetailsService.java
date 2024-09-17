package ma.maroc.echecs.chessportal.service;

import ma.maroc.echecs.chessportal.model.RegistrationRequest;
import ma.maroc.echecs.chessportal.model.User;
import ma.maroc.echecs.chessportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private EmailService emailService;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return org.springframework.security.core.userdetails.User.withUsername((user).getUsername()).password(user.getPassword()).roles(user.getRoles().stream().map(role -> role.replace("ROLE_", "")).toArray(String[]::new)).build();
    }

    public User saveUser(RegistrationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already taken.");
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRoles(request.getRoles());
        newUser.setEmail(request.getEmail());

        return userRepository.save(newUser);
    }

    public void sendPasswordResetEmail(String email) {
        // Generate a token
        String token = UUID.randomUUID().toString();

        // Save the token to the database associated with the user

        // Send email logic here (using JavaMailSender or other email service)
        String resetLink = "http://your-app.com/reset-password?token=" + token;
        String message = "Click the link to reset your password: " + resetLink;

        emailService.sendEmail(email, "Password Reset", message);
    }

}
