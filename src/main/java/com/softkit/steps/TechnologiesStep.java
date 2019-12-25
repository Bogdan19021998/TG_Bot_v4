package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.softkit.database.Status;
import com.softkit.database.User;
import com.softkit.repository.UserStatusRepository;
import com.softkit.repository.UserTechnologyRepository;
import com.softkit.vo.Step;
import com.softkit.vo.TextParser;
import com.softkit.vo.UpdateProcessorResult;
import com.softkit.vo.UpdateTool;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class TechnologiesStep extends AbstractStep {

    private UserTechnologyRepository userTechnologyRepository;

    public TechnologiesStep(UserStatusRepository userStatusRepository, UserTechnologyRepository userTechnologyRepository) {
        super(userStatusRepository);
        this.userTechnologyRepository = userTechnologyRepository;
    }

    @Override
    public UpdateProcessorResult process(Update update, User user) {
        Long chatId = UpdateTool.getChatId(update);

        String userText = UpdateTool.getUpdateMessage(update).text();

        if (TextParser.isEngLettDigSpecSymbText(userText) || (UpdateTool.isCallback(update) && update.callbackQuery().data().contentEquals(StepHolder.FINISH_SELECTION)) ) {
            nextStep = Step.EXPERIENCE;
            userText = TextParser.fixSpacing(userText);
            String[] technologiesStr = userText.split(" ");
            userTechnologyRepository.saveTechnologies(technologiesStr);
            outgoingMessage = this.userStatusRepository.findUserStatusByStep(nextStep).map(Status::getBotMessage).get();
        } else {
            outgoingMessage = this.userStatusRepository.findUserStatusByStep(nextStep).map(Status::getUserMistakeResponse).get();
        }

        return new UpdateProcessorResult(chatId, new SendMessage(chatId, outgoingMessage), nextStep, user);
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
