package ru.mirea.sevostyanov.domain.repository;

import ru.mirea.sevostyanov.domain.models.User;

public interface UserRepository {
    void saveUser(User user);
    User getUser();
}