package ru.mirea.maiorovsevostyanov.rumireamaiorovsevostyanovnewsapp.presentation.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.mirea.maiorovsevostyanov.rumireamaiorovsevostyanovnewsapp.R;
import ru.mirea.maiorovsevostyanov.rumireamaiorovsevostyanovnewsapp.presentation.adapters.NewsAdapter;
import ru.mirea.maiorovsevostyanov.rumireamaiorovsevostyanovnewsapp.presentation.viewmodels.NewsViewModel;
import ru.mirea.maiorovsevostyanov.rumireamaiorovsevostyanovnewsapp.presentation.viewmodels.NewsViewModelFactory;
import ru.mirea.sevostyanov.data.repository.ArticlesRepositoryImpl;
import ru.mirea.sevostyanov.data.storage.room.AppDatabase;
import ru.mirea.sevostyanov.data.network.ArticlesApiService;

public class NewsFragment extends Fragment {
    private static final String TAG = "NewsFragment";
    private NewsViewModel newsViewModel;
    private RecyclerView newsRecyclerView;
    private NewsAdapter newsAdapter;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "NewsFragment создан");

        newsRecyclerView = view.findViewById(R.id.newsRecyclerView);
        progressBar = view.findViewById(R.id.progressBar);

        newsAdapter = new NewsAdapter();
        newsAdapter.setOnArticleClickListener(article -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", article.getTitle());
            bundle.putString("content", article.getContent());
            bundle.putString("date", article.getDate());
            bundle.putString("imageUrl", article.getImageUrl());
            
            Navigation.findNavController(view)
                    .navigate(R.id.action_newsFragment_to_articleDetailFragment, bundle);
        });

        newsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        newsRecyclerView.setAdapter(newsAdapter);

        NewsViewModelFactory factory = new NewsViewModelFactory(
                new ArticlesRepositoryImpl(
                        AppDatabase.getInstance(requireContext()),
                        new ArticlesApiService()));

        newsViewModel = new ViewModelProvider(this, factory).get(NewsViewModel.class);

        newsViewModel.getNews().observe(getViewLifecycleOwner(), news -> {
            if (news != null) {
                newsAdapter.setArticles(news);
            }
        });

        newsViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            Log.d(TAG, "Состояние загрузки изменено: " + (isLoading ? "загрузка" : "завершено"));
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        newsViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Log.e(TAG, "Получена ошибка: " + error);
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}