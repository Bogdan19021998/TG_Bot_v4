package com.softkit.repository;

import com.softkit.database.UserStatus;
import com.softkit.vo.Step;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserStatusRepository extends CrudRepository<UserStatus,Integer>{

    Optional<UserStatus> findUserStatusByStep (Step step);

}
