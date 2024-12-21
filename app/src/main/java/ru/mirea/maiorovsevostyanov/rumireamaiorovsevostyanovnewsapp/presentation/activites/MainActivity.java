package ru.mirea.maiorovsevostyanov.rumireamaiorovsevostyanovnewsapp.presentation.activites;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.mirea.maiorovsevostyanov.rumireamaiorovsevostyanovnewsapp.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "MainActivity создана");

        // Получаем NavHostFragment и NavController
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        // Находим BottomNavigationView
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        
        // Связываем BottomNavigationView с NavController
        NavigationUI.setupWithNavController(bottomNav, navController);
    }
}
