package com.mamh.jpa.service;

import com.mamh.jpa.springdata.People;
import com.mamh.jpa.springdata.PeopleCrudRepository;
import com.mamh.jpa.springdata.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PeopleService {

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private PeopleCrudRepository peopleCrudRepository;

    @Transactional
    public void savePeoples(List<People> peoples) {
        peopleCrudRepository.saveAll(peoples);

    }

    @Transactional
    public void update(Integer id, String email) {
        peopleRepository.updatePeopleEmail(id, email);
    }
}
