package com.autosalon.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.autosalon.config.SessionConfig;
import com.autosalon.model.Employee;
import com.autosalon.model.Showroom;

public class ShowroomDAO {

    private SessionConfig sc;

    public ShowroomDAO() {
        sc = SessionConfig.getInstanceOfSeccionFactory();
    }

    public List<Showroom> getShowrooms() {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Showroom> salons = session.createQuery("FROM Showroom", Showroom.class).getResultList();
        transaction.commit();
        session.close();
        return salons;
    }

    public Set<Showroom> getShowroomsWithEmployees() {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Set<Showroom> showrooms = new HashSet<>(session
                .createQuery("FROM Showroom s LEFT JOIN FETCH s.employees", Showroom.class)
                .getResultList());
        transaction.commit();
        session.close();
        return showrooms;
    }

    public List<Employee> getEmployeesByShowroom(Showroom showroom) {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Showroom salon = session.get(Showroom.class, showroom.getShowroomId());
        Hibernate.initialize(salon.getEmployees());
        List<Employee> emps = salon.getEmployees();
        transaction.commit();
        session.close();
        return emps;
    }

    // найти салон по ID
    public Showroom findShowroomByID(String showroomID) {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Showroom showroom = session.get(Showroom.class, showroomID);
        transaction.commit();
        session.close();
        if (showroom == null) {
            throw new NullPointerException("Салон не найден");
        }
        return showroom;
    }

    // добавить салон
    public Showroom addShowroom(Showroom salon) {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.persist(salon);
        transaction.commit();
        session.close();
        return salon;
    }

    // удалить салон (
    public void deleteShowroom(String id) {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Showroom showroom = session.get(Showroom.class, id);
        if (showroom == null) {
            transaction.rollback();
            session.close();
            throw new NullPointerException("Салон не найден");
        }
        session.remove(showroom);
        transaction.commit();
        session.close();
    }

    // салоны в городе
    public List<Showroom> findShowroomsByCity(String city) {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Showroom> salons = session
                .createQuery("FROM Showroom s WHERE lower(s.address) LIKE lower(:city)", Showroom.class)
                .setParameter("city", "%" + city + "%")
                .getResultList();
        transaction.commit();
        session.close();
        return salons;
    }
}