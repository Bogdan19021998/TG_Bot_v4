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
//todo rename
public class CandidateStep extends AbstractStep {

    private final UserFieldsSetter userFieldsSetter;

    public CandidateStep(UserFieldsSetter userFieldsSetter) {
        this.userFieldsSetter = userFieldsSetter;
    }

    public UpdateProcessorResult process(Update update, User user) {

        Long chatId = UpdateUtils.getChatId(update);
        Step nextStep = getCurrentStepId();
        BaseRequest<?,?> baseRequest = null;

        if (UpdateUtils.isMessage(update)) {
            String userText = UpdateUtils.getMessage(update).text();
            int userTextWords = TextParser.wordCount(userText);
            String outgoingMessage;

            if ((userTextWords == 2 || userTextWords == 3) && TextParser.isLetterText(userText)) {
                userFieldsSetter.setCandidate(user, TextParser.fixSpacing(userText));
                nextStep = Step.SPECIALISATIONS;
                outgoingMessage = nextStep.getBotMessage();
            } else
                outgoingMessage = nextStep.getUserMistakeResponse();

            baseRequest = new SendMessage(chatId, outgoingMessage);
        }

        return new UpdateProcessorResult(chatId, baseRequest, nextStep, user);
    }

    public Step getCurrentStepId() {
        return Step.CANDIDATE;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult result) {
        return result.getRequest();
    }

}
