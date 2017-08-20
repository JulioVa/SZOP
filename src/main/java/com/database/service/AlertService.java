package com.database.service;

import com.database.model.Alert;
import org.hibernate.Session;

import java.util.List;

public class AlertService {

    public static Alert findAlertById(int id){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        return session.createQuery("FROM Alert a WHERE a.id = :id", Alert.class).setParameter("id", id).getSingleResult();
    }

    public static List<Alert> findAll(){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        return session.createQuery("FROM Alert a", Alert.class).getResultList();
    }

    public static void save(Alert alert) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(alert);
        session.getTransaction().commit();
    }

    public static void update(Alert alert) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(alert);
        session.getTransaction().commit();
    }

    public static void delete(Alert alert) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(alert);
        session.getTransaction().commit();
    }

}
