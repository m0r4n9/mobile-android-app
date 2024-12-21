package ru.mirea.sevostyanov.domain.repository;

public interface AuthCallback {
    void onSuccess();
    void onError(String message);
}

