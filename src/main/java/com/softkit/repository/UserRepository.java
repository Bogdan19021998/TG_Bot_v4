package com.softkit.repository;

import com.softkit.database.User;
import com.softkit.vo.Step;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findUserByUserId(Integer userId);

    default User setNewStep(Integer userId, Step step) {
        return null;
    }

}
