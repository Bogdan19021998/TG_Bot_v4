package com.softkit.service;

import com.softkit.database.User;
import com.softkit.database.UserLocation;
import com.softkit.repository.LocationRepository;
import com.softkit.repository.UserFieldsSetter;
import com.softkit.repository.UserRepository;
import com.softkit.vo.City;
import com.softkit.vo.EnglishLevel;
import com.softkit.vo.Experience;
import com.softkit.vo.Step;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final EmploymentsService employmentsService;
    private final ReferralService referralService;
    private final SpecializationService specializationService;
    private final TechnologiesService technologiesService;


    public User addNewUser(Integer telegramId) {
        User user = new User(telegramId);
        user.setStep(Step.START);
        return userRepository.save(user);
    }

    public void removeUser(User user) {
        employmentsService.removeAllUserEmployments(user);
        technologiesService.removeAllTechnologies(user);
        referralService.removeAllUserReferrals(user);
        specializationService.removeAllUserSpecialisations(user);
        locationRepository.deleteById( user.getId() );
        userRepository.deleteById(user.getId());
    }
}
