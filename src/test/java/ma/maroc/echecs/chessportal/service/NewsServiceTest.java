package ma.maroc.echecs.chessportal.service;

import ma.maroc.echecs.chessportal.model.News;
import ma.maroc.echecs.chessportal.repository.NewsRepository;
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

class NewsServiceTest {

    @Mock
    private NewsRepository newsRepository;

    @InjectMocks
    private NewsService newsService;

    private News news;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        news = new News(1L, "Chess Tournament", "Details about the tournament", "Author", LocalDate.now(), LocalDate.now(), LocalDate.now());
    }

    @Test
    void getAllNews() {
        when(newsRepository.findAll()).thenReturn(Arrays.asList(news));
        List<News> newsList = newsService.getAllNews();
        assertEquals(1, newsList.size());
        verify(newsRepository, times(1)).findAll();
    }

    @Test
    void getNewsById() {
        when(newsRepository.findById(1L)).thenReturn(Optional.of(news));
        Optional<News> found = newsService.getNewsById(1L);
        assertTrue(found.isPresent());
        verify(newsRepository, times(1)).findById(1L);
    }

    @Test
    void addNews() {
        when(newsRepository.save(any(News.class))).thenReturn(news);
        News created = newsService.addNews(news);
        assertEquals(news.getId(), created.getId());
        verify(newsRepository, times(1)).save(news);
    }

    @Test
    void updateNews() {
        when(newsRepository.findById(1L)).thenReturn(Optional.of(news));
        when(newsRepository.save(news)).thenReturn(news);
        News updated = newsService.updateNews(1L, news);
        assertEquals(news.getId(), updated.getId());
        verify(newsRepository, times(1)).save(news);
    }

    @Test
    void deleteNews() {
        newsService.deleteNews(1L);
        verify(newsRepository, times(1)).deleteById(1L);
    }
}
