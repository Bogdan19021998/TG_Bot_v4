package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.softkit.database.User;
import com.softkit.utils.UpdateUtils;
import com.softkit.vo.Step;
import org.springframework.stereotype.Component;

@Component
public class Commands {

    public static final String START_COMMAND = "/start";
    public static final String COMMAND_PROFILE = "/profile";
    public static final String COMMAND_INVITES = "/invites";

    public Step getStepForUserCommand(Update update, User user) {

        if (UpdateUtils.hasMassageText(update)) {
            String userText = UpdateUtils.getMessage(update).text();

            if (userText.contentEquals(START_COMMAND)) {
                return Step.START;
            } else if (userText.contentEquals(COMMAND_PROFILE) && user.getStep().ordinal() >= Step.DONE_BASIC_REGISTRATION.ordinal()) {
                return Step.DONE_BASIC_REGISTRATION;
            } else if (userText.contentEquals(COMMAND_INVITES) && user.getStep().ordinal() >= Step.DONE_REGISTRATION.ordinal()) {
                return Step.INVITED_USERS;
            }
        }
        return null;
    }
}