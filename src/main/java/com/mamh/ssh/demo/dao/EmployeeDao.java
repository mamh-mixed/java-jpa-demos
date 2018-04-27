package com.mamh.ssh.demo.dao;

import com.mamh.ssh.demo.entities.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class EmployeeDao {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        System.out.println(sessionFactory);
         return this.sessionFactory.getCurrentSession();
    }


    public List<Employee> getAll() {

        String hql = "FROM Employee";

        List list = getSession().createQuery(hql).list();

        System.out.println(list);
        return list;


    }

}
