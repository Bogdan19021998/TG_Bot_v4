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
        return new HashSet<>( userEmploymentRepository.findUserEmploymentsByUserId( user.getId() ));
    }

    public void addUserEmployment(User user, Employment employment) {
        userEmploymentRepository.save( new UserEmployment(user.getId(), employment ));
    }

    public void removeUserEmployment(User user, Employment employment) {
        userEmploymentRepository.removeUserEmployment(user.getId(), employment);
    }

    public void removeAllUserEmployments(User user) {
        userEmploymentRepository.removeAllUserEmployments( user.getId() );
    }
}
