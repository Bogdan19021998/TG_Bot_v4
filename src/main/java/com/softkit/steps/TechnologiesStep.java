package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.softkit.database.User;
import com.softkit.service.TechnologiesService;
import com.softkit.vo.Step;
import com.softkit.utils.TextParser;
import com.softkit.vo.UpdateProcessorResult;
import com.softkit.utils.UpdateUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class TechnologiesStep extends AbstractStep {

    private TechnologiesService technologiesService;

    public TechnologiesStep(TechnologiesService technologiesService) {
        this.technologiesService = technologiesService;
    }

    @Override
    public UpdateProcessorResult process(Update update, User user) {
        Long chatId = UpdateUtils.getChatId(update);
        Step nextStep = getCurrentStepId();
        String outgoingMessage;
        BaseRequest<?,?> optional = null;

        String userText = UpdateUtils.getMessageOrCallbackMessage(update).text();

        if ( UpdateUtils.isCallback(update) && update.callbackQuery().data().contentEquals(StepHolder.FINISH_SELECTION) ) {
            optional = new AnswerCallbackQuery(update.callbackQuery().id());
            nextStep = Step.EXPERIENCE;
            outgoingMessage = nextStep.getBotMessage();
        } else if (UpdateUtils.isMessage(update) && TextParser.isEngLettDigSpecSymbText(userText)){
            userText = TextParser.fixSpacing(userText);
            String[] technologiesStr = userText.split(" ");
            technologiesService.addAllTechnologies(user, technologiesStr);
            // duplicated code ??
            nextStep = Step.EXPERIENCE;
            outgoingMessage = nextStep.getBotMessage();
        } else {
            outgoingMessage = nextStep.getUserMistakeResponse();
        }

        return new UpdateProcessorResult(chatId, new SendMessage(chatId, outgoingMessage), nextStep, user, optional);
    }

    @Override
    public Step getCurrentStepId() {
        return Step.TECHNOLOGIES;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult updateProcessorResult) {

        return ((SendMessage)updateProcessorResult.getRequest()).replyMarkup(
                new InlineKeyboardMarkup(UpdateUtils.getButtonArrayWithExitButton(new ArrayList<>()))
        );
    }

}
