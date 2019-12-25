package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;
import com.softkit.database.Status;
import com.softkit.database.User;
import com.softkit.repository.UserStatusRepository;
import com.softkit.service.EmploymentsService;
import com.softkit.vo.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
public class EmploymentStep extends AbstractStep {

    private final EmploymentsService employmentsService;

    public EmploymentStep(UserStatusRepository userStatusRepository, EmploymentsService employmentsService) {
        super(userStatusRepository);
        this.employmentsService = employmentsService;
    }

    @Override
    public UpdateProcessorResult process(Update update, User user) {
        Long chatId = UpdateTool.getChatId(update);

        outgoingMessage = this.userStatusRepository.findUserStatusByStep(nextStep).map(Status::getUserMistakeResponse).get();
        BaseRequest<?,?> botAnswer = new SendMessage(chatId, outgoingMessage);
        BaseRequest<?,?> optional = null;

        String data = update.callbackQuery().data();

        if (UpdateTool.isCallback(update)) {

            if (data.contentEquals(StepHolder.FINISH_SELECTION) && employmentsService.findAllUserEmployments(user).size() >= 0/*1*/ /* debug ..*/ ) {
                nextStep = Step.MIN_SALARY;
                outgoingMessage = userStatusRepository.findUserStatusByStep(nextStep).map(Status::getBotMessage).get();
                botAnswer = new SendMessage(chatId, outgoingMessage);
                optional = new AnswerCallbackQuery( update.callbackQuery().id() );
            } else if (Specialization.hasEnumWithName(data)) {

                InlineKeyboardMarkup inlineKeyboardMarkup = update.callbackQuery().message().replyMarkup();
                InlineKeyboardButton inlineKeyboardButton = UpdateTool.findButtonByCallback(inlineKeyboardMarkup.inlineKeyboard(), data);

                if (inlineKeyboardButton != null) {
                    boolean hasMarker = UpdateTool.hasMarker(inlineKeyboardButton.text());

                    if (hasMarker) {
                        employmentsService.removeUserEmployment(user, Employment.valueOf(data));
                        inlineKeyboardButton = UpdateTool.removeMarkerFromButton(inlineKeyboardButton);
                    } else {
                        employmentsService.addUserEmployment(user, Employment.valueOf(data));
                        inlineKeyboardButton = UpdateTool.addMarkerToButton(inlineKeyboardButton);
                    }

                    UpdateTool.changeButtonByCallback(inlineKeyboardMarkup.inlineKeyboard(), data, inlineKeyboardButton);

                    outgoingMessage = UpdateTool.getUpdateMessage(update).text();
                    EditMessageText editMessageText = new EditMessageText(chatId, update.callbackQuery().message().messageId(), outgoingMessage);
                    editMessageText.replyMarkup(inlineKeyboardMarkup);
                    botAnswer = editMessageText;
                }
            }

        }

        return new UpdateProcessorResult(chatId, botAnswer, nextStep, user, optional);
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