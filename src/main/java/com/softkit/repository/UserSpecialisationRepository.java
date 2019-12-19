package com.softkit.repository;

import com.softkit.database.UserSpecialization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSpecialisationRepository extends CrudRepository<UserSpecialization, Integer> {

}
