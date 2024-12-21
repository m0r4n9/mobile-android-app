package ru.mirea.maiorovsevostyanov.rumireamaiorovsevostyanovnewsapp.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import ru.mirea.maiorovsevostyanov.rumireamaiorovsevostyanovnewsapp.R;

public class ArticleDetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_detail, container, false);
        
        Bundle args = getArguments();
        if (args != null) {
            TextView titleView = view.findViewById(R.id.titleTextView);
            TextView dateView = view.findViewById(R.id.dateTextView);
            TextView contentView = view.findViewById(R.id.contentTextView);
            
            titleView.setText(args.getString("title"));
            dateView.setText(args.getString("date"));
            contentView.setText(args.getString("content"));
        }

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }
} 