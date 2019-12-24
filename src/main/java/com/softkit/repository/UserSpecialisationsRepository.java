package com.softkit.repository;

import com.softkit.database.UserSpecialization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSpecialisationsRepository extends CrudRepository<UserSpecialization, Integer> {

    default void addUserSpecialisation(Integer userId, String specialisationDescription){};

    default void removeUserSpecialisation(Integer userId, String specialisationDescription){};

}
