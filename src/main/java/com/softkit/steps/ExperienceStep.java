package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.softkit.database.User;
import com.softkit.repository.UserFieldsSetter;
import com.softkit.utils.UpdateUtils;
import com.softkit.vo.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
public class ExperienceStep extends AbstractStep {

    private final UserFieldsSetter userFieldsSetter;

    public ExperienceStep(UserFieldsSetter userFieldsSetter) {
        this.userFieldsSetter = userFieldsSetter;
    }

    @Override
    public UpdateProcessorResult process(Update update, User user) {
        Long chatId = UpdateUtils.getChatId(update);
        Step nextStep = getCurrentStepId();
        String outgoingMessage;

        BaseRequest<?,?> optional = null;

        if (UpdateUtils.isCallback(update) && Experience.hasEnumWithName(update.callbackQuery().data())) {
            userFieldsSetter.setExperience(user, Experience.valueOf(update.callbackQuery().data()));
            nextStep = getDefaultNextStep();
            optional = UpdateUtils.getSelectedItemBaseRequest(chatId, update.callbackQuery());
            outgoingMessage = nextStep.getBotMessage();
        } else {
            outgoingMessage = nextStep.getUserMistakeResponse();
        }

        return new UpdateProcessorResult(chatId, new SendMessage(chatId, outgoingMessage), nextStep, user, optional);
    }

    @Override
    public Step getCurrentStepId() {
        return Step.EXPERIENCE;
    }

    @Override
    public Step getDefaultNextStep() {
        return Step.ENGLISH_LEVEL;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult updateProcessorResult) {

        List<String> experiences = new ArrayList<>();
        Stream.of(Experience.values()).forEach(experience -> experiences.add(experience.getDescription()));

        List<String> callbacks = new ArrayList<>();
        Stream.of(Experience.values()).forEach(experience -> callbacks.add(experience.name()));

        return ((SendMessage) updateProcessorResult.getRequest()).replyMarkup(
                new InlineKeyboardMarkup(UpdateUtils.getButtonArray(experiences, callbacks, 1, false))
        );
    }

}