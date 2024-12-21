package ru.mirea.maiorovsevostyanov.rumireamaiorovsevostyanovnewsapp.presentation.activites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.maiorovsevostyanov.rumireamaiorovsevostyanovnewsapp.R;
import ru.mirea.sevostyanov.data.repository.AuthRepositoryImpl;
import ru.mirea.sevostyanov.domain.repository.AuthCallback;
import ru.mirea.sevostyanov.domain.repository.AuthRepository;
import ru.mirea.sevostyanov.domain.usecase.SignInUseCase;
import ru.mirea.sevostyanov.domain.usecase.SignUpUseCase;
import ru.mirea.sevostyanov.data.storage.SharedPrefsUserStorage;

public class AuthActivity extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText usernameEditText;
    private Button signInButton;
    private Button signUpButton;
    private Button guestButton;
    private ProgressBar progressBar;

    private SignInUseCase signInUseCase;
    private SignUpUseCase signUpUseCase;
    private SharedPrefsUserStorage userStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        // Initialize views
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        signInButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.button);
        guestButton = findViewById(R.id.guestButton);
        progressBar = findViewById(R.id.progressBar);

        // Initialize SharedPrefsUserStorage
        userStorage = new SharedPrefsUserStorage(this);

        // Initialize use cases
        initializeRepositories();

        // Set click listeners
        signInButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            handleSignIn();
        });
        signUpButton.setOnClickListener(v -> {
            usernameEditText.setVisibility(View.VISIBLE);
            signInButton.setVisibility(View.GONE);
            guestButton.setVisibility(View.GONE);
            signUpButton.setText("Зарегистрироваться");
            signUpButton.setOnClickListener(v2 -> handleSignUp());
        });
        guestButton.setOnClickListener(v -> handleGuestLogin());
    }

    private void handleSignIn() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (validateInput(email, password)) {
            signInUseCase.execute(email, password, new AuthCallback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(AuthActivity.this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AuthActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void onError(String message) {
                    String errorMessage = getString(R.string.login_error, message);
                    Toast.makeText(AuthActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void handleSignUp() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String username = usernameEditText.getText().toString().trim();

        Log.d("AuthActivity", "Attempting signup with username: " + username);

        if (validateSignUpInput(email, password, username)) {
            signUpUseCase.execute(email, password, username, new AuthCallback() {
                @Override
                public void onSuccess() {
                    Log.d("AuthActivity", "SignUp success, saving user data");
                    userStorage.setGuestMode(false);
                    userStorage.saveUser(null, username, email);
                    
                    // Проверяем сохраненные данные
                    Log.d("AuthActivity", "Saved username: " + userStorage.getUserName());
                    
                    Toast.makeText(AuthActivity.this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AuthActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void onError(String message) {
                    Log.e("AuthActivity", "SignUp error: " + message);
                    Toast.makeText(AuthActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void handleGuestLogin() {
        userStorage.setGuestMode(true);
        userStorage.saveUser(null, null, null);

        Toast.makeText(this, "Вход в режиме гостя", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AuthActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean validateInput(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateSignUpInput(String email, String password, String username) {
        if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
            Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (username.length() < 3) {
            Toast.makeText(this, "Имя пользователя должно содержать минимум 3 символа", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initializeRepositories() {
        AuthRepository authRepository = new AuthRepositoryImpl(this);
        signInUseCase = new SignInUseCase(authRepository);
        signUpUseCase = new SignUpUseCase(authRepository);
    }
}