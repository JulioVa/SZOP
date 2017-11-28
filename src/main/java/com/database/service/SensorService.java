package com.database.service;

import com.database.model.Sensor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import java.util.List;

public class SensorService {
    private static final Logger LOGGER = LogManager.getLogger("SensorService");

    public static Sensor findSensorById(int id){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        Sensor sensor = session.createQuery("FROM Sensor s WHERE s.id = :id", Sensor.class).setParameter("id", id).getSingleResult();
        session.getTransaction().commit();
        return sensor;
    }

    public static Sensor findSensorBySensorId(String sensorId){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        Sensor sensor = session.createQuery("FROM Sensor s WHERE s.sensorId = :sensorId", Sensor.class).setParameter("sensorId", sensorId).getSingleResult();
        session.getTransaction().commit();
        return sensor;
    }

    public static List<Sensor> findAll(){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        List<Sensor> sensors = session.createQuery("FROM Sensor s", Sensor.class).getResultList();
        session.getTransaction().commit();
        return sensors;
    }

    public static List<Sensor> findAllBySchema(int schemaId){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        List<Sensor> sensors = session.createQuery("SELECT s FROM Sensor s WHERE s.schema.id = :schemaId", Sensor.class).setParameter("schemaId",schemaId).getResultList();
        session.getTransaction().commit();
        return sensors;
    }

    public static List<Sensor> findAllBySystem(int systemId){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        List<Sensor> sensors = session.createQuery("SELECT s FROM Sensor s WHERE s.system.id = :systemId", Sensor.class).setParameter("systemId",systemId).getResultList();
        session.getTransaction().commit();
        return sensors;
    }

    public static List<Sensor> findAllByType(int sensorType){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        List<Sensor> sensors = session.createQuery("SELECT s FROM Sensor s WHERE s.type = :sensorType",Sensor.class).setParameter("sensorType",sensorType).getResultList();
        session.getTransaction().commit();
        return sensors;
    }

    public static List<Sensor> findAllByTypeForUser(int sensorType, int userId){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        List<Sensor> sensors = session.createQuery("SELECT s FROM Sensor s WHERE s.type = :sensorType AND s.system.user.id = :userId",Sensor.class).setParameter("sensorType", sensorType).setParameter("userId", userId).getResultList();
        session.getTransaction().commit();
        return sensors;
    }

    public static Sensor findBySensorIdAndSystemId(String sensorId, int systemId){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        try {
            return session.createQuery("FROM Sensor s WHERE s.sensorId = :sensorId AND s.system.id = :systemId", Sensor.class).setParameter("sensorId", sensorId).setParameter("systemId", systemId).getSingleResult();
        } catch (final NoResultException e) {
            return null;
        } finally {
            session.getTransaction().commit();
        }
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
        session.update(sensor);
        session.getTransaction().commit();
    }

    public static void delete(Sensor sensor) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(sensor);
        session.getTransaction().commit();
    }

}
