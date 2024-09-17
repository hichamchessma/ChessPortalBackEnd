package ma.maroc.echecs.chessportal.controller;

import ma.maroc.echecs.chessportal.model.Title;
import ma.maroc.echecs.chessportal.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/titles")
public class TitleController {

    private final TitleService titleService;

    @Autowired
    public TitleController(TitleService titleService) {
        this.titleService = titleService;
    }

    @GetMapping
    public List<Title> getAllTitles() {
        return titleService.getAllTitles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Title> getTitleById(@PathVariable Long id) {
        Optional<Title> title = titleService.getTitleById(id);
        return title.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Title addTitle(@RequestBody Title title) {
        return titleService.addTitle(title);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Title> updateTitle(@PathVariable Long id, @RequestBody Title updatedTitle) {
        Optional<Title> updated = Optional.ofNullable(titleService.updateTitle(id, updatedTitle));
        return updated.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTitle(@PathVariable Long id) {
        titleService.deleteTitle(id);
        return ResponseEntity.noContent().build();
    }
}
