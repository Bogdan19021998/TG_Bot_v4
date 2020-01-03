package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.softkit.database.User;
import com.softkit.vo.Step;
import com.softkit.vo.UpdateProcessorResult;
import com.softkit.utils.UpdateUtils;
import org.springframework.stereotype.Component;

@Component
public class DoneBasicRegistrationStep extends AbstractStep {

    public UpdateProcessorResult process(Update update, User user) {

        // version two

        Long chatId = UpdateUtils.getChatId(update);
        Step nextStep = getCurrentStepId();
        String outgoingMessage = nextStep.getBotMessage();

        if (UpdateUtils.isContainsIncomingMessage( update, "/profile")) {
            nextStep = Step.PHONE;
            outgoingMessage = nextStep.getBotMessage();
        }
        return new UpdateProcessorResult(chatId, new SendMessage(chatId, outgoingMessage), nextStep, user);

        // version one
        /*
        Long chatId = UpdateUtils.getChatId(update);
        Step nextStep = getCurrentStepId();
        String outgoingMessage = nextStep.getBotMessage();

        if (UpdateUtils.hasMassageText(update) && update.message().text().contentEquals("/profile")) {
            nextStep = Step.PHONE;
            outgoingMessage = nextStep.getBotMessage();
        }

        return new UpdateProcessorResult(chatId, new SendMessage(chatId, outgoingMessage), nextStep, user);
         */
    }

    public Step getCurrentStepId() {
        return Step.DONE_BASIC_REGISTRATION;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult result) {
        return result.getRequest();
    }

}
