package com.database.service;

import com.database.model.System;
import org.hibernate.Session;

import java.util.List;

public class SystemService {

    public static System findSystemById(int id){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        System system = session.createQuery("FROM System s WHERE s.id = :id", System.class).setParameter("id", id).getSingleResult();
        session.getTransaction().commit();
        return system;
    }

    public static List<System> findAll(){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        List<System> systems = session.createQuery("FROM System s", System.class).getResultList();
        session.getTransaction().commit();
        return systems;
    }

    public static List<System> findAllByUser(int userId){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        List<System> systems = session.createQuery("SELECT s FROM System s WHERE s.user.id = :userId", System.class).setParameter("userId",userId).getResultList();
        session.getTransaction().commit();
        return systems;
    }

    public static System findSystemByName(String name){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        System system = session.createQuery("FROM System s WHERE s.name = :name", System.class).setParameter("name", name).uniqueResultOptional().orElse(null);
        session.getTransaction().commit();
        return system;
    }

    public static int save(System system) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        int id = (int) session.save(system);
        session.getTransaction().commit();
        return id;
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
