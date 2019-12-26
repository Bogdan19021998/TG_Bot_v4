package com.softkit.service;

import com.softkit.database.User;
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
public class UserService implements UserFieldsSetter {

    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    private final EmploymentsService employmentsService;
    private final ReferralService referralService;
    private final SpecializationService specializationService;
    private final TechnologiesService technologiesService;

    public User addUserAndSetFirstStep(Integer telegramId) {
        User user = new User(telegramId);
        user.setStep(Step.START);
        return user;
    }

    public void removeUser(User user) {
        employmentsService.removeAllUserEmployments(user);
        technologiesService.removeAllTechnologies(user);
        referralService.removeAllUserReferrals(user);
        specializationService.removeAllUserSpecialisations(user);
        locationRepository.deleteById( user.getId() );
        userRepository.deleteById(user.getId());
    }

    @Override
    public void setCity(User user, City city) {
        user.setCity( city );
    }

    @Override
    public void setCandidate(User user, String text) {
        user.setName( text );
    }

    @Override
    public void setEnglishLevel(User user, EnglishLevel englishLevel) {
        user.setEnglishLevel( englishLevel );
    }

    @Override
    public void setExperience(User user, Experience experience) {
        user.setExperience( experience );
    }

    @Override
    public void setSalaryUpTo(User user, Integer price) {
        user.setSalaryUpTo( price );
    }

    @Override
    public void setSalaryFrom(User user, Integer price) {
        user.setSalaryFrom( price );
    }
}
