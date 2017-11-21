package com.database.service;

import com.database.model.Schema;
import org.hibernate.Session;

import java.util.List;

public class SchemaService {
    public static Schema findSchemaById(int id){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        Schema schema = session.createQuery("FROM Schema s WHERE s.id = :id", Schema.class).setParameter("id", id).getSingleResult();
        session.getTransaction().commit();
        return schema;
    }

    public static List<Schema> findAll(){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        List<Schema> schemas = session.createQuery("FROM Schema s", Schema.class).getResultList();
        session.getTransaction().commit();
        return schemas;
    }

    public static List<Schema> findSchemasByUserId(int userId){
        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        List<Schema> schemas = session.createQuery("FROM Schema s WHERE s.user.id = :userId", Schema.class).setParameter("userId", userId).getResultList();
        session.getTransaction().commit();
        return schemas;
    }

    public static int save(Schema schema) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        int id = (int) session.save(schema);
        session.getTransaction().commit();
        return id;
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
