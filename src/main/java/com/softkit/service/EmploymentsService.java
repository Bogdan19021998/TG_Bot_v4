package com.softkit.service;

import com.softkit.database.User;
import com.softkit.database.UserEmployment;
import com.softkit.vo.Employment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class EmploymentsService {

    public HashSet<UserEmployment> findAllUserEmployments(User user) {
//        return new HashSet<UserSpecialization>(
//                userSpecialisationsRepository.findUserSpecializationsByUserId(user.getId()));
        return new HashSet<>();
    }

    public void addUserEmployment(User user, Employment employment) {
    }

    public void removeUserEmployment(User user, Employment employment) {
    }
}
