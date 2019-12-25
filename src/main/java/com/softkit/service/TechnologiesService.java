package com.softkit.service;

import com.softkit.database.User;
import com.softkit.database.UserTechnology;
import com.softkit.repository.UserTechnologyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TechnologiesService {

    public final UserTechnologyRepository userTechnologyRepository;

    public HashSet<UserTechnology> findAllUserTechnologies(User user) {
        return new HashSet<>(userTechnologyRepository.findUserTechnologiesByUserId(user.getId()));
    }

    public void addAllTechnologies(User user, String[] technologies) {
        if (technologies != null) {

            Set<UserTechnology> userTechnologiesSet = new LinkedHashSet<>(technologies.length);

            int size = technologies.length;

            for (int i = 0; i < technologies.length; i++) {

               userTechnologyRepository.save(new UserTechnology(user.getId(), technologies[i]) );
            }

        }
    }

    public void removeAllTechnologies(User user) {
        userTechnologyRepository.removeAllUserTechnologies(user.getId());
    }
}
