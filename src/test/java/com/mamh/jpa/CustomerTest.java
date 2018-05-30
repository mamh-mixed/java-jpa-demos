package com.mamh.jpa;

import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

public class CustomerTest {

    @Test
    public void test() {
        String name = "jpa-1";
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(name);

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setAge(12);
        customer.setLastName("jerry1......");
        customer.setEmail("email.....");
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());

        entityManager.persist(customer);


        transaction.commit();

        entityManager.close();

        entityManagerFactory.close();

    }
}
