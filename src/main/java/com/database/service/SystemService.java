package com.database.service;

import com.database.model.System;
import org.hibernate.Session;

import java.util.List;

public class SystemService {

    public static System findSystemById(int id){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        return session.createQuery("FROM System s WHERE s.id = :id", System.class).setParameter("id", id).getSingleResult();
    }

    public static List<System> findAll(){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        return session.createQuery("FROM System s", System.class).getResultList();
    }

    public static List<System> findAllByUser(int userId){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        return session.createQuery("SELECT s FROM System s WHERE s.user.id = :userId", System.class).setParameter("userId",userId).getResultList();
    }

    public static void save(System system) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(system);
        session.getTransaction().commit();
    }

    public static void update(System system) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(system);
        session.getTransaction().commit();
    }

    public static void delete(System system) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(system);
        session.getTransaction().commit();
    }

}
