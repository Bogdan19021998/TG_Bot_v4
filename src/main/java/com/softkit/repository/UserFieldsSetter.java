package com.softkit.repository;

import com.softkit.database.User;
import com.softkit.vo.City;
import com.softkit.vo.EnglishLevel;
import com.softkit.vo.Experience;
import org.springframework.stereotype.Service;

@Service
public interface UserFieldsSetter {

    default void setCity(User user, City city){};

    default void setLocation(User user, float longitude, float latitude){};

    default void setCandidate(User user, String text) {};

    default void setEnglishLevel(User user, EnglishLevel englishName){};

    default void setExperience(User user, Experience experience){};

    default void setSalaryUpTo(User user, Integer price){};
    
    default void setSalaryFrom(User user, Integer price){};

}
