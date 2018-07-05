package com.mamh.jpa.service;

import com.mamh.jpa.springdata.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PeopleService {

    @Autowired
    private PeopleRepository peopleRepository;

    @Transactional
    public void update(Integer id, String email) {
        peopleRepository.updatePeopleEmail(id, email);
    }
}
