package com.mamh.jpa.service;

import com.mamh.jpa.dao.PersonDao;
import com.mamh.jpa.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class PersonService {

    @Autowired
    private PersonDao personDao;

    @Transactional
    public void savePerson(Person p){
        personDao.save(p);
    }


}
