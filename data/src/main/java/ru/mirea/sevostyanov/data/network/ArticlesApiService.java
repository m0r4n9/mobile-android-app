package ru.mirea.sevostyanov.data.network;

import java.util.ArrayList;
import java.util.List;
import ru.mirea.sevostyanov.domain.models.Articles;

public class ArticlesApiService {
    public List<Articles> getMockArticles() {
        List<Articles> mockArticles = new ArrayList<>();
        mockArticles.add(new Articles(
                "1",
                "Новые технологии в образовании",
                "Искусственный интеллект и виртуальная реальность меняют подход к обучению в современных университетах",
                "10.10.2024",
                "https://example.com/education.jpg"));
                
        mockArticles.add(new Articles(
                "2",
                "Экологические инициативы в городе",
                "Запуск новых программ по переработке отходов и озеленению городских пространств",
                "09.10.2024",
                "https://example.com/ecology.jpg"));
                
        mockArticles.add(new Articles(
                "3",
                "Студенческие проекты получили признание",
                "Три проекта студентов МИРЭА победили в международном конкурсе инноваций",
                "08.10.2024",
                "https://example.com/students.jpg"));
                
        mockArticles.add(new Articles(
                "4",
                "Научная конференция 2024",
                "Ведущие специалисты обсудили перспективы развития информационных технологий",
                "07.10.2024",
                "https://example.com/conference.jpg"));
                
        return mockArticles;
    }
}