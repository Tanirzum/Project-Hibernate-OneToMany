package org.example;

import org.example.Model.Director;
import org.example.Model.Film;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class App {
    public static void main(String[] args) {


        Configuration configuration = new Configuration().addAnnotatedClass(Director.class).
                addAnnotatedClass(Film.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

//    1. помощью Hibernate получите любого режиссера, а затем получите список его фильмов

            Director director = session.get(Director.class, 1);
            List<Film> list = director.getFilm();
            for (Film film : list) {
                System.out.println(film);
            }

//    2. Получите любой фильм, а затем получите его режиссера.

            Film film = session.get(Film.class, 2);
            System.out.println(film);
            System.out.println(film.getDirector());

//    3. Добавьте еще один фильм для любого режиссера.

            Director director = session.get(Director.class, 2);
            Film film = new Film("social network ", director);
            session.save(film);

//    4. Смените режиссера у существующего фильма.

            Director director = session.get(Director.class, 1);
            Film film = session.get(Film.class, 3);
            film.setDirector(director)

//    5. Удалите фильм у любого режиссера.

            Film film = session.get(Film.class, 8);
            session.remove(film);

            session.getTransaction().commit();

        } finally {
            sessionFactory.close();
        }
    }
}
