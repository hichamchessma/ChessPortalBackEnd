package ma.maroc.echecs.chessportal.service;

import ma.maroc.echecs.chessportal.model.Membership;
import ma.maroc.echecs.chessportal.repository.MembershipRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class MembershipServiceTest {

    @Mock
    private MembershipRepository membershipRepository;

    @InjectMocks
    private MembershipService membershipService;

    private Membership membership;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        membership = new Membership(1L, null, null, LocalDate.of(2020, 1, 1), null, "Active", LocalDate.now(), LocalDate.now());
    }

    @Test
    void getAllMemberships() {
        when(membershipRepository.findAll()).thenReturn(Arrays.asList(membership));
        List<Membership> memberships = membershipService.getAllMemberships();
        assertEquals(1, memberships.size());
        verify(membershipRepository, times(1)).findAll();
    }

    @Test
    void getMembershipById() {
        when(membershipRepository.findById(1L)).thenReturn(Optional.of(membership));
        Optional<Membership> found = membershipService.getMembershipById(1L);
        assertTrue(found.isPresent());
        verify(membershipRepository, times(1)).findById(1L);
    }

    @Test
    void addMembership() {
        when(membershipRepository.save(any(Membership.class))).thenReturn(membership);
        Membership created = membershipService.addMembership(membership);
        assertEquals(membership.getId(), created.getId());
        verify(membershipRepository, times(1)).save(membership);
    }

    @Test
    void updateMembership() {
        when(membershipRepository.findById(1L)).thenReturn(Optional.of(membership));
        when(membershipRepository.save(membership)).thenReturn(membership);
        Membership updated = membershipService.updateMembership(1L, membership);
        assertEquals(membership.getId(), updated.getId());
        verify(membershipRepository, times(1)).save(membership);
    }

    @Test
    void deleteMembership() {
        membershipService.deleteMembership(1L);
        verify(membershipRepository, times(1)).deleteById(1L);
    }
}

