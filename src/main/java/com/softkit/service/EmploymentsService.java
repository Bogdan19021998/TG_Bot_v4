package com.softkit.service;

import com.softkit.database.User;
import com.softkit.database.UserEmployment;
import com.softkit.repository.UserEmploymentRepository;
import com.softkit.vo.Employment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class EmploymentsService {

    private final UserEmploymentRepository userEmploymentRepository;

    public HashSet<UserEmployment> findAllUserEmployments(User user) {
        return new HashSet<UserEmployment>(userEmploymentRepository.findUserEmploymentsByUserId(user.getId()));
    }

    public void addUserEmployment(User user, Employment emplyment) {
        userEmploymentRepository.save( new UserEmployment(user.getId(), emplyment));
    }

    public void removeUserEmployment(User user, Employment emplyment) {
        userEmploymentRepository.removeUserEmployment( user.getId(), emplyment );
    }
}
