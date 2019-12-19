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
import org.springframework.stereotype.Component;

@Component
public class CandidateStatus extends AbstractStep {

    public UpdateProcessorResult process(Update update, User user) {

        Long chatId = UpdateTool.getChatId(update);

        outgoingMessage = UpdateTool.getUpdateMessage(update).text();
        int userTextWords = TextParser.wordCount(outgoingMessage);

        String botText;
        if ( (userTextWords == 2 || userTextWords == 3) && TextParser.isLetterText(outgoingMessage)) {
            user.setCandidate(TextParser.fixSpacing(outgoingMessage));
            nextStep = Step.SPECIALISATIONS;
            botText = this.userStatusRepository.findUserStatusByStep(nextStep).map(UserStatus::getBotMessage).get();
        } else {
            botText = this.userStatusRepository.findUserStatusByStep(nextStep).map(UserStatus::getUserMistakeResponse).get();
        }

        return new UpdateProcessorResult(chatId, new SendMessage(chatId, botText), nextStep, user);
    }

    public Step getStepId() {
        return Step.CANDIDATE;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult result) {
        return result.getRequest();
    }

}
