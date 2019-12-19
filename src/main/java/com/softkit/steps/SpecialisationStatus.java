package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.softkit.database.UserProfile;
import com.softkit.database.UserStatus;
import com.softkit.vo.Step;
import com.softkit.vo.UpdateProcessorResult;
import com.softkit.vo.UpdateTool;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class SpecialisationStatus extends AbstractStep {

    @Override
    public UpdateProcessorResult process(Update update, UserProfile userProfile) {

        Long chatId = UpdateTool.getChatId(update);

        if (UpdateTool.isCallback(update)) {
            String data = update.callbackQuery().data();
            System.out.println(data);
            outgoingMessage = this.userStatusRepository.findById(nextStep.getStepIntId()).map(UserStatus::getBotMessage).get();
        } else
            outgoingMessage = this.userStatusRepository.findById(nextStep.getStepIntId()).map(UserStatus::getUserMistakeResponse).get();

        return new UpdateProcessorResult(chatId, new SendMessage(chatId, outgoingMessage), nextStep, userProfile);
    }

    @Override
    public Step getStepId() {
        return Step.SPECIALISATIONS;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult updateProcessorResult) {

        List<String> specialisations = Stream.of(".NET", "Front-End / JS", "Android", "Node.js", "C/C++", "PHP", "Golang",
                "Python", "iOS", "Ruby / Rails", "Java", "Scala", "Завершить").collect(Collectors.toList());

        InlineKeyboardButton[][] inlineKeyboardButtons = UpdateTool.getButtonArray(specialisations, 2);

        return ((SendMessage)updateProcessorResult.getRequest()).replyMarkup(new InlineKeyboardMarkup(inlineKeyboardButtons));
    }
}
