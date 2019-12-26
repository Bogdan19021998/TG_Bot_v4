package com.softkit.repository;

import com.softkit.database.UserLocation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LocationRepository extends CrudRepository<UserLocation, Integer> {

    @Transactional
    void deleteByUserId(Integer userId);
}
