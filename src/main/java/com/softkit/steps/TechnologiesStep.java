package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.softkit.database.User;
import com.softkit.service.TechnologiesService;
import com.softkit.vo.Step;
import com.softkit.vo.TextParser;
import com.softkit.vo.UpdateProcessorResult;
import com.softkit.vo.UpdateTool;
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
        Long chatId = UpdateTool.getChatId(update);
        Step nextStep = getStepId();
        String outgoingMessage;

        String userText = UpdateTool.getUpdateMessage(update).text();

        if (TextParser.isEngLettDigSpecSymbText(userText) || (UpdateTool.isCallback(update) && update.callbackQuery().data().contentEquals(StepHolder.FINISH_SELECTION)) ) {
            nextStep = Step.EXPERIENCE;
            userText = TextParser.fixSpacing(userText);
            String[] technologiesStr = userText.split(" ");
            technologiesService.addAllTechnologies(user, technologiesStr);
            outgoingMessage = nextStep.getBotMessage();
        } else {
            outgoingMessage = nextStep.getUserMistakeResponse();
        }

        BaseRequest<?,?> optional = UpdateTool.isCallback(update) ? new AnswerCallbackQuery(update.callbackQuery().id()) : null;

        return new UpdateProcessorResult(chatId, new SendMessage(chatId, outgoingMessage), nextStep, user, optional);
    }

    @Override
    public Step getStepId() {
        return Step.TECHNOLOGIES;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult updateProcessorResult) {

        return ((SendMessage)updateProcessorResult.getRequest()).replyMarkup(
                new InlineKeyboardMarkup(UpdateTool.getButtonArrayWithExitButton(new ArrayList<>()))
        );
    }

}
