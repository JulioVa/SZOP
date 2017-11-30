package com.database.service;

import com.database.model.User;
import org.hibernate.Session;

import java.util.List;

public class UserService {
    public static User findUserById(int id){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        User user = session.createQuery("FROM User u WHERE u.id = :id", User.class).setParameter("id", id).getSingleResult();
        session.getTransaction().commit();
        return user;
    }

    public static User findUserByEmail(String email){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        User user = session.createQuery("FROM User u WHERE u.email = :email", User.class).setParameter("email", email).uniqueResultOptional().orElse(null);
        session.getTransaction().commit();
        return user;
    }

    public static List<User> findAll(){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        List<User> users = session.createQuery("FROM User u", User.class).getResultList();
        session.getTransaction().commit();
        return users;
    }

    public static int save(User user) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        int id = (int) session.save(user);
        session.getTransaction().commit();
        return id;
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
