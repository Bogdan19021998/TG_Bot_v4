package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.softkit.database.User;
import com.softkit.repository.UserFieldsSetter;
import com.softkit.utils.TextParser;
import com.softkit.utils.UpdateUtils;
import com.softkit.vo.Step;
import com.softkit.vo.UpdateProcessorResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AgeStep extends AbstractStep {

    private final UserFieldsSetter userFieldsSetter;

    @Override
    public UpdateProcessorResult process(Update update, User user) {

        Long chatId = UpdateUtils.getChatId(update);
        Step nextStep = getCurrentStepId();

        BaseRequest<?,?> baseRequest = null;

        if (UpdateUtils.isMessage(update)) {
            String userText = UpdateUtils.getMessage(update).text();

            String outgoingMessage;

            if (TextParser.isIntegerText(userText)) {
                int age = Integer.parseInt(userText);
                if (age >= 18 && age <= 99) {
                    nextStep = Step.SUMMARY;
                    userFieldsSetter.setAge(user, age);
                    outgoingMessage = nextStep.getBotMessage();
                }else{
                    outgoingMessage = nextStep.getUserMistakeResponse();
                }
                baseRequest = new SendMessage(chatId, outgoingMessage);
            }
        }

        return new UpdateProcessorResult(chatId, baseRequest, nextStep, user);
    }

    @Override
    public Step getCurrentStepId() {
        return Step.AGE;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult updateProcessorResult) {
        return updateProcessorResult.getRequest();
    }
}
