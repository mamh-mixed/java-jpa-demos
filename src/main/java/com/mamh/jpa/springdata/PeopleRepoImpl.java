package com.mamh.jpa.springdata;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class PeopleRepoImpl implements PeopleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void test() {
        People people = entityManager.find(People.class, 1);
        System.out.println(people);
    }
}
