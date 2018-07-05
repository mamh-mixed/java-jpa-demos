package com.mamh.jpa.springdata;

import org.springframework.data.repository.Repository;

public interface PeopleRepo extends PeopleDao, Repository<People, Integer> {
}
