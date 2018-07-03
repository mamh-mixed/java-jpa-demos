package com.mamh.jpa;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.hibernate.jpa.QueryHints;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

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
    public void testSubQuery() {
        String jsql = "select o from Order  o where o.customer = (select c from  Customer c where c.lastName = ?)";

        Query query = entityManager.createQuery(jsql);
        query.setParameter(1, "aa");
        List list = query.getResultList();

        System.out.println(list);
    }

    @Test
    public void testLeftOuterJoinFetch1() {
        String jsql = "from Customer  c left outer join  c.orders where c.id=?";
        //String jsql = "from Customer  c   where c.id=?";
        Query query = entityManager.createQuery(jsql);
        query.setParameter(1, 10);
        List list = query.getResultList();

        System.out.println(list);
    }

    @Test
    public void testLeftOuterJoinFetch() {
        String jsql = "from Customer  c left outer join fetch c.orders where c.id=?";
        //String jsql = "from Customer  c   where c.id=?";
        Query query = entityManager.createQuery(jsql);
        query.setParameter(1, 10);
        Customer customer = (Customer) query.getSingleResult();
        System.out.println(customer.getLastName());
        System.out.println(customer.getOrders().size());
    }


    @Test
    public void testGroupBy() {
        String jsql = "select o.customer FROM  Order o group by o.customer HAVING count(o.id)>2";
        Query query = entityManager.createQuery(jsql);
        List resultList = query.getResultList();
        System.out.println(resultList);
    }

    @Test
    public void testOrderBy() {
        String jsql = "from Customer c where c.age > ? order by c.id asc";
        Query query = entityManager.createQuery(jsql);
        query.setParameter(1, 1);
        List resultList = query.getResultList();
        System.out.println(resultList);
    }


    @Test
    public void testQueryCache() {
        String jpsql = "select new Customer(c.lastName,c.age) FROM Customer  c where c.age > ?";

        //Query query = entityManager.createQuery(jpsql);
        Query query = entityManager.createQuery(jpsql).setHint(QueryHints.HINT_CACHEABLE, true);
        query.setParameter(1, 1);
        List resultList = query.getResultList();
        System.out.println(resultList);

        //query = entityManager.createQuery(jpsql);  //默认是发送2条sql查询语句的
        query = entityManager.createQuery(jpsql).setHint(QueryHints.HINT_CACHEABLE, true);  //默认是发送2条sql查询语句的
        query.setParameter(1, 1);
        resultList = query.getResultList();
        System.out.println(resultList);

    }

    @Test
    public void testNativeQuery() {
        String sql = "SELECT age FROM jpa_customer WHERE id = ?";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, 3);
        List resultList = query.getResultList();
        System.out.println(resultList);
    }

    @Test
    public void testNamedQuery() {
        Query query = entityManager.createNamedQuery("testNamedQuery");
        query.setParameter(1, 3);
        List resultList = query.getResultList();
        System.out.println(resultList);
    }

    @Test
    public void testJpql() {
        String jpsql = "select new Customer(c.lastName,c.age) FROM Customer  c where c.age > ?";

        Query query = entityManager.createQuery(jpsql);
        query.setParameter(1, 1);
        List resultList = query.getResultList();
        System.out.println(resultList);

    }

    @Test
    public void testCache1() {
        Customer customer = entityManager.find(Customer.class, 3); //这里一级缓存为什么没有生效呢？？？？

        System.out.println(customer.getLastName());
        transaction.commit();
        entityManager.close();
        System.out.println("");

        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
        transaction.begin();

        Customer customer1 = entityManager.find(Customer.class, 3);
        System.out.println(customer1.getLastName());

    }

    @Test
    public void testCache() {
        Customer customer = entityManager.find(Customer.class, 3); //这里一级缓存为什么没有生效呢？？？？

        System.out.println(customer.getLastName());

        System.out.println("");
        Customer customer1 = entityManager.find(Customer.class, 3);
        System.out.println(customer1.getLastName());

    }


    @Test
    public void testManyToManyFind() {
//        Item item = entityManager.find(Item.class, 2);
//        System.out.println(item.getItemName());
//        System.out.println(item.getCategories().size());

        Category category = entityManager.find(Category.class, 1);
        System.out.println(category.getCategoryName());
        System.out.println(category.getItems().size());
    }

    @Test
    public void testManyToManySave() {
        Item i1 = new Item();
        i1.setItemName("i - 1");
        Item i2 = new Item();
        i2.setItemName("i - 2");


        Category c1 = new Category();
        c1.setCategoryName("c - 1");
        Category c2 = new Category();
        c2.setCategoryName("c - 2");

        i1.getCategories().add(c1);
        i1.getCategories().add(c2);

        i2.getCategories().add(c1);
        i2.getCategories().add(c2);

        c1.getItems().add(i1);
        c1.getItems().add(i2);

        c2.getItems().add(i1);
        c2.getItems().add(i2);

        entityManager.persist(i1);
        entityManager.persist(i2);
        entityManager.persist(c1);
        entityManager.persist(c2);
    }


    @Test
    public void testOnetoOneFind1() {
        //这里获取不维护关联关系的一方，这里 (fetch = FetchType.LAZY)设置不设置这个都是使用的左外链接。发送2条sql语句。

        Manager manager = entityManager.find(Manager.class, 3);
        System.out.println(manager.getMgrName());
        System.out.println(manager.getDeptartment().getClass().getName());
    }

    @Test
    public void testOnetoOneFind() {
        //加上    @OneToOne(fetch = FetchType.LAZY) 就不会使用左外链接了,这样只会发送一条sql语句。
        Department department = entityManager.find(Department.class, 3);
        System.out.println(department.getDeptName());
        System.out.println(department.getManager().getClass().getName());

    }

    @Test
    public void testOnetoOneSave() {
        Manager manager = new Manager();
        manager.setMgrName("bright.ma");
        Department department = new Department();
        department.setDeptName("scm");
        department.setManager(manager);
        manager.setDeptartment(department);

        //先保存不维护关联关系的那一方
        entityManager.persist(manager);
        entityManager.persist(department);
    }

    /**
     * 双向一对多关联关系
     */
    @Test
    public void testOneToMany2() {
        Customer customer = new Customer();
        customer.setAge(112);
        customer.setLastName("aa");
        customer.setEmail("aa");
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());

        Order order = new Order();
        order.setOderName("o-ff-1");
        Order order1 = new Order();
        order1.setOderName("o-ff-2");

        customer.getOrders().add(order);
        customer.getOrders().add(order1);
        order1.setCustomer(customer);
        order.setCustomer(customer);

        entityManager.persist(customer);
        entityManager.persist(order);
        entityManager.persist(order1);

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
