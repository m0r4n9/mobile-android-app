package ru.mirea.maiorovsevostyanov.rumireamaiorovsevostyanovnewsapp.presentation.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.sevostyanov.domain.repository.NewsRepository;

public class NewsViewModelFactory implements ViewModelProvider.Factory {
    private final NewsRepository newsRepository;

    public NewsViewModelFactory(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NewsViewModel.class)) {
            return (T) new NewsViewModel(newsRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
} 