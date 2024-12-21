package ru.mirea.sevostyanov.domain.repository;

import java.util.List;

import ru.mirea.sevostyanov.domain.models.Articles;

public interface NewsRepository {
    List<Articles> getNews();

    void saveNews(List<Articles> articles);
}