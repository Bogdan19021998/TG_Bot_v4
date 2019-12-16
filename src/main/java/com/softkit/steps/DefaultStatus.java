package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.softkit.database.User;
import com.softkit.database.UserStatus;
import com.softkit.repository.UserStatusRepository;
import com.softkit.vo.Step;
import com.softkit.vo.UpdateProcessorResult;
import com.softkit.vo.UpdateTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultStatus extends AbstractStep {

    @Override
    public UpdateProcessorResult process(Update update, User user) {
        Step nextStep = getStepId();
        Long chatId = UpdateTool.getUpdateMessage(update).chat().id();

        if (UpdateTool.getUpdateMessage(update).text().contentEquals(StepHolder.getStartCommand())) {
            nextStep = Step.NAME_SURNAME;
        }

        String text = this.userStatusRepository.findById(getStepId().getStepIntId()).map(UserStatus::getBotMessage).get();

        return new UpdateProcessorResult(chatId, new SendMessage(chatId, text), Step.NAME_SURNAME, user);
    }

    @Override
    public Step getStepId() {
        return Step.START;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(User user) {
        return null;
    }

}
