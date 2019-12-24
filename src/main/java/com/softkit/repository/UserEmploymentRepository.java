package com.softkit.repository;

import com.softkit.database.UserEmployment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEmploymentRepository extends CrudRepository<UserEmployment, Long> {

    default void addUserEmployment(Integer userId, String employmentDescription){}

    default void removeUserEmployment(Integer userId, String employmentDescription){};
}
