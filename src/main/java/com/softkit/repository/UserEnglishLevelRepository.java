package com.softkit.repository;

import com.softkit.database.User;
import com.softkit.database.UserEnglishLevel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEnglishLevelRepository extends CrudRepository<UserEnglishLevel, Integer> {

}
