package com.database.service;

import com.database.model.Schema;
import org.hibernate.Session;

import java.util.List;

public class SchemaService {
    public static Schema findSchemaById(int id){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        return session.createQuery("FROM Schema s WHERE s.id = :id", Schema.class).setParameter("id", id).getSingleResult();
    }

    public static List<Schema> findAll(){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        return session.createQuery("FROM Schema s", Schema.class).getResultList();
    }

    public static void save(Schema schema) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(schema);
        session.getTransaction().commit();
    }

    public static void update(Schema schema) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(schema);
        session.getTransaction().commit();
    }

    public static void delete(Schema schema) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(schema);
        session.getTransaction().commit();
    }

}
