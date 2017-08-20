package com.database.service;

import com.database.model.Sensor;
import org.hibernate.Session;

import java.util.List;

public class SensorService {

    public static Sensor findSensorById(int id){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        return session.createQuery("FROM Sensor s WHERE s.id = :id", Sensor.class).setParameter("id", id).getSingleResult();
    }

    public static List<Sensor> findAll(){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        return session.createQuery("FROM Sensor s", Sensor.class).getResultList();
    }

    public static void save(Sensor sensor) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(sensor);
        session.getTransaction().commit();
    }

    public static void update(Sensor sensor) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(sensor);
        session.getTransaction().commit();
    }

    public static void delete(Sensor sensor) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(sensor);
        session.getTransaction().commit();
    }

}
