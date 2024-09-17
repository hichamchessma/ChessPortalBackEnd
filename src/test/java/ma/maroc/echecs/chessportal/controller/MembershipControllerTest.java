package ma.maroc.echecs.chessportal.controller;

import ma.maroc.echecs.chessportal.config.SecurityConfig;
import ma.maroc.echecs.chessportal.model.Membership;
import ma.maroc.echecs.chessportal.service.MembershipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MembershipController.class)
@Import(SecurityConfig.class) // If you have a SecurityConfig class
@AutoConfigureMockMvc(addFilters = false) // This will disable Spring Security filters
public class MembershipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MembershipService membershipService;

    private Membership membership;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        membership = new Membership(1L, null, null, LocalDate.of(2022, 1, 1), null, "Active", LocalDate.now(), LocalDate.now());
    }

    @Test
    void testGetAllMemberships() throws Exception {
        when(membershipService.getAllMemberships()).thenReturn(Arrays.asList(membership));

        mockMvc.perform(get("/api/memberships")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("Active"));

        verify(membershipService, times(1)).getAllMemberships();
    }

    @Test
    void testGetMembershipById() throws Exception {
        when(membershipService.getMembershipById(1L)).thenReturn(Optional.of(membership));

        mockMvc.perform(get("/api/memberships/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Active"));

        verify(membershipService, times(1)).getMembershipById(1L);
    }

    @Test
    void testAddMembership() throws Exception {
        when(membershipService.addMembership(any(Membership.class))).thenReturn(membership);

        mockMvc.perform(post("/api/memberships")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\": \"Active\", \"startDate\": \"2022-01-01\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Active"));

        verify(membershipService, times(1)).addMembership(any(Membership.class));
    }

    @Test
    void testUpdateMembership() throws Exception {
        when(membershipService.updateMembership(eq(1L), any(Membership.class))).thenReturn(membership);

        mockMvc.perform(put("/api/memberships/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\": \"Inactive\", \"startDate\": \"2023-01-01\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Active"));

        verify(membershipService, times(1)).updateMembership(eq(1L), any(Membership.class));
    }

    @Test
    void testDeleteMembership() throws Exception {
        doNothing().when(membershipService).deleteMembership(1L);

        mockMvc.perform(delete("/api/memberships/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(membershipService, times(1)).deleteMembership(1L);
    }
}
