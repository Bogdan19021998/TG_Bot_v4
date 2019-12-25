package com.softkit.service;

import com.softkit.database.User;
import com.softkit.database.UserTechnology;
import com.softkit.repository.UserTechnologyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class TechnologiesService {

    public final UserTechnologyRepository userTechnologyRepository;

    public HashSet<UserTechnology> findAllUserTechnologies(User user) {
        return new HashSet<>(userTechnologyRepository.findUserTechnologiesByUserId(user.getId()));
    }

    public void addAllTechnologies(User user, @NonNull String[] technologies) {
        for (String technology : technologies) {
            userTechnologyRepository.save(new UserTechnology(user.getId(), technology));
        }
    }

    public void removeAllTechnologies(User user) {
        userTechnologyRepository.removeAllUserTechnologies(user.getId());
    }
}
