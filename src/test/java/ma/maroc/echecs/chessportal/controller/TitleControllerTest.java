package ma.maroc.echecs.chessportal.controller;

import ma.maroc.echecs.chessportal.config.SecurityConfig;
import ma.maroc.echecs.chessportal.model.Title;
import ma.maroc.echecs.chessportal.service.TitleService;
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

@WebMvcTest(TitleController.class)
@Import(SecurityConfig.class) // If you have a SecurityConfig class
@AutoConfigureMockMvc(addFilters = false) // This will disable Spring Security filters
public class TitleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TitleService titleService;

    private Title title;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        title = new Title(1L, null, "Grandmaster", LocalDate.of(2023, 9, 1), LocalDate.now(), LocalDate.now());
    }

    @Test
    void testGetAllTitles() throws Exception {
        when(titleService.getAllTitles()).thenReturn(Arrays.asList(title));

        mockMvc.perform(get("/api/titles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Grandmaster"));

        verify(titleService, times(1)).getAllTitles();
    }

    @Test
    void testGetTitleById() throws Exception {
        when(titleService.getTitleById(1L)).thenReturn(Optional.of(title));

        mockMvc.perform(get("/api/titles/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Grandmaster"));

        verify(titleService, times(1)).getTitleById(1L);
    }

    @Test
    void testAddTitle() throws Exception {
        when(titleService.addTitle(any(Title.class))).thenReturn(title);

        mockMvc.perform(post("/api/titles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Grandmaster\", \"awardedDate\": \"2023-09-01\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Grandmaster"));

        verify(titleService, times(1)).addTitle(any(Title.class));
    }

    @Test
    void testUpdateTitle() throws Exception {
        when(titleService.updateTitle(eq(1L), any(Title.class))).thenReturn(title);

        mockMvc.perform(put("/api/titles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"International Master\", \"awardedDate\": \"2023-09-01\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Grandmaster"));

        verify(titleService, times(1)).updateTitle(eq(1L), any(Title.class));
    }

    @Test
    void testDeleteTitle() throws Exception {
        doNothing().when(titleService).deleteTitle(1L);

        mockMvc.perform(delete("/api/titles/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(titleService, times(1)).deleteTitle(1L);
    }
}
