package com.softkit.repository;

import com.softkit.database.Specialization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialisationRepository extends CrudRepository<Specialization, Integer> {

}
