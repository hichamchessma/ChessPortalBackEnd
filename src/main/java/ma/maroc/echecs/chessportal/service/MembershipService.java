package ma.maroc.echecs.chessportal.service;

import ma.maroc.echecs.chessportal.model.Membership;
import ma.maroc.echecs.chessportal.repository.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MembershipService {

    private final MembershipRepository membershipRepository;

    @Autowired
    public MembershipService(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    // Add a new membership
    public Membership addMembership(Membership membership) {
        return membershipRepository.save(membership);
    }

    // Get a membership by id
    public Optional<Membership> getMembershipById(Long id) {
        return membershipRepository.findById(id);
    }

    // Get all memberships
    public List<Membership> getAllMemberships() {
        return membershipRepository.findAll();
    }

    // Update an existing membership
    public Membership updateMembership(Long id, Membership updatedMembership) {
        return membershipRepository.findById(id)
                .map(membership -> {
                    membership.setPlayer(updatedMembership.getPlayer());
                    membership.setClub(updatedMembership.getClub());
                    membership.setStartDate(updatedMembership.getStartDate());
                    membership.setEndDate(updatedMembership.getEndDate());
                    membership.setStatus(updatedMembership.getStatus());
                    return membershipRepository.save(membership);
                })
                .orElse(null);
    }

    // Delete a membership
    public void deleteMembership(Long id) {
        membershipRepository.deleteById(id);
    }
}

