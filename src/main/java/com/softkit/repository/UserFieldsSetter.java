package com.softkit.repository;

import com.softkit.database.User;
import com.softkit.vo.City;
import com.softkit.vo.EnglishLevel;
import com.softkit.vo.Experience;
import org.springframework.stereotype.Service;

@Service
public interface UserFieldsSetter {

    void setCity(User user, City city);

    void setCandidate(User user, String text);

    void setEnglishLevel(User user, EnglishLevel englishLevel);

    void setExperience(User user, Experience experience);

    void setAge( User user, Integer age );

    void setSalaryUpTo(User user, Integer price);
    
    void setSalaryFrom(User user, Integer price);

    void setPhone(User user, String phoneNumber);
}
