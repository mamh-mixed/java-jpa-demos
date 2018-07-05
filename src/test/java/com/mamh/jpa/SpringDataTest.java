package com.mamh.jpa;

import com.mamh.jpa.service.PeopleService;
import com.mamh.jpa.springdata.People;
import com.mamh.jpa.springdata.PeopleCrudRepository;
import com.mamh.jpa.springdata.PeopleRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class SpringDataTest {

    private ApplicationContext context = null;
    private PeopleRepository peopleRepository;
    private PeopleService peopleService;
    private PeopleCrudRepository peopleCrudRepository;

    @Before
    public void init() {
        context = new ClassPathXmlApplicationContext("spring.xml");
        peopleRepository = context.getBean(PeopleRepository.class);
        peopleService = context.getBean(PeopleService.class);
        peopleCrudRepository = context.getBean(PeopleCrudRepository.class);
    }

    @Test
    public void testQueryAnnotation1() {
        //peopleRepository.updatePeopleEmail(1, "aa@mage.com");
        peopleService.update(1, "aa@mage.com");

    }

    @Test
    public void testCrud() {
        List<People> peopleList = new ArrayList<>();
        for (int i = 'a'; i < 'z'; i++) {
            People people = new People();
            people.setEmail((char) i + "" + (char) i + "@mage.com");
            people.setLastName((char) i + "" + (char) i);

            peopleList.add(people);
        }

        peopleService.savePeoples(peopleList);

    }

    @Test
    public void testQueryAnnotation() {
        People people = peopleRepository.getMaxIdPeople();
        System.out.println(people);

        List<People> people1 = peopleRepository.getPeople("aa", "aa");
        System.out.println(people1);

        List<People> people2 = peopleRepository.getPeople2("a", "a");//模糊查询
        System.out.println(people2);

        Long total = peopleRepository.getTotal();//本地sql查询
        System.out.println(total);
    }


    @Test
    public void test() {
        People people = peopleRepository.getByLastName("aa");
        System.out.println(people);

    }


    @Test
    public void testDataSource() {
        DataSource dataSource = context.getBean(DataSource.class);
        System.out.println(dataSource);
    }
}
