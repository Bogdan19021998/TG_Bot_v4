package com.softkit.repository;

import com.softkit.database.UserSpecialization;
import com.softkit.vo.Specialization;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface UserSpecialisationsRepository extends CrudRepository<UserSpecialization, Integer> {

    @Query(value = "delete from UserSpecialization us where us.userId = :userId and us.specialization = :specialization")
    @Modifying
    @Transactional
    Integer removeUserSpecialization(Integer userId, Specialization specialization);
}
