package com.autosalon.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.autosalon.config.SessionConfig;
import com.autosalon.model.Employee;
import com.autosalon.model.Showroom;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class EmployeeDAO {

    private SessionConfig sc;

    public EmployeeDAO() {
        sc = SessionConfig.getInstanceOfSeccionFactory();
    }

    public void addEmployee(Employee employee) {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.persist(employee);
        transaction.commit();
        session.close();
    }

    public Employee getEmployeeById(Integer id) {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Employee employee = session.get(Employee.class, id);
        transaction.commit();
        session.close();
        return employee;
    }

    public List<Employee> getEmployees() {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Employee> employees = session.createQuery("FROM Employee", Employee.class).getResultList();
        transaction.commit();
        session.close();
        return employees;
    }

    public void removeEmployee(Integer id) {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Employee employee = session.get(Employee.class, id);
        session.remove(employee);
        transaction.commit();
        session.close();
    }

    // перевести сотрудника в другой салон
    public Employee updateEmployeesShowroom(Integer empId, Showroom showroom) {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Employee employee = session.get(Employee.class, empId);
        employee.setShowroom(showroom);
        transaction.commit();
        session.close();
        return employee;
    }

    // сотрудники по должности
    public List<Employee> findByPosition(String position) {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Employee> emps = session
                .createQuery("FROM Employee e WHERE lower(e.position) = lower(:pos)", Employee.class)
                .setParameter("pos", position)
                .getResultList();
        transaction.commit();
        session.close();
        return emps;
    }

    // стаж >= N, по убыванию
    public List<Employee> findByExperienceAtLeastCriteria(Integer experience) {
        Session session = sc.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Employee> criteria = cb.createQuery(Employee.class);
        Root<Employee> root = criteria.from(Employee.class);
        criteria.select(root)
                .where(cb.ge(root.get("experience"), experience))
                .orderBy(cb.desc(root.get("experience")));
        List<Employee> emps = session.createQuery(criteria).getResultList();
        transaction.commit();
        session.close();
        return emps;
    }
}