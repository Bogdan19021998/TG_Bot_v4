package com.softkit.service;

import com.softkit.database.User;
import com.softkit.database.UserSpecialization;
import com.softkit.repository.UserSpecialisationsRepository;
import com.softkit.vo.Emplyment;
import com.softkit.vo.Specialization;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class EmploymentsService {


    public HashSet<UserSpecialization> findAllUserEmployments(User user) {
//        return new HashSet<UserSpecialization>(
//                userSpecialisationsRepository.findUserSpecializationsByUserId(user.getId()));
        return new HashSet<>();
    }

    public void addUserEmployment(User user, Emplyment emplyment) {
    }

    public void removeUserEmployment(User user, Emplyment emplyment) {
    }
}
