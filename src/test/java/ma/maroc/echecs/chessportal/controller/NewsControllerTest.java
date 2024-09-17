package ma.maroc.echecs.chessportal.controller;

import ma.maroc.echecs.chessportal.config.SecurityConfig;
import ma.maroc.echecs.chessportal.model.News;
import ma.maroc.echecs.chessportal.service.NewsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
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

@WebMvcTest(NewsController.class)
@Import(SecurityConfig.class) // If you have a SecurityConfig class
@AutoConfigureMockMvc(addFilters = false) // This will disable Spring Security filters
public class NewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsService newsService;

    private News news;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        news = new News(1L, "Breaking News", "This is the content of the news", "Author Name", LocalDate.of(2022, 12, 12), LocalDate.now(), LocalDate.now());
    }

    @Test
    void testGetAllNews() throws Exception {
        when(newsService.getAllNews()).thenReturn(Arrays.asList(news));

        mockMvc.perform(get("/api/news")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Breaking News"));

        verify(newsService, times(1)).getAllNews();
    }

    @Test
    void testGetNewsById() throws Exception {
        when(newsService.getNewsById(1L)).thenReturn(Optional.of(news));

        mockMvc.perform(get("/api/news/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Breaking News"));

        verify(newsService, times(1)).getNewsById(1L);
    }

    @Test
    void testAddNews() throws Exception {
        when(newsService.addNews(any(News.class))).thenReturn(news);

        mockMvc.perform(post("/api/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Breaking News\", \"content\": \"This is the content of the news\", \"author\": \"Author Name\", \"publishedDate\": \"2022-12-12\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Breaking News"));

        verify(newsService, times(1)).addNews(any(News.class));
    }

    @Test
    void testUpdateNews() throws Exception {
        when(newsService.updateNews(eq(1L), any(News.class))).thenReturn(news);

        mockMvc.perform(put("/api/news/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Updated News\", \"content\": \"This is the updated content\", \"author\": \"New Author\", \"publishedDate\": \"2023-01-01\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Breaking News"));

        verify(newsService, times(1)).updateNews(eq(1L), any(News.class));
    }

    @Test
    void testDeleteNews() throws Exception {
        doNothing().when(newsService).deleteNews(1L);

        mockMvc.perform(delete("/api/news/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(newsService, times(1)).deleteNews(1L);
    }
}
