package com.softkit.repository;

import com.softkit.database.Status;
import com.softkit.vo.Step;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserStatusRepository extends CrudRepository<Status,Integer>{

    Optional<Status> findUserStatusByStep (Step step);

}
