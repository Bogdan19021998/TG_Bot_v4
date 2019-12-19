package com.softkit.repository;

import com.softkit.database.User;
import com.softkit.database.UserEmployment;
import com.softkit.vo.Step;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEmploymentRepository extends CrudRepository<UserEmployment, Integer> {

}
