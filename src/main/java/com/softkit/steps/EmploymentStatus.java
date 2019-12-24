package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;
import com.softkit.database.Status;
import com.softkit.database.User;
import com.softkit.database.UserEmployment;
import com.softkit.repository.UserEmploymentRepository;
import com.softkit.repository.UserStatusRepository;
import com.softkit.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
public class EmploymentStatus extends AbstractStep {

    private final UserEmploymentRepository userEmploymentRepository;

    public EmploymentStatus(UserStatusRepository userStatusRepository, UserEmploymentRepository userEmploymentRepository) {
        super(userStatusRepository);
        this.userEmploymentRepository = userEmploymentRepository;
    }

    @Override
    public UpdateProcessorResult process(Update update, User user) {
        Long chatId = UpdateTool.getChatId(update);

        outgoingMessage = this.userStatusRepository.findUserStatusByStep(nextStep).map(Status::getUserMistakeResponse).get();
        BaseRequest<?,?> botAnswer = new SendMessage(chatId, outgoingMessage);

        if (UpdateTool.isCallback(update)) {

            String data = update.callbackQuery().data();
            boolean hasMarker = UpdateTool.hasMarker(data);

            if (data.contentEquals(StepHolder.FINISH_SELECTION) &&
                    ( user.getEmployments().size() >= 1 || true ) // debug .. remove " || true "
            ) {
                nextStep = Step.MIN_SALARY;
                outgoingMessage = userStatusRepository.findUserStatusByStep(nextStep).map(Status::getBotMessage).get();
                botAnswer = new SendMessage(chatId, outgoingMessage);
            } else {

                InlineKeyboardMarkup inlineKeyboardMarkup = update.callbackQuery().message().replyMarkup();
                InlineKeyboardButton inlineKeyboardButton = UpdateTool.findButtonByText(inlineKeyboardMarkup.inlineKeyboard(), data);

                if (inlineKeyboardButton != null) {
                    if (hasMarker) {
                        userEmploymentRepository.removeUserEmployment(user.getUserId(), data);
                        inlineKeyboardButton = UpdateTool.removeMarkerFromButton(inlineKeyboardButton);
                    } else {
                        userEmploymentRepository.addUserEmployment(user.getUserId(), data);
                        inlineKeyboardButton = UpdateTool.addMarkerToButton(inlineKeyboardButton);
                    }

                    UpdateTool.changeButtonByText(inlineKeyboardMarkup.inlineKeyboard(), data, inlineKeyboardButton);

                    outgoingMessage = UpdateTool.getUpdateMessage(update).text();
                    EditMessageText editMessageText = new EditMessageText(chatId, update.callbackQuery().message().messageId(), outgoingMessage);
                    editMessageText.replyMarkup(inlineKeyboardMarkup);
                    botAnswer = editMessageText;
                }
            }

        }

        return new UpdateProcessorResult(chatId, botAnswer, nextStep, user);
    }

    @Override
    public Step getStepId() {
        return Step.EMPLOYMENT;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult updateProcessorResult) {

        List<String> experiences = new ArrayList<>();
        Stream.of(Employment.values()).forEach(experience -> experiences.add(experience.getDescription()));

        List<String> callbacks = new ArrayList<>();
        Stream.of(Employment.values()).forEach(experience -> callbacks.add(experience.name()));

        return ((SendMessage)updateProcessorResult.getRequest()).replyMarkup(
                new InlineKeyboardMarkup(UpdateTool.getButtonArray(experiences, callbacks, 1, true))
        );
    }

}