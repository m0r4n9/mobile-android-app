package ru.mirea.sevostyanov.domain.usecase;

import ru.mirea.sevostyanov.domain.repository.AuthCallback;
import ru.mirea.sevostyanov.domain.repository.AuthRepository;

public class SignInUseCase {
    private final AuthRepository authRepository;

    public SignInUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void execute(String email, String password, AuthCallback callback) {
        authRepository.signIn(email, password, callback);
    }
}