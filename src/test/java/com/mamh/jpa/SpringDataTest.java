package com.mamh.jpa;

import com.mamh.jpa.service.PeopleService;
import com.mamh.jpa.springdata.People;
import com.mamh.jpa.springdata.PeopleCrudRepository;
import com.mamh.jpa.springdata.PeoplePagingAndSortingRepository;
import com.mamh.jpa.springdata.PeopleRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class SpringDataTest {

    private ApplicationContext context = null;
    private PeopleRepository peopleRepository;
    private PeopleService peopleService;
    private PeopleCrudRepository peopleCrudRepository;
    private PeoplePagingAndSortingRepository peoplePagingAndSortingRepository;

    @Before
    public void init() {
        context = new ClassPathXmlApplicationContext("spring.xml");
        peopleRepository = context.getBean(PeopleRepository.class);
        peopleService = context.getBean(PeopleService.class);
        peopleCrudRepository = context.getBean(PeopleCrudRepository.class);
        peoplePagingAndSortingRepository = context.getBean(PeoplePagingAndSortingRepository.class);
    }

    @Test
    public void testSorting() {
        int pageNo = 2 - 1;
        int pageSize = 5;
        Sort.Order order1 = new Sort.Order(Sort.Direction.ASC, "id");
        Sort.Order order2 = new Sort.Order(Sort.Direction.DESC, "email");
        Sort sort = Sort.by(order1, order2);

        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        Page<People> page = peoplePagingAndSortingRepository.findAll(pageRequest);

        System.out.println("总记录数： " + page.getTotalElements());
        System.out.println("当前第几页： " + (page.getNumber() + 1));
        System.out.println("当前页面的list： " + page.getContent());
        System.out.println("当前页面的记录数： " + page.getNumberOfElements());

    }

    @Test
    public void testPageing() {
        int pageNo = 1 - 1;
        int pageSize = 5;

        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        Page<People> page = peoplePagingAndSortingRepository.findAll(pageRequest);

        System.out.println("总记录数： " + page.getTotalElements());
        System.out.println("当前第几页： " + (page.getNumber() + 1));
        System.out.println("当前页面的list： " + page.getContent());
        System.out.println("当前页面的记录数： " + page.getNumberOfElements());

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
