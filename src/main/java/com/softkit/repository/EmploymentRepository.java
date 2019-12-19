package com.softkit.repository;

import com.softkit.database.Employment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmploymentRepository extends CrudRepository<Employment, Integer> {

}
