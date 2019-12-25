package com.softkit.repository;

import com.softkit.database.UserTechnology;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public interface UserTechnologyRepository extends CrudRepository<UserTechnology, Integer> {

    @Query("select us from UserTechnology us where us.userId = :userId")
    Set<UserTechnology> findUserTechnologiesByUserId(Integer userId );

    @Query(value = "delete from UserTechnology us where us.userId = :userId")
    @Modifying
    @Transactional
    Integer removeAllUserTechnologies( Integer userId );
}
