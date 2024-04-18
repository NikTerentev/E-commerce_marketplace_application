package org.example.lab_1.manager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Класс, отвечающий за создание сессий с базой данных.
 */
public class DAO {

    /**
     * Поле сессии
     */
    private static final ThreadLocal<Session> session = new ThreadLocal<Session>();

    /**
     * Фабрика для создания сессий
     */
    private static final SessionFactory sessionFactory = new Configuration().
            configure("hibernate.cfg.xml").buildSessionFactory();

    /**
     * Получает сессию
     * @return Открытая сессия
     */
    public static Session getSession() {
        // Проверяем, существует ли открытая сессия в DAO
        if (session.get() == null) {
            // Если нет, запрашиваем новую сессию из sessionFactory
            session.set(sessionFactory.openSession());
        }
        // Возвращаем текущую сессию
        return session.get();
    }

    /**
     * Получает сессию и начинает транзакцию
     */
    public static void begin() {
        getSession().beginTransaction();
    }

    /**
     * Подтверждает транзакцию и сохраняет изменения в бд
     */
    public static void commit() {
        getSession().getTransaction().commit();
    }
}

