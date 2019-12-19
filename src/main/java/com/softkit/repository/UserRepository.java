package com.softkit.repository;

import com.softkit.database.UserProfile;
import com.softkit.vo.Step;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserProfile, Integer> {

    Optional<UserProfile> findUserByUserId(Integer userId);

    default UserProfile setNewStep(Integer userId, Step step) {
        Optional<UserProfile> optionalUser = findUserByUserId(userId);
        UserProfile userProfile = optionalUser.orElse(new UserProfile(userId));
        userProfile.setUserStatus(step.getStepIntId());
        save(userProfile);
        return userProfile;
    }


}
