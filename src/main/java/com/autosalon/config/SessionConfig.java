package com.autosalon.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.autosalon.model.Employee;
import com.autosalon.model.Showroom;
import com.autosalon.model.User;

public class SessionConfig {

    private static SessionConfig sc;
    final SessionFactory sessionFactory;

    private SessionConfig() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(Showroom.class)
                .configure()
                .buildSessionFactory();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static SessionConfig getInstanceOfSeccionFactory() {
        if (sc == null) sc = new SessionConfig();
        return sc;
    }
}