package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.softkit.database.Status;
import com.softkit.database.User;
import com.softkit.repository.UserFieldsSetter;
import com.softkit.repository.UserStatusRepository;
import com.softkit.vo.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
public class EnglishLevelStep extends AbstractStep {

    private final UserFieldsSetter userFieldsSetter;

    public EnglishLevelStep(UserStatusRepository userStatusRepository, UserFieldsSetter userFieldsSetter) {
        super(userStatusRepository);
        this.userFieldsSetter = userFieldsSetter;
    }

    @Override
    public UpdateProcessorResult process(Update update, User user) {
        Long chatId = UpdateTool.getChatId(update);
        BaseRequest<?,?> optional = null;

        if (UpdateTool.isCallback(update) && EnglishLevel.hasEnumWithName(update.callbackQuery().data())) {
            userFieldsSetter.setEnglishLevel(user, EnglishLevel.valueOf(update.callbackQuery().data()));
            nextStep = Step.CITY_OR_LOCATION;
            optional = UpdateTool.getSelectedItemBaseRequest(chatId, update.callbackQuery());
            outgoingMessage = this.userStatusRepository.findUserStatusByStep(nextStep).map(Status::getBotMessage).get();
        } else {
            outgoingMessage = this.userStatusRepository.findUserStatusByStep(nextStep).map(Status::getUserMistakeResponse).get();
        }

        return new UpdateProcessorResult(chatId, new SendMessage(chatId, outgoingMessage), nextStep, user, optional);
    }

    @Override
    public Step getStepId() {
        return Step.ENGLISH_LEVEL;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult updateProcessorResult) {

        List<String> englishLevels = new ArrayList<>();
        Stream.of(EnglishLevel.values()).forEach(englishLevel -> englishLevels.add(englishLevel.getDescription()));

        List<String> callbacks = new ArrayList<>();
        Stream.of(EnglishLevel.values()).forEach(experience -> callbacks.add(experience.name()));

        return ((SendMessage)updateProcessorResult.getRequest()).replyMarkup(
                new InlineKeyboardMarkup(UpdateTool.getButtonArray(englishLevels, callbacks, 1, false))
        );
    }

}