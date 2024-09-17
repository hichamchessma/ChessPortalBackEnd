package ma.maroc.echecs.chessportal.service;

import ma.maroc.echecs.chessportal.model.News;
import ma.maroc.echecs.chessportal.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    public Optional<News> getNewsById(Long id) {
        return newsRepository.findById(id);
    }

    public News addNews(News news) {
        return newsRepository.save(news);
    }

    public News updateNews(Long id, News updatedNews) {
        return newsRepository.findById(id)
                .map(news -> {
                    news.setTitle(updatedNews.getTitle());
                    news.setContent(updatedNews.getContent());
                    news.setAuthor(updatedNews.getAuthor());
                    news.setPublishedDate(updatedNews.getPublishedDate());
                    return newsRepository.save(news);
                }).orElse(null);
    }

    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }
}

