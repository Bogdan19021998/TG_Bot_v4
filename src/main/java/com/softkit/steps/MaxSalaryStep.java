package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.softkit.database.Status;
import com.softkit.database.User;
import com.softkit.repository.UserFieldsSetter;
import com.softkit.repository.UserStatusRepository;
import com.softkit.vo.Step;
import com.softkit.vo.TextParser;
import com.softkit.vo.UpdateProcessorResult;
import com.softkit.vo.UpdateTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MaxSalaryStep extends AbstractStep {

    private final UserFieldsSetter userFieldsSetter;

    public MaxSalaryStep(UserStatusRepository userStatusRepository, UserFieldsSetter userFieldsSetter) {
        super(userStatusRepository);
        this.userFieldsSetter = userFieldsSetter;
    }

    public UpdateProcessorResult process(Update update, User user) {

        Long chatId = UpdateTool.getChatId(update);

        String price = UpdateTool.getUpdateMessage(update).text();
        if (TextParser.isIntegerText(price) && Integer.parseInt(price) >= 10 && Integer.parseInt(price) <= 99999) {
            nextStep = Step.DONE_BASIC_REGISTRATION;
            userFieldsSetter.setSalaryUpTo(user, Integer.parseInt(price));
            outgoingMessage = this.userStatusRepository.findUserStatusByStep(nextStep).map(Status::getBotMessage).get();
        } else {
            outgoingMessage = this.userStatusRepository.findUserStatusByStep(nextStep).map(Status::getUserMistakeResponse).get();
        }

        return new UpdateProcessorResult(chatId, new SendMessage(chatId, outgoingMessage), nextStep, user);
    }

    public Step getStepId() {
        return Step.MAX_SALARY;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult result) {
        return result.getRequest();
    }

}
