package com.softkit.service;

import com.softkit.database.User;
import com.softkit.database.UserReferral;
import com.softkit.repository.ReferralRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class ReferralService {

    private final ReferralRepository referralRepository;

    public HashSet<UserReferral> findAllUserReferrals(User user) {
        return new HashSet<>(referralRepository.findUserReferralsByUserId(user.getId()));
    }

    public void addUserReferral(User userOwner, User userChild) {
        referralRepository.save( new UserReferral(userOwner.getId(), userChild.getId() ));
    }

    public void removeAllUserReferrals(User user) {
        referralRepository.removeAllUserReferrals( user.getId() );
    }
}
