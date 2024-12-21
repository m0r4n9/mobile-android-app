package ru.mirea.sevostyanov.domain.usecase;

import java.util.List;

import ru.mirea.sevostyanov.domain.models.Articles;
import ru.mirea.sevostyanov.domain.repository.NewsRepository;

public class GetArticlesUseCase {
    private final NewsRepository newsRepository;

    public GetArticlesUseCase(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public List<Articles> execute() {
        return newsRepository.getNews();
    }
}