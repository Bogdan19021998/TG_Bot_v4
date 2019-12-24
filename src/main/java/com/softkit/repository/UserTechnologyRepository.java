package com.softkit.repository;

import com.softkit.database.UserTechnology;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTechnologyRepository extends CrudRepository<UserTechnology, Integer> {

    default void saveTechnologies(String technologies) {};

}
