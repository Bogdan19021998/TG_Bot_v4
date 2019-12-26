package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.softkit.database.User;
import com.softkit.repository.UserFieldsSetter;
import com.softkit.vo.Step;
import com.softkit.utils.TextParser;
import com.softkit.vo.UpdateProcessorResult;
import com.softkit.utils.UpdateUtils;
import org.springframework.stereotype.Component;

@Component
public class MaxSalaryStep extends AbstractStep {

    private final UserFieldsSetter userFieldsSetter;

    public MaxSalaryStep(UserFieldsSetter userFieldsSetter) {
        this.userFieldsSetter = userFieldsSetter;
    }

    public UpdateProcessorResult process(Update update, User user) {
        Long chatId = UpdateUtils.getChatId(update);
        Step nextStep = getCurrentStepId();
        String outgoingMessage;
        BaseRequest<?,?> baseRequest = null;

        String price = UpdateUtils.getMessage(update).text();
        if (UpdateUtils.isMessage(update)) {

            if (TextParser.isIntegerText(price) && Integer.parseInt(price) >= 10 && Integer.parseInt(price) <= 99999) {
                userFieldsSetter.setSalaryUpTo(user, Integer.parseInt(price));
                nextStep = Step.DONE_BASIC_REGISTRATION;
                outgoingMessage = nextStep.getBotMessage();
            } else {
                outgoingMessage = nextStep.getUserMistakeResponse();
            }
            baseRequest = new SendMessage(chatId, outgoingMessage);
        }

        return new UpdateProcessorResult(chatId, baseRequest, nextStep, user);
    }

    public Step getCurrentStepId() {
        return Step.MAX_SALARY;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult result) {
        return result.getRequest();
    }

}
