package com.mamh.jpa;

import com.mamh.jpa.entities.Person;
import com.mamh.jpa.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.SQLException;

public class JPATest {

    private ApplicationContext context = null;
    private PersonService personService;

    @Before
    public void init() {
        context = new ClassPathXmlApplicationContext("spring.xml");
        personService = context.getBean(PersonService.class);
    }

    @Test
    public void testPersonService(){
        Person p1 = new Person();
        p1.setAge(12);
        p1.setEmail("aa@163.com");
        p1.setLastName("aa");

        Person p2 = new Person();
        p2.setAge(13);
        p2.setEmail("BB@163.com");
        p2.setLastName("bb");

        System.out.println(personService.getClass().getName());
        personService.savePerson(p1);
        personService.savePerson(p2);
    }

    @Test
    public void testDataSource() {
        DataSource dataSource = context.getBean(DataSource.class);
        EntityManagerFactory entityManagerFactory = (EntityManagerFactory) context.getBean("entityManagerFactory");

        try {
            System.out.println(dataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
