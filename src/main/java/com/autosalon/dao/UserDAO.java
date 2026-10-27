package com.autosalon.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.autosalon.config.SessionConfig;
import com.autosalon.model.User;

import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class UserDAO {

    private SessionConfig sc;

    public UserDAO() {
        sc = SessionConfig.getInstanceOfSeccionFactory();
    }

    public User AuthUser(String username, String password) throws NoResultException {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> criteria = cb.createQuery(User.class);
            Root<User> root = criteria.from(User.class);
            criteria.where(cb.and(
                    cb.equal(root.get("username"), username),
                    cb.equal(root.get("password"), password)));
            User user = session.createQuery(criteria).getSingleResult();
            Hibernate.initialize(user.getEmployee());
            return user;
        } finally {
            transaction.commit();
            session.close();
        }
    }

    public void updatePassword(Integer id, String password) {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.createMutationQuery("update User u SET u.password = :pwd WHERE u.userId = :id")
                .setParameter("pwd", password)
                .setParameter("id", id)
                .executeUpdate();
        transaction.commit();
        session.close();
    }

    public List<User> getUsers() {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<User> users = session.createQuery("FROM User", User.class).getResultList();
        transaction.commit();
        session.close();
        return users;
    }

    // ЗАДАНИЕ 5: найти юзера по никнейму
    public User getUserByUsername(String username) throws NoResultException {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        try {
            return session.createQuery("FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } finally {
            transaction.commit();
            session.close();
        }
    }

    public User addUser(User user) {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.merge(user);
        transaction.commit();
        session.close();
        return user;
    }

    public User getUserByID(Integer id) {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        User user = session.find(User.class, id);
        transaction.commit();
        session.close();
        return user;
    }

    public void removeUser(Integer id) {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        User user = session.find(User.class, id);
        session.remove(user);
        transaction.commit();
        session.close();
    }
}