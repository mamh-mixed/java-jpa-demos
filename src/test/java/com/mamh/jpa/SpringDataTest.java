package com.mamh.jpa;

import com.mamh.jpa.springdata.People;
import com.mamh.jpa.springdata.PeopleRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;

public class SpringDataTest {

    private ApplicationContext context = null;

    @Before
    public void init() {
        context = new ClassPathXmlApplicationContext("spring.xml");
    }



    @Test
    public void test(){
        PeopleRepository peopleRepository = context.getBean(PeopleRepository.class);
        People people = peopleRepository.getByLastName("aa");
        System.out.println(people);

    }


    @Test
    public void testDataSource(){
        DataSource dataSource = context.getBean(DataSource.class);
        System.out.println(dataSource);
    }
}
