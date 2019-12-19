package com.softkit.repository;

import com.softkit.database.EnglishLevel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnglishLevelRepository extends CrudRepository<EnglishLevel, Integer> {

}
