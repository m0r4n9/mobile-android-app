package ru.mirea.sevostyanov.data.repository;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.sevostyanov.data.network.ArticlesApiService;
import ru.mirea.sevostyanov.data.storage.room.AppDatabase;
import ru.mirea.sevostyanov.data.storage.room.ArticleEntity;
import ru.mirea.sevostyanov.domain.models.Articles;
import ru.mirea.sevostyanov.domain.repository.NewsRepository;

public class ArticlesRepositoryImpl implements NewsRepository {
    private final AppDatabase database;
    private final ArticlesApiService apiService;

    public ArticlesRepositoryImpl(AppDatabase database, ArticlesApiService apiService) {
        this.database = database;
        this.apiService = apiService;
    }

    @Override
    public List<Articles> getNews() {
        try {
            List<Articles> articlesFromApi = apiService.getMockArticles();
            saveNews(articlesFromApi);
            return articlesFromApi;
        } catch (Exception e) {
            List<ArticleEntity> newsEntities = database.articleDao().getAllArticles();
            return mapFromEntity(newsEntities);
        }
    }

    @Override
    public void saveNews(List<Articles> articles) {
        database.articleDao().insertArticles(mapToEntity(articles));
    }

    private List<ArticleEntity> mapToEntity(List<Articles> articles) {
        List<ArticleEntity> entities = new ArrayList<>();
        for (Articles article : articles) {
            entities.add(new ArticleEntity(
                    article.getId(),
                    article.getTitle(),
                    article.getContent(),
                    article.getDate(),
                    article.getImageUrl()));
        }
        return entities;
    }

    private List<Articles> mapFromEntity(List<ArticleEntity> entities) {
        List<Articles> articles = new ArrayList<>();
        for (ArticleEntity entity : entities) {
            articles.add(new Articles(
                    entity.getId(),
                    entity.getTitle(),
                    entity.getContent(),
                    entity.getDate(),
                    entity.getImageUrl()));
        }
        return articles;
    }
}