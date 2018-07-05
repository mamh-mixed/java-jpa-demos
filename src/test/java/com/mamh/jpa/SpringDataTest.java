package com.mamh.jpa;

import com.mamh.jpa.service.PeopleService;
import com.mamh.jpa.springdata.People;
import com.mamh.jpa.springdata.PeopleJpaRepository;
import com.mamh.jpa.springdata.PeoplePagingAndSortingRepository;
import com.mamh.jpa.springdata.PeopleRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SpringDataTest {

    private ApplicationContext context = null;
    private PeopleRepository peopleRepository;
    private PeopleService peopleService;
    private PeoplePagingAndSortingRepository peoplePagingAndSortingRepository;
    private PeopleJpaRepository peopleJpaRepository;

    @Before
    public void init() {
        context = new ClassPathXmlApplicationContext("spring.xml");
        peopleRepository = context.getBean(PeopleRepository.class);
        peoplePagingAndSortingRepository = context.getBean(PeoplePagingAndSortingRepository.class);
        peopleJpaRepository = context.getBean(PeopleJpaRepository.class);

        peopleService = context.getBean(PeopleService.class);

    }

    @Test
    public void testJpaSpecificationExecutor() {
        /**
         * 带查询条件的分页， 条件是 ID 大于 5.
         */
        int pageNo = 1 - 1;
        int pageSize = 5;

        Sort.Order order1 = new Sort.Order(Sort.Direction.ASC, "id");
        Sort.Order order2 = new Sort.Order(Sort.Direction.DESC, "email");
        Sort sort = Sort.by(order1, order2);

        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);

        Specification<People> specification = new Specification<People>() {

            public Predicate toPredicate(Root<People> root, CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {

                Path path = root.get("id");

                Predicate predicate = criteriaBuilder.gt(path, 5);//这个就是  id  大于 5


                return predicate;

            }
        };
        Page page = peopleRepository.findAll(specification, pageRequest);

        System.out.println("总记录数： " + page.getTotalElements());
        System.out.println("当前第几页： " + (page.getNumber() + 1));
        System.out.println("当前页面的list： " + page.getContent());
        System.out.println("当前页面的记录数： " + page.getNumberOfElements());
    }

    @Test
    public void testJpaRepository() {
        People people = new People();
        people.setLastName("xyz");
        people.setEmail("xy@mage.com");
        people.setBirth(new Date());
        //people.setId(27);


        People people1 = peopleJpaRepository.saveAndFlush(people);

        System.out.println(people == people1);

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
