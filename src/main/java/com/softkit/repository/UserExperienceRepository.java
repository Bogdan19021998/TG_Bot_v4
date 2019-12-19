package com.softkit.repository;

import com.softkit.database.User;
import com.softkit.database.UserExperience;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserExperienceRepository extends CrudRepository<UserExperience, Integer> {

}
