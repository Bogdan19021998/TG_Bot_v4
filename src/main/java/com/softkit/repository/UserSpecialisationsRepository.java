package com.softkit.repository;

import com.softkit.database.UserSpecializations;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSpecialisationsRepository extends CrudRepository<UserSpecializations, Integer> {

}
