package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;
import com.softkit.database.User;
import com.softkit.service.EmploymentsService;
import com.softkit.vo.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
public class EmploymentStep extends AbstractStep {

    private final EmploymentsService employmentsService;

    public EmploymentStep(EmploymentsService employmentsService) {
        this.employmentsService = employmentsService;
    }

    @Override
    public UpdateProcessorResult process(Update update, User user) {
        Long chatId = UpdateTool.getChatId(update);
        Step nextStep = getStepId();

        String outgoingMessage;
        BaseRequest<?, ?> botAnswer = null;
        BaseRequest<?, ?> optional = null;


        if (UpdateTool.isCallback(update)) {

            String data = update.callbackQuery().data();
            if (data.contentEquals(StepHolder.FINISH_SELECTION)) {
                if (employmentsService.findAllUserEmployments(user).size() >= 1) {

                    nextStep = Step.MIN_SALARY;
                    outgoingMessage = nextStep.getBotMessage();
                    botAnswer = new SendMessage(chatId, outgoingMessage);
                    optional = new AnswerCallbackQuery(update.callbackQuery().id());
                } else {
                    outgoingMessage = nextStep.getUserMistakeResponse();
                    botAnswer = new SendMessage(chatId, outgoingMessage);
                }

            } else if (Employment.hasEnumWithName(data)) {
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

        List<String> employments = new ArrayList<>();
        Stream.of(Employment.values()).forEach(employment -> employments.add(employment.getDescription()));

        List<String> callbacks = new ArrayList<>();
        Stream.of(Employment.values()).forEach(experience -> callbacks.add(experience.name()));

        return ((SendMessage) updateProcessorResult.getRequest()).replyMarkup(
                new InlineKeyboardMarkup(UpdateTool.getButtonArray(employments, callbacks, 1, true))
        );
    }

}