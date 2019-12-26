package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.softkit.Bot;
import com.softkit.database.User;
import com.softkit.database.UserLocation;
import com.softkit.repository.UserFieldsSetter;
import com.softkit.utils.TextParser;
import com.softkit.utils.UpdateUtils;
import com.softkit.vo.ApplicationContextProvider;
import com.softkit.vo.City;
import com.softkit.vo.Step;
import com.softkit.vo.UpdateProcessorResult;
import jdk.jshell.execution.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class PhoneStep extends AbstractStep{

    private final UserFieldsSetter userFieldsSetter;

    @Override
    public UpdateProcessorResult process(Update update, User user) {

        Long chatId = UpdateUtils.getChatId(update);
        Step nextStep = getCurrentStepId();
        String outgoingMessage;

        BaseRequest<?,?> baseRequest = null;

        if (UpdateUtils.isMessage(update)) {
            if (update.message().contact() != null) {
                userFieldsSetter.setPhone(user, update.message().contact().phoneNumber());
                nextStep = Step.AGE;
                outgoingMessage = nextStep.getBotMessage();
                baseRequest = new SendMessage(chatId, outgoingMessage).replyMarkup(new ReplyKeyboardRemove(false));
            } else if (UpdateUtils.hasMassageText(update) && update.message().text().contentEquals("Пропустить")) {
                nextStep = Step.AGE;
                outgoingMessage = nextStep.getBotMessage();
                baseRequest = new SendMessage(chatId, outgoingMessage).replyMarkup(new ReplyKeyboardRemove(false));
            }

        }
        return new UpdateProcessorResult(chatId, baseRequest, nextStep, user);
    }

    @Override
    public Step getCurrentStepId() {
        return Step.PHONE;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult updateProcessorResult) {

        KeyboardButton[] buttons = new KeyboardButton[] {
                new KeyboardButton("Поделиться номером").requestContact(true),
                new KeyboardButton("Пропустить")};
        return ((SendMessage)updateProcessorResult.getRequest()).replyMarkup(new ReplyKeyboardMarkup(buttons).resizeKeyboard(true));
    }
}
