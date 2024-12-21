package ru.mirea.maiorovsevostyanov.rumireamaiorovsevostyanovnewsapp.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.maiorovsevostyanov.rumireamaiorovsevostyanovnewsapp.R;
import ru.mirea.sevostyanov.domain.models.Articles;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<Articles> articlesList = new ArrayList<>();
    private OnArticleClickListener listener;

    // Define the interface inside the adapter
    public interface OnArticleClickListener {
        void onArticleClick(Articles article);
    }

    public void setOnArticleClickListener(OnArticleClickListener listener) {
        this.listener = listener;
    }

    public void setArticles(List<Articles> articles) {
        this.articlesList = articles;
        notifyDataSetChanged();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView dateTextView;
        TextView descriptionTextView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        Articles articles = articlesList.get(position);
        if (holder.dateTextView != null && articles.getTitle() != null) {
            holder.dateTextView.setText(articles.getDate());
        }
        if (holder.titleTextView != null && articles.getContent() != null) {
            holder.titleTextView.setText(articles.getTitle());
        }
        if (holder.descriptionTextView != null && articles.getContent() != null) {
            holder.descriptionTextView.setText(articles.getContent());
        }
        
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onArticleClick(articles);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }
}