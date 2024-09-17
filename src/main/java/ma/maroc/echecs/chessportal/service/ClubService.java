package ma.maroc.echecs.chessportal.service;

import ma.maroc.echecs.chessportal.model.Club;
import ma.maroc.echecs.chessportal.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClubService {

    @Autowired
    private ClubRepository clubRepository;

    // Method to get all clubs
    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    // Method to get a club by ID
    public Optional<Club> getClubById(Long id) {
        return clubRepository.findById(id);
    }

    // Method to add a new club
    public Club addClub(Club club) {
        return clubRepository.save(club);
    }

    // Method to update an existing club
    public Club updateClub(Long id, Club updatedClub) {
        return clubRepository.findById(id)
                .map(club -> {
                    club.setName(updatedClub.getName());
                    club.setCity(updatedClub.getCity());
                    club.setCreationYear(updatedClub.getCreationYear());
                    club.setContactEmail(updatedClub.getContactEmail());
                    return clubRepository.save(club);
                })
                .orElse(null);
    }

    // Method to delete a club by ID
    public void deleteClub(Long id) {
        clubRepository.deleteById(id);
    }
}
