package ru.mirea.sevostyanov.data.repository;

import android.content.Context;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.UserProfileChangeRequest;
import android.util.Log;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;

import ru.mirea.sevostyanov.domain.models.User;
import ru.mirea.sevostyanov.domain.repository.AuthCallback;
import ru.mirea.sevostyanov.domain.repository.AuthRepository;
import ru.mirea.sevostyanov.data.storage.SharedPrefsUserStorage;

public class AuthRepositoryImpl implements AuthRepository {
    private final FirebaseAuth firebaseAuth;
    private final SharedPrefsUserStorage userStorage;
    private final Context context;

    public AuthRepositoryImpl(Context context) {
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.userStorage = new SharedPrefsUserStorage(context);
        this.context = context;
    }

    @Override
    public void signIn(String email, String password, AuthCallback callback) {
        if (!isNetworkAvailable()) {
            callback.onError("Нет подключения к интернету");
            return;
        }

        Task<AuthResult> signInTask = firebaseAuth.signInWithEmailAndPassword(email, password);
        
        signInTask
            .addOnSuccessListener(authResult -> {
                FirebaseUser user = authResult.getUser();
                if (user != null) {
                    // Получаем displayName и сохраняем его
                    String username = user.getDisplayName();
                    Log.d("Firebase", "Sign in successful, username: " + username);
                    
                    // Сохраняем данные пользователя локально
                    userStorage.saveUser(user.getUid(), username, user.getEmail());
                    callback.onSuccess();
                } else {
                    callback.onError("Ошибка входа");
                }
            })
            .addOnFailureListener(e -> {
                String errorMessage = getReadableErrorMessage(e.getMessage());
                callback.onError(errorMessage);
            });
    }

    @Override
    public void signUp(String email, String password, String username, AuthCallback callback) {
        if (!isNetworkAvailable()) {
            callback.onError("Нет подключения к интернету");
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener(authResult -> {
                FirebaseUser user = authResult.getUser();
                if (user != null) {
                    Log.d("Firebase", "User created successfully, setting display name: " + username);
                    
                    // Create user profile
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build();

                    user.updateProfile(profileUpdates)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Проверяем, что имя действительно установлено
                                FirebaseUser updatedUser = firebaseAuth.getCurrentUser();
                                if (updatedUser != null) {
                                    Log.d("Firebase", "Display name after update: " + updatedUser.getDisplayName());
                                }
                                callback.onSuccess();
                            } else {
                                Log.e("Firebase", "Error updating profile: ", task.getException());
                                callback.onError("Ошибка при сохранении имени пользователя: " + 
                                              task.getException().getMessage());
                            }
                        });
                } else {
                    Log.e("Firebase", "User is null after creation");
                    callback.onError("Ошибка регистрации: пользователь не создан");
                }
            })
            .addOnFailureListener(e -> {
                Log.e("Firebase", "Sign up failed: ", e);
                String errorMessage = getReadableErrorMessage(e.getMessage());
                callback.onError(errorMessage);
            });
    }

    @Override
    public void signOut() {
        firebaseAuth.signOut();
    }

    @Override
    public User getCurrentUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            return new User(
                    firebaseUser.getUid(),
                    firebaseUser.getEmail(),
                    firebaseUser.getDisplayName(),
                    true
            );
        }
        return null;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) 
            context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkCapabilities capabilities = connectivityManager
                    .getNetworkCapabilities(connectivityManager.getActiveNetwork());
            return capabilities != null && (
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET));
        }
        return false;
    }

    private String getReadableErrorMessage(String firebaseError) {
        if (firebaseError == null) return "Неизвестная ошибка";

        if (firebaseError.contains("network error")) {
            return "Ошибка сети. Проверьте подключение к интернету";
        } else if (firebaseError.contains("ERROR_INVALID_EMAIL")) {
            return "Неверный формат email";
        } else if (firebaseError.contains("ERROR_WRONG_PASSWORD")) {
            return "Неверный пароль";
        } else if (firebaseError.contains("ERROR_USER_NOT_FOUND")) {
            return "Пользователь не найден";
        } else if (firebaseError.contains("ERROR_USER_DISABLED")) {
            return "Аккаунт отключен";
        } else if (firebaseError.contains("ERROR_TOO_MANY_REQUESTS")) {
            return "Слишком много попыток входа. Попробуйте позже";
        } else if (firebaseError.contains("ERROR_OPERATION_NOT_ALLOWED")) {
            return "Операция не разрешена";
        }

        return "Ошибка авторизации: " + firebaseError;
    }
}
