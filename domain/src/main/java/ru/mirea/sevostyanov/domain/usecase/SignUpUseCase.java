package ru.mirea.sevostyanov.domain.usecase;

import ru.mirea.sevostyanov.domain.repository.AuthCallback;
import ru.mirea.sevostyanov.domain.repository.AuthRepository;

public class SignUpUseCase {
    private final AuthRepository authRepository;

    public SignUpUseCase(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void execute(String email, String password, String username, AuthCallback callback) {
        authRepository.signUp(email, password, username, callback);
    }
}