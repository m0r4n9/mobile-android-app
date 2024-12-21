package ru.mirea.sevostyanov.domain.repository;

import ru.mirea.sevostyanov.domain.models.User;

public interface AuthRepository {
    void signIn(String email, String password, AuthCallback callback);
    void signUp(String email, String password, String username, AuthCallback callback);
    void signOut();
    User getCurrentUser();
}
