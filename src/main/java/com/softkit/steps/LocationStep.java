package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove;
import com.pengrad.telegrambot.request.*;
import com.softkit.Bot;
import com.softkit.database.User;
import com.softkit.database.UserLocation;
import com.softkit.repository.LocationRepository;
import com.softkit.repository.UserFieldsSetter;
import com.softkit.utils.UpdateUtils;
import com.softkit.vo.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
public class LocationStep extends AbstractStep {

    private final LocationRepository locationRepository;
    private final UserFieldsSetter userFieldsSetter;

    public LocationStep(LocationRepository locationRepository, UserFieldsSetter userFieldsSetter) {
        this.locationRepository = locationRepository;
        this.userFieldsSetter = userFieldsSetter;
    }

    @Override
    public UpdateProcessorResult process(Update update, User user) {
        Long chatId = UpdateUtils.getChatId(update);
        Step nextStep = getCurrentStepId();
        String outgoingMessage;

        BaseRequest<?,?> baseRequest = null;
        BaseRequest<?,?> optional = null;

        if (UpdateUtils.isCallback(update) && City.hasEnumWithName(update.callbackQuery().data())) {

            userFieldsSetter.setCity(user, City.valueOf(update.callbackQuery().data()));
            optional = UpdateUtils.getSelectedItemBaseRequest(chatId, update.callbackQuery());
            nextStep = getDefaultNextStep();
            outgoingMessage = nextStep.getBotMessage();
            baseRequest = new SendMessage(chatId, outgoingMessage).replyMarkup(new ReplyKeyboardRemove(false));

        } else if (UpdateUtils.isMessage(update) && update.message().location() != null) {

            locationRepository.save(new UserLocation(user.getId(),
                    update.message().location().longitude(), update.message().location().latitude()));
            nextStep = getDefaultNextStep();
            outgoingMessage = nextStep.getBotMessage();

            baseRequest = new SendMessage(chatId, outgoingMessage).replyMarkup(new ReplyKeyboardRemove(false));
        }

        return new UpdateProcessorResult(chatId, baseRequest, nextStep, user, optional);
    }

    @Override
    public Step getCurrentStepId() {
        return Step.CITY_OR_LOCATION;
    }

    @Override
    public Step getDefaultNextStep() {
        return Step.EMPLOYMENT;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult updateProcessorResult) {

        List<String> cities = new ArrayList<>();
        Stream.of(City.values()).forEach(city -> cities.add(city.getDescription()));

        List<String> callbacks = new ArrayList<>();
        Stream.of(City.values()).forEach(experience -> callbacks.add(experience.name()));

        //
        InlineKeyboardMarkup citiesMarkup = new InlineKeyboardMarkup(UpdateUtils.getButtonArray(cities, callbacks, 1, false));
        BaseRequest<?,?> optionalRequest = ((SendMessage)updateProcessorResult.getRequest()).replyMarkup(citiesMarkup);
        ApplicationContextProvider.getApplicationContext().getBean(Bot.class).execute(optionalRequest);
        //
        KeyboardButton[] buttons = new KeyboardButton[] {new KeyboardButton("Поделится Местоположением").requestLocation(true)};
        return new SendMessage(updateProcessorResult.getChatId(), "Ты также можешь отправить свое местоположение").
                replyMarkup(new ReplyKeyboardMarkup(buttons).resizeKeyboard(true));
    }
}