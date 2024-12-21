package ru.mirea.sevostyanov.data.api;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.mirea.sevostyanov.data.api.model.ArticlesResponse;

public interface ArticlesApiService {
    @GET("articles")
    Call<List<ArticlesResponse>> getArticles();
}