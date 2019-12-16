package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.softkit.database.User;
import com.softkit.database.UserStatus;
import com.softkit.vo.Step;
import com.softkit.vo.TextParser;
import com.softkit.vo.UpdateProcessorResult;
import com.softkit.vo.UpdateTool;

public class NameStatus extends AbstractStep {

    public UpdateProcessorResult process(Update update, User user) {

        String userText = UpdateTool.getUpdateMessage(update).text();
        Step nextStep = getStepId();
        int userTextWords = TextParser.wordCount(userText);

        long chatId = UpdateTool.getUpdateMessage(update).chat().id();

        String botText;
        if ( (userTextWords == 2 || userTextWords == 3) && TextParser.isLetterText(userText)) {
            user.setCandidate(userText);
            nextStep = Step.SPECIALISATIONS;
            botText = userStatusRepository.findById(nextStep.getStepId()).map(UserStatus::getBotMessage).get();
        } else {
            botText = userStatusRepository.findById(nextStep.getStepId()).map(UserStatus::getUserMistakeResponse).get();
        }

        return new UpdateProcessorResult(chatId, new SendMessage(chatId, botText), nextStep, user);
    }

    public Step getStepId() {
        return Step.NAME_SURNAME;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(User user) {
        return new SendMessage(user.getUserId(), "Please enter you first name");
    }

}
