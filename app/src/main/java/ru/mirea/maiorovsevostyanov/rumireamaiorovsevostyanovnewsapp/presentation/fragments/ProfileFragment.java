package ru.mirea.maiorovsevostyanov.rumireamaiorovsevostyanovnewsapp.presentation.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.NavController;

import ru.mirea.maiorovsevostyanov.rumireamaiorovsevostyanovnewsapp.R;
import ru.mirea.maiorovsevostyanov.rumireamaiorovsevostyanovnewsapp.presentation.activites.AuthActivity;
import ru.mirea.sevostyanov.data.storage.SharedPrefsUserStorage;

public class ProfileFragment extends Fragment {
    private SharedPrefsUserStorage userStorage;
    private TextView emailTextView;
    private TextView userTypeTextView;
    private Button logoutButton;
    private Button favoritesButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userStorage = new SharedPrefsUserStorage(requireContext());
        emailTextView = view.findViewById(R.id.emailTextView);
        userTypeTextView = view.findViewById(R.id.userTypeTextView);
        logoutButton = view.findViewById(R.id.logoutButton);
        favoritesButton = view.findViewById(R.id.favoritesButton);

        String email = userStorage.getUserEmail();
        String username = userStorage.getUserName();
        boolean isGuest = userStorage.isGuestMode();

        emailTextView.setText(isGuest ? "Гость" : username);
        userTypeTextView.setText(isGuest ? "Гостевой режим" : email);

        favoritesButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.action_profileFragment_to_favoritesFragment);
        });

        logoutButton.setOnClickListener(v -> {
            userStorage.setGuestMode(false);
            userStorage.saveUser(null, null, null);
            
            Intent intent = new Intent(requireActivity(), AuthActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
} 