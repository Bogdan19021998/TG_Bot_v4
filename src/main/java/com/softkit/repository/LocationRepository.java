package com.softkit.repository;

import com.softkit.database.UserLocation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<UserLocation, Integer> {

    default void deleteById(Integer id){};
}
