package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.softkit.database.User;
import com.softkit.repository.UserFieldsSetter;
import com.softkit.vo.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
public class LocationStep extends AbstractStep {


    private final UserFieldsSetter userFieldsSetter;

    public LocationStep(UserFieldsSetter userFieldsSetter) {
        this.userFieldsSetter = userFieldsSetter;
    }

    @Override
    public UpdateProcessorResult process(Update update, User user) {
        Long chatId = UpdateTool.getChatId(update);
        Step nextStep = getStepId();
        String outgoingMessage;

        BaseRequest<?,?> optional = null;

        if (UpdateTool.isCallback(update) && City.hasEnumWithName(update.callbackQuery().data())) {
            userFieldsSetter.setCity(user, City.valueOf(update.callbackQuery().data()));
            nextStep = Step.EMPLOYMENT;
            optional = UpdateTool.getSelectedItemBaseRequest(chatId, update.callbackQuery());
            outgoingMessage = nextStep.getBotMessage();
        } else {
            outgoingMessage = nextStep.getUserMistakeResponse();
        }

        return new UpdateProcessorResult(chatId, new SendMessage(chatId, outgoingMessage), nextStep, user, optional);
    }

    @Override
    public Step getStepId() {
        return Step.CITY_OR_LOCATION;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult updateProcessorResult) {

        List<String> cities = new ArrayList<>();
        Stream.of(City.values()).forEach(city -> cities.add(city.getDescription()));

        List<String> callbacks = new ArrayList<>();
        Stream.of(City.values()).forEach(experience -> callbacks.add(experience.name()));

        return ((SendMessage)updateProcessorResult.getRequest()).replyMarkup(
                new InlineKeyboardMarkup(UpdateTool.getButtonArray(cities, callbacks, 1, false))
        );
    }

}