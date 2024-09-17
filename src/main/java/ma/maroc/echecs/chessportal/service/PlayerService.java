package ma.maroc.echecs.chessportal.service;

import ma.maroc.echecs.chessportal.model.Player;
import ma.maroc.echecs.chessportal.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Optional<Player> getPlayerById(Long id) {
        return playerRepository.findById(id);
    }

    public Player addPlayer(Player player) {
        return playerRepository.save(player);
    }

    public Player updatePlayer(Long id, Player updatedPlayer) {
        return playerRepository.findById(id)
                .map(player -> {
                    player.setFirstName(updatedPlayer.getFirstName());
                    player.setLastName(updatedPlayer.getLastName());
                    player.setBirthdate(updatedPlayer.getBirthdate());
                    player.setEloRating(updatedPlayer.getEloRating());
                    player.setBlitzRating(updatedPlayer.getBlitzRating());
                    player.setRapidRating(updatedPlayer.getRapidRating());
                    return playerRepository.save(player);
                }).orElse(null);
    }

    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }
}
