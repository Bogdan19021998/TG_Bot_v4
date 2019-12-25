package com.softkit.service;

import com.softkit.database.User;
import com.softkit.database.UserEmployment;
import com.softkit.database.UserReferral;
import com.softkit.repository.ReferralRepository;
import com.softkit.repository.UserEmploymentRepository;
import com.softkit.repository.UserRepository;
import com.softkit.vo.Employment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class RefferalService {

    private final ReferralRepository referralRepository;

    public HashSet<UserReferral> findAllUserReferrals(User user) {
        return new HashSet<UserReferral>(referralRepository.findUserReferralsByUserId(user.getId()));
    }

    public void addUserReferral(User user, Integer referralId) {
        referralRepository.save( new UserReferral(user.getId(), referralId));
    }

    public void removeAllUserReferrals(User user) {
        referralRepository.removeAllUserRefferals( user.getId() );
    }
}
