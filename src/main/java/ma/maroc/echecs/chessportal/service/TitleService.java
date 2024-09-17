package ma.maroc.echecs.chessportal.service;

import ma.maroc.echecs.chessportal.model.Title;
import ma.maroc.echecs.chessportal.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
public class TitleService {

    @Autowired
    private TitleRepository titleRepository;

    // Get all Titles
    public List<Title> getAllTitles() {
        return titleRepository.findAll();
    }

    // Get a Title by ID
    public Optional<Title> getTitleById(Long id) {
        return titleRepository.findById(id);
    }

    // Add a new Title
    public Title addTitle(Title title) {
        return titleRepository.save(title);
    }

    // Update an existing Title
    public Title updateTitle(Long id, Title updatedTitle) {
        return titleRepository.findById(id)
                .map(title -> {
                    title.setTitle(updatedTitle.getTitle());
                    title.setAwardedDate(updatedTitle.getAwardedDate());
                    title.setPlayer(updatedTitle.getPlayer());
                    return titleRepository.save(title);
                }).orElse(null);
    }

    // Delete a Title
    public void deleteTitle(Long id) {
        titleRepository.deleteById(id);
    }
}

