package com.mamh.jpa.dao;

import com.mamh.jpa.entities.Person;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class PersonDao {

    /**
     * 如何获取到和当前事务关联的entitymanger
     * 对象呢？
     */
    @PersistenceContext
    private EntityManager entityManager;

    public void save(Person person) {
        entityManager.persist(person);
    }


}
