package ru.mirea.sevostyanov.data.storage.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ArticleDao {
    @Query("SELECT * FROM articles")
    List<ArticleEntity> getAllArticles();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArticles(List<ArticleEntity> articles);

    @Query("DELETE FROM articles")
    void deleteAllArticles();
}