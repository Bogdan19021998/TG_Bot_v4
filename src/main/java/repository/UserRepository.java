package repository;

import database.User;
import vo.Step;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findUserById(Long userId);

    User setNewStep(Long userId, Step step);

}
