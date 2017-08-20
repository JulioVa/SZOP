package com.database.service;

import com.database.model.User;
import org.hibernate.Session;

import java.util.List;

public class UserService {
    public static User findUserById(int id){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        return session.createQuery("FROM User u WHERE u.id = :id", User.class).setParameter("id", id).getSingleResult();
    }

    public static List<User> findAll(){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        return session.createQuery("FROM User u", User.class).getResultList();
    }

    public static void save(User user) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
    }

    public static void update(User user) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(user);
        session.getTransaction().commit();
    }

    public static void delete(User user) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(user);
        session.getTransaction().commit();
    }

}
