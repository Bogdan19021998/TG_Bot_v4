package com.softkit.repository;

import com.softkit.database.City;
import com.softkit.database.UserEmployment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends CrudRepository<City, Integer> {

}
