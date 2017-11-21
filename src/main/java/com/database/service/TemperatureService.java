package com.database.service;

import com.database.model.Temperature;
import org.hibernate.Session;

import java.util.List;

public class TemperatureService {

    public static Temperature findTemperatureById(int id){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        Temperature temperature = session.createQuery("FROM Temperature t WHERE t.id = :id", Temperature.class).setParameter("id", id).getSingleResult();
        session.getTransaction().commit();
        return temperature;
    }

    public static List<Temperature> findAll(){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        List<Temperature> temperatures = session.createQuery("FROM Temperature t", Temperature.class).getResultList();
        session.getTransaction().commit();
        return temperatures;
    }

    public static void save(Temperature temperature) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(temperature);
        session.getTransaction().commit();
    }

    public static void update(Temperature temperature) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(temperature);
        session.getTransaction().commit();
    }

    public static void delete(Temperature temperature) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(temperature);
        session.getTransaction().commit();
    }

}
