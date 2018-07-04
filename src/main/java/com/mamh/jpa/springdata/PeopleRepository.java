package com.mamh.jpa.springdata;

import org.springframework.data.repository.Repository;

import java.util.Date;
import java.util.List;

//@RepositoryDefinition(domainClass = People.class, idClass = Integer.class)
public interface PeopleRepository extends Repository<People, Integer> {

    /**
     * 通过last name获取people对象
     *
     * @param lastName
     * @return
     */
    People getByLastName(String lastName);


    //where lastName like ?% and id < ?
    List<People> getByLastNameStartingWithAndIdLessThan(String lastName, Integer id);

    //where lastName like %? and id < ?
    List<People> getByLastNameEndingWithAndIdLessThan(String lastName, Integer id);

    //where email in (?, ?, ?)  or birth < ?
    List<People> getByEmailInOrBirthLessThan(List<String> emails, Date birth);
}
