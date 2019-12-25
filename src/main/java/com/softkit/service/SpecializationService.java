package com.softkit.service;

import com.softkit.database.User;
import com.softkit.database.UserSpecialization;
import com.softkit.repository.UserSpecialisationsRepository;
import com.softkit.vo.Specialization;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SpecializationService {

    private final UserSpecialisationsRepository userSpecialisationsRepository;

    public HashSet<UserSpecialization> findAllUserSpecialization(User user) {
        return new HashSet<UserSpecialization>(
                userSpecialisationsRepository.findUserSpecializationsByUserId(user.getId()));
    }

    public void addUserSpecialisation(User user, Specialization specialization) {
        userSpecialisationsRepository.save(new UserSpecialization(user.getId(), specialization));
    }

    public void removeUserSpecialisation(User user, Specialization specialization) {
        userSpecialisationsRepository.removeUserSpecialization(user.getId(), specialization);
    }
}
