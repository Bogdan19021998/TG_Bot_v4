package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.softkit.database.User;
import com.softkit.utils.TextParser;
import com.softkit.utils.UpdateUtils;
import com.softkit.vo.Step;
import com.softkit.vo.UpdateProcessorResult;
import org.springframework.stereotype.Component;

@Component
public class DoneRegistrationStep extends AbstractStep {

    public UpdateProcessorResult process(Update update, User user) {
        Long chatId = UpdateUtils.getChatId(update);
        Step nextStep = getCurrentStepId();

        user.setReferralLink( TextParser.createReferralLink( user.getId() ));

        String outgoingMessage = nextStep.getBotMessage().replace("&", user.getReferralLink());
        return new UpdateProcessorResult(chatId, new SendMessage(chatId, outgoingMessage), nextStep, user);
    }

    public Step getCurrentStepId() {
        return Step.DONE_REGISTRATION;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult result) {
        result.getUpdatedUser().setReferralLink(TextParser.createReferralLink(result.getUpdatedUser().getId()));
        String text = getCurrentStepId().getBotMessage().replace("&", result.getUpdatedUser().getReferralLink());
        return new SendMessage(result.getChatId(), text);
    }

    @Override
    public Step getDefaultNextStep() {
        return null;
    }

}
