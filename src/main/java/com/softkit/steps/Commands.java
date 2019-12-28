package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.softkit.database.User;
import com.softkit.service.UserService;
import com.softkit.utils.TextParser;
import com.softkit.utils.UpdateUtils;
import com.softkit.vo.Step;
import com.softkit.vo.UpdateProcessorResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Commands extends AbstractStep {

    private final UserService userService;

    @Override
    public UpdateProcessorResult process(Update update, User user) {
        Long chatId = UpdateUtils.getChatId(update);
        Step nextStep = getCurrentStepId();
        BaseRequest<?, ?> baseRequest = null;

        String refferalLink = TextParser.createReferralLink(1062233435);

        if (UpdateUtils.hasMassageText(update)) {
            String userText = UpdateUtils.getMessage(update).text();

            if (userText.contentEquals("/start")) {

                // user press start
                // must set step Step.FIRST



            } else if (user.getStep().ordinal() >= Step.DONE_BASIC_REGISTRATION.ordinal()) {
                if (userText.contentEquals("/profile")) {

                    // user press profile=
                    // find not filled field. And set status for this field.
//                    nextStep = userService.getNotFilledStepInProfile( user );
                    nextStep = Step.PHONE;

                    return new UpdateProcessorResult(chatId, new SendMessage(chatId, nextStep.getBotMessage() ), nextStep, user);

                } else if (userText.contentEquals("/invites")) {

                    // user press invited users
                    // just view invited users
                    nextStep = Step.INVITED_USERS;
                    return new UpdateProcessorResult(chatId, new SendMessage(chatId, nextStep.getBotMessage() ), nextStep, user);
                } else if( userText.contentEquals("/myreferrallink") )
                {
                    return new UpdateProcessorResult(chatId, new SendMessage(chatId, user.getReferralLink() ), nextStep, user);
                }
            }
        }
        return null;
    }

    @Override
    public Step getCurrentStepId() {
        return Step.COMMANDS;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult updateProcessorResult) {
        return null;
    }
}
