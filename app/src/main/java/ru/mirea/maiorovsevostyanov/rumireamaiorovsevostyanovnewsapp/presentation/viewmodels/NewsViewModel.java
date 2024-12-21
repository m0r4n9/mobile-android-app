package ru.mirea.maiorovsevostyanov.rumireamaiorovsevostyanovnewsapp.presentation.viewmodels;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.mirea.sevostyanov.domain.models.Articles;
import ru.mirea.sevostyanov.domain.repository.NewsRepository;

public class NewsViewModel extends ViewModel {
    private static final String TAG = "NewsViewModel";
    private final NewsRepository newsRepository;
    private final MediatorLiveData<List<Articles>> newsLiveData = new MediatorLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<List<Articles>> apiNewsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Articles>> dbNewsLiveData = new MutableLiveData<>();

    public NewsViewModel(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
        Log.d(TAG, "ViewModel создана");

        newsLiveData.addSource(apiNewsLiveData, news -> {
            if (news != null) {
                Log.d(TAG, "Получены данные из API, количество новостей: " + news.size());
                newsLiveData.setValue(news);
            }
        });

        newsLiveData.addSource(dbNewsLiveData, news -> {
            if (news != null && (apiNewsLiveData.getValue() == null || apiNewsLiveData.getValue().isEmpty())) {
                Log.d(TAG, "Получены данные из БД, количество новостей: " + news.size());
                newsLiveData.setValue(news);
            }
        });

        loadNews();
    }

    public void loadNews() {
        Log.d(TAG, "Начало загрузки новостей");
        isLoading.setValue(true);

        new Thread(() -> {
            try {
                Log.d(TAG, "Попытка загрузки из API");
                List<Articles> apiNews = newsRepository.getNews();
                new Handler(Looper.getMainLooper()).post(() -> {
                    Log.d(TAG, "Данные из API успешно загружены");
                    apiNewsLiveData.setValue(apiNews);
                    isLoading.setValue(false);
                });
            } catch (Exception e) {
                Log.e(TAG, "Ошибка загрузки из API: " + e.getMessage());
                try {
                    Log.d(TAG, "Повторная попытка");
                    List<Articles> dbNews = newsRepository.getNews();
                    new Handler(Looper.getMainLooper()).post(() -> {
                        Log.d(TAG, "Успешно");
                        dbNewsLiveData.setValue(dbNews);
                        isLoading.setValue(false);
                    });
                } catch (Exception dbError) {
                    Log.e(TAG, "Ошибка повторого запроса: " + dbError.getMessage());
                    new Handler(Looper.getMainLooper()).post(() -> {
                        error.setValue(dbError.getMessage());
                        isLoading.setValue(false);
                    });
                }
            }
        }).start();
    }
    public LiveData<List<Articles>> getNews() {
        return newsLiveData;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getError() {
        return error;
    }
}