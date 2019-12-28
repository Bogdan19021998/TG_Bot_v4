package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.softkit.database.User;
import com.softkit.utils.UpdateUtils;
import com.softkit.vo.Step;
import org.springframework.stereotype.Component;

@Component
public class Commands {
    public Step getStepForUserCommand(Update update, User user) {

        Long chatId = UpdateUtils.getChatId(update);

        if (UpdateUtils.hasMassageText(update)) {
            String userText = UpdateUtils.getMessage(update).text();

            if (userText.contentEquals("/start")) {

                // user press start
                // must set step Step.START
                return Step.START;

            } else if (user.getStep().ordinal() >= Step.DONE_BASIC_REGISTRATION.ordinal()) {
                if (userText.contentEquals("/profile")) {

                    // user press profile=
                    // find not filled field. And set status for this field.
                    return Step.PHONE;
                } else if (userText.contentEquals("/invites")) {

                    // user press invited users.
                    // just view invited users.
                    return  Step.INVITED_USERS;
                } else if( userText.contentEquals("/myreferrallink") )
                {
                    return Step.REFERRAL_LINK;
                }
            }
        }
        return null;
    }
}
