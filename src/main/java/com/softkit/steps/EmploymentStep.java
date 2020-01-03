package com.softkit.steps;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;
import com.softkit.database.User;
import com.softkit.service.EmploymentsService;
import com.softkit.utils.UpdateUtils;
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


        Long chatId = UpdateUtils.getChatId(update);
        Step nextStep = getCurrentStepId();

        String outgoingMessage;
        BaseRequest<?, ?> botAnswer = null;
        BaseRequest<?, ?> optional = null;

        if (UpdateUtils.isCallback(update)) {

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
                InlineKeyboardButton inlineKeyboardButton = UpdateUtils.findButtonByCallback(inlineKeyboardMarkup.inlineKeyboard(), data);

                if (inlineKeyboardButton != null) {
                    boolean hasMarker = UpdateUtils.hasMarker(inlineKeyboardButton.text());

                    if (hasMarker) {
                        employmentsService.removeUserEmployment(user, Employment.valueOf(data));
                        inlineKeyboardButton = UpdateUtils.removeMarkerFromButton(inlineKeyboardButton);
                    } else {
                        employmentsService.addUserEmployment(user, Employment.valueOf(data));
                        inlineKeyboardButton = UpdateUtils.addMarkerToButton(inlineKeyboardButton);
                    }

                    UpdateUtils.changeButtonByCallback(inlineKeyboardMarkup.inlineKeyboard(), data, inlineKeyboardButton);

                    Message message = UpdateUtils.getCallbackMessage(update);
                    EditMessageText editMessageText = new EditMessageText(chatId, message.messageId(), message.text());
                    editMessageText.replyMarkup(inlineKeyboardMarkup);
                    botAnswer = editMessageText;
                }
            }

        }

        return new UpdateProcessorResult(chatId, botAnswer, nextStep, user, optional);

    }

    @Override
    public Step getCurrentStepId() {
        return Step.EMPLOYMENT;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult updateProcessorResult) {

        List<String> employments = new ArrayList<>();
        Stream.of(Employment.values()).forEach(employment -> employments.add(employment.getDescription()));

        List<String> callbacks = new ArrayList<>();
        Stream.of(Employment.values()).forEach(experience -> callbacks.add(experience.name()));

        return ((SendMessage) updateProcessorResult.getRequest()).replyMarkup(
                new InlineKeyboardMarkup(UpdateUtils.getButtonArray(employments, callbacks, 1, true))
        );
    }

}