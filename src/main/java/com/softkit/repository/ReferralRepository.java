package com.softkit.repository;

import com.softkit.database.UserReferral;
import com.softkit.database.UserSpecialization;
import com.softkit.vo.Specialization;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface ReferralRepository extends CrudRepository<UserReferral, Long> {

    @Query("select us from UserReferral us where us.userId = :userId")
    Set<UserReferral> findUserReferralsByUserId(Integer userId );

    @Query(value = "delete from UserReferral us where us.userId = :userId")
    @Modifying
    @Transactional
    void removeAllUserRefferals(Integer userId);
}
