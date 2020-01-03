package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.softkit.database.User;
import com.softkit.utils.UpdateUtils;
import com.softkit.vo.Step;
import org.springframework.stereotype.Component;

@Component
public class Commands {
    public Step getStepForUserCommand(Update update, User user) {

        if (UpdateUtils.hasMassageText(update)) {
            String userText = UpdateUtils.getMessage(update).text();

            if (userText.contentEquals("/start")) {
                return Step.START;
            } else if (userText.contentEquals("/profile") && user.getStep().ordinal() >= Step.DONE_BASIC_REGISTRATION.ordinal()) {
                return Step.DONE_BASIC_REGISTRATION;
            } else if (userText.contentEquals("/invites") && user.getStep().ordinal() >= Step.DONE_REGISTRATION.ordinal()) {
                return Step.INVITED_USERS;
            }
        }
        return null;
    }
}