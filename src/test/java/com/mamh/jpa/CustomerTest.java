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
    public void testOneToMany1() {
        Customer customer = entityManager.find(Customer.class, 7);
        System.out.println(customer);

    }

    @Test
    public void testOneToMany() {
        Customer customer = new Customer();
        customer.setAge(112);
        customer.setLastName("zz");
        customer.setEmail("zzzzz");
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());

        Order order = new Order();
        order.setOderName("o-ff-1");
        Order order1 = new Order();
        order1.setOderName("o-ff-2");

        customer.getOrders().add(order);
        customer.getOrders().add(order1);

        entityManager.persist(customer);
        entityManager.persist(order);
        entityManager.persist(order1);

    }

    @Test
    public void testManyToOne2() {
        Customer customer = entityManager.find(Customer.class, 6);
        entityManager.remove(customer);


    }

    @Test
    public void testManyToOne1() {
        Order order = entityManager.find(Order.class, 1);
        System.out.println(order);
    }

    @Test
    public void testManyToOne() {
        //多对一映射关联关系
        //一个customer可以有多个order。
        Customer customer = new Customer();
        customer.setAge(112);
        customer.setLastName("b");
        customer.setEmail("b");
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());

        Order order = new Order();
        order.setOderName("o-ff-1");
        Order order1 = new Order();
        order1.setOderName("o-ff-2");
        //order.setCustomer(customer);
        //order1.setCustomer(customer);
        entityManager.persist(customer);

        entityManager.persist(order);
        entityManager.persist(order1);
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
    public void testMerge1() {
        Customer customer = new Customer();
        customer.setAge(12);
        customer.setLastName("Tom......");
        customer.setEmail("email..1111.000..");
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());
        customer.setId(100);

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
