package com.softkit.steps;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.softkit.database.User;
import com.softkit.database.UserStatus;
import com.softkit.vo.Step;
import com.softkit.vo.UpdateProcessorResult;
import com.softkit.vo.UpdateTool;

public class DefaultStatus extends AbstractStep {

    @Override
    public UpdateProcessorResult process(Update update, User user) {
        Long chatId = UpdateTool.getUpdateMessage(update).chat().id();
        String text = userStatusRepository.findById(getStepId().getStepId()).map(UserStatus::getBotMessage).get();
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
