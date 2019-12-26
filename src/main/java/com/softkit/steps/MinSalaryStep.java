package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.softkit.database.User;
import com.softkit.repository.UserFieldsSetter;
import com.softkit.vo.Step;
import com.softkit.vo.TextParser;
import com.softkit.vo.UpdateProcessorResult;
import com.softkit.vo.UpdateTool;
import org.springframework.stereotype.Component;

@Component
public class MinSalaryStep extends AbstractStep {

    private final UserFieldsSetter userFieldsSetter;

    public MinSalaryStep(UserFieldsSetter userFieldsSetter) {
        this.userFieldsSetter = userFieldsSetter;
    }

    public UpdateProcessorResult process(Update update, User user) {

        Long chatId = UpdateTool.getChatId(update);
        Step nextStep = getStepId();
        String outgoingMessage;

        String price = UpdateTool.getUpdateMessage(update).text();
        if (TextParser.isIntegerText(price) && Integer.parseInt(price) >= 10 && Integer.parseInt(price) <= 99999) {
            nextStep = Step.MAX_SALARY;
            userFieldsSetter.setSalaryFrom(user, Integer.parseInt(price));
            outgoingMessage = nextStep.getBotMessage();
        } else {
            outgoingMessage = nextStep.getUserMistakeResponse();
        }

        return new UpdateProcessorResult(chatId, new SendMessage(chatId, outgoingMessage), nextStep, user);
    }

    public Step getStepId() {
        return Step.MIN_SALARY;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult result) {
        return result.getRequest();
    }

}
