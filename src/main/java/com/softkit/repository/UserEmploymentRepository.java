package com.softkit.repository;

import com.softkit.database.UserEmployment;
import com.softkit.vo.Employment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public interface UserEmploymentRepository extends CrudRepository<UserEmployment, Integer> {


    @Query("select us from UserEmployment us where us.userId = :userId")
    Set<UserEmployment> findUserEmploymentsByUserId(Integer userId );

    @Query(value = "delete from UserEmployment us where us.userId = :userId and us.employment = :employment")
    @Modifying
    @Transactional
    void removeUserEmployment( Integer userId, Employment employment);

    @Query(value = "delete from UserEmployment us where us.userId = :userId")
    @Modifying
    @Transactional
    void removeAllUserEmployments(Integer userId);
}
