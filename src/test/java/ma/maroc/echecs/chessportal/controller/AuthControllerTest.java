package ma.maroc.echecs.chessportal.controller;

import ma.maroc.echecs.chessportal.config.JwtUtil;
import ma.maroc.echecs.chessportal.model.AuthenticationRequest;
import ma.maroc.echecs.chessportal.model.RegistrationRequest;
import ma.maroc.echecs.chessportal.model.User;
import ma.maroc.echecs.chessportal.service.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @InjectMocks
    private AuthController authController;


       @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @WithMockUser(roles = "USER")
    @Test
    public void testRegisterUser() throws Exception {
        // Mocking the request
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("newUser");
        request.setPassword("password123");
        request.setRoles(Set.of("ROLE_USER"));

        // Mocking saveUser method
        when(customUserDetailsService.saveUser(any(RegistrationRequest.class))).thenReturn(new User());

        // Perform request and assert response
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"newUser\", \"password\": \"password123\" }"))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));

    }


    @Test
    public void testLoginUser() throws Exception {
        // Mocking the request
        AuthenticationRequest authRequest = new AuthenticationRequest("newUser", "password123");

        // Mocking Authentication Manager
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken("newUser", "password123", List.of());
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authToken); // Return a valid authentication token
        // Mocking JWT Util
        when(jwtUtil.generateToken(anyString())).thenReturn("mockJwtToken");

        // Perform request and assert response
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"newUser\", \"password\": \"password123\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt").value("mockJwtToken"));
    }

}
