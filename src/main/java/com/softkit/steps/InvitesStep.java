package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.softkit.database.User;
import com.softkit.service.ReferralService;
import com.softkit.utils.UpdateUtils;
import com.softkit.vo.Step;
import com.softkit.vo.UpdateProcessorResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InvitesStep extends AbstractStep {

    private final ReferralService referralService;

    @Override
    public UpdateProcessorResult process(Update update, User user) {

        Long chatId = UpdateUtils.getChatId(update);

        String outgoingText = getCurrentStepId().getBotMessage()
                .replaceFirst("@invited" , referralService.findAllUserReferrals( user ).size() + "" )
                .replaceFirst("@working" , "0")
                .replaceFirst("@profit","0.0")
                + "\n" + user.getReferralLink();

        SendMessage sendMessage = new SendMessage(chatId, outgoingText );
        return new UpdateProcessorResult(chatId, sendMessage, getCurrentStepId(), user);
    }

    @Override
    public Step getCurrentStepId() {
        return Step.INVITED_USERS;
    }

    @Override
    public Step getDefaultNextStep() {
        return null;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult updateProcessorResult) {
        return updateProcessorResult.getRequest();
    }


}
