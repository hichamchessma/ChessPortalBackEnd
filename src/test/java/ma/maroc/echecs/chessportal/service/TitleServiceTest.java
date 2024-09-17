package ma.maroc.echecs.chessportal.service;

import ma.maroc.echecs.chessportal.model.Player;
import ma.maroc.echecs.chessportal.model.Title;
import ma.maroc.echecs.chessportal.repository.TitleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TitleServiceTest {

    @Mock
    private TitleRepository titleRepository;

    @InjectMocks
    private TitleService titleService;

    private Title title;

    @BeforeEach
    void setUp() {
        title = new Title(1L, new Player(), "Grandmaster", LocalDate.of(2020, 1, 1), LocalDate.now(), LocalDate.now());
    }

    @Test
    void testAddTitle() {
        when(titleRepository.save(any(Title.class))).thenReturn(title);
        Title result = titleService.addTitle(title);
        assertEquals("Grandmaster", result.getTitle());
    }

    @Test
    void testGetAllTitles() {
        List<Title> titles = List.of(title);
        when(titleRepository.findAll()).thenReturn(titles);
        List<Title> result = titleService.getAllTitles();
        assertEquals(1, result.size());
        assertEquals("Grandmaster", result.get(0).getTitle());
    }

    @Test
    void testDeleteTitle() {
        titleService.deleteTitle(1L);
        verify(titleRepository, times(1)).deleteById(1L);
    }
}

