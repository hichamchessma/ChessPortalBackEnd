package ma.maroc.echecs.chessportal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ma.maroc.echecs.chessportal.model.Player;
import ma.maroc.echecs.chessportal.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public Page<Player> getAllPlayers(Pageable pageable)  {
                Page players = playerRepository.findAll(pageable);


        return players;
    }

    public Optional<Player> getPlayerById(Long id) {
        return playerRepository.findById(id);
    }

    public Player addPlayer(Player player) {
        return playerRepository.save(player);
    }

    public Player updatePlayer(Long id, Player updatedPlayer) {
        return playerRepository.findById(id).map(player -> {
            player.setFirstName(updatedPlayer.getFirstName());
            player.setLastName(updatedPlayer.getLastName());
            player.setBirthdate(updatedPlayer.getBirthdate());
            player.setEloRating(updatedPlayer.getEloRating());
            player.setBlitzRating(updatedPlayer.getBlitzRating());
            player.setRapidRating(updatedPlayer.getRapidRating());
            player.setUpdatedAt(LocalDate.now());
            return playerRepository.save(player);
        }).orElse(null);
    }

    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }


    public Page<Player> searchPlayers(String keyword, Pageable pageable) {
        return playerRepository.findByFirstNameContainingOrLastNameContainingOrClubNameContaining(keyword, keyword, keyword, pageable);
    }
}
