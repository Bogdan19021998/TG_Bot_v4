package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.softkit.database.User;
import com.softkit.utils.UpdateUtils;
import com.softkit.vo.Step;
import com.softkit.vo.UpdateProcessorResult;
import org.springframework.stereotype.Component;

import static com.softkit.vo.Step.DONE_REGISTRATION;

@Component
public class Commands {
    public Step getStepForUserCommand(Update update, User user) {

        if (UpdateUtils.hasMassageText(update)) {
            String userText = UpdateUtils.getMessage(update).text();

            if (userText.contentEquals("/start")) {
                return Step.START;
            } else if (userText.contentEquals("/profile") && user.getStep().ordinal() >= Step.DONE_BASIC_REGISTRATION.ordinal()) {
                return Step.DONE_BASIC_REGISTRATION;
            } else if (user.getStep().ordinal() >= Step.DONE_BASIC_REGISTRATION.ordinal()) {

                if (userText.contentEquals("/invites"))
                    return Step.DONE_REGISTRATION;
                else if (userText.contentEquals("/myreferrallink"))
                    return Step.REFERRAL_LINK;
            }
        }
        return null;
    }
}