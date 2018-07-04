package com.mamh.jpa.springdata;

import org.springframework.data.repository.Repository;

public interface PeopleRepository extends Repository<People, Integer>{

    /**
     * 通过last name获取people对象
     * @param lastName
     * @return
     */
    People getByLastName(String lastName);

}
