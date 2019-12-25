package com.softkit.service;

import com.softkit.database.User;
import com.softkit.database.UserSpecialization;
import com.softkit.repository.UserSpecialisationsRepository;
import com.softkit.vo.Specialization;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpecializationService {

    private final UserSpecialisationsRepository userSpecialisationsRepository;

    public boolean addUserSpecialisation(User user, String nameSpecialization){
        Specialization specialization = Specialization.valueOf(nameSpecialization);
        if (specialization != null) {
            userSpecialisationsRepository.save(new UserSpecialization(user.getId(), specialization));
            return true;
        }
        return false;
    }

    public boolean removeUserSpecialisation(User user, String nameSpecialization){
        Specialization specialization = getSpecialization(nameSpecialization);
        Integer count = 0;
        if( specialization != null ) {
            count = userSpecialisationsRepository.removeUserSpecialization(user.getId(), specialization);
        }
        return count != 0 ? true : false;
    }

    private Specialization getSpecialization( String nameSpecilization )
    {
        Specialization specialization = null;
        try{
            specialization = Specialization.valueOf( nameSpecilization );
        }catch (IllegalArgumentException iae){}
        return specialization;
    }
}
