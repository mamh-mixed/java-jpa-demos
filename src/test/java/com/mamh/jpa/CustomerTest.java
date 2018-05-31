package com.mamh.jpa;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

public class CustomerTest {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction transaction;

    @Before
    public void init() {
        String name = "jpa-1";
        entityManagerFactory = Persistence.createEntityManagerFactory(name);

        entityManager = entityManagerFactory.createEntityManager();

        transaction = entityManager.getTransaction();

        transaction.begin();
    }

    @After
    public void destroy() {
        transaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    public void testFind() {
        Customer customer = entityManager.find(Customer.class, 1);
        System.out.println(customer);
    }

    @Test
    public void testRef() {
        Customer customer = entityManager.getReference(Customer.class, 1);
        System.out.println("----------------------");
        System.out.println(customer);

    }

    @Test
    public void testRemove() {
        //Customer customer = new Customer();
        //customer.setId(123);
        Customer customer = entityManager.find(Customer.class, 1);
        entityManager.remove(customer);

        //System.out.println(customer);

        //entityManager.persist(customer);
    }

    @Test
    public void testMerge() {
        Customer customer = new Customer();
        customer.setAge(12);
        customer.setLastName("Tom......");
        customer.setEmail("email.....");
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());

        Customer merge = entityManager.merge(customer);

        System.out.println(customer);
        System.out.println(merge);
    }

    @Test
    public void testPersist() {
        Customer customer = new Customer();
        customer.setAge(12);
        customer.setLastName("jerry1......");
        customer.setEmail("email.....");
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());
        //customer.setId(123);

        entityManager.persist(customer);


    }
}
