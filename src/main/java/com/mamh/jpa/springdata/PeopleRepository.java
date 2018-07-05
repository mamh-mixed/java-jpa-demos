package com.mamh.jpa.springdata;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

//@RepositoryDefinition(domainClass = People.class, idClass = Integer.class)
public interface PeopleRepository extends Repository<People, Integer>,JpaSpecificationExecutor {

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


    //查询id最大的people
    @Query("select p from People p where p.id = (select max(p2.id) from People p2)")
    People getMaxIdPeople();


    @Query("select p from People p where p.lastName = ?1 and p.email = ?2")
    List<People> getPeople(String last, String email);

    @Query("select p from People p where p.lastName = :lastName and p.email = :email")
    List<People> getPeople1(@Param("lastName") String last, @Param("email") String email);

    @Query("select p from People p where p.lastName like %:lastName% and p.email like %:email%")
    List<People> getPeople2(@Param("lastName") String last, @Param("email") String email);


    @Query(value = "select count(id) from  jpa_people",nativeQuery = true)
    Long getTotal();

    @Modifying
    @Query("update People  p set p.email = :email where  p.id = :id")
    void updatePeopleEmail(@Param("id") Integer id,@Param("email") String email);
}
