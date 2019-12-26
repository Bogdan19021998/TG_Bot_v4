package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.*;
import com.softkit.database.User;
import com.softkit.service.SpecializationService;
import com.softkit.vo.Specialization;
import com.softkit.vo.Step;
import com.softkit.vo.UpdateProcessorResult;
import com.softkit.vo.UpdateTool;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
public class SpecialisationStep extends AbstractStep {

    private final SpecializationService specializationService;

    public SpecialisationStep(SpecializationService specializationService) {
        this.specializationService = specializationService;
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

                if (specializationService.findAllUserSpecialization(user).size() >= 1 &&
                        specializationService.findAllUserSpecialization(user).size() <= 5) {

                    nextStep = Step.TECHNOLOGIES;

                    outgoingMessage = nextStep.getBotMessage();
                    botAnswer = new SendMessage(chatId, outgoingMessage);
                    optional = new AnswerCallbackQuery(update.callbackQuery().id());
                } else {
                    outgoingMessage = nextStep.getUserMistakeResponse();
                    botAnswer = new SendMessage(chatId, outgoingMessage);
                }

            } else if (Specialization.hasEnumWithName(data)) {

                InlineKeyboardMarkup inlineKeyboardMarkup = update.callbackQuery().message().replyMarkup();
                InlineKeyboardButton inlineKeyboardButton = UpdateTool.findButtonByCallback(inlineKeyboardMarkup.inlineKeyboard(), data);

                if (inlineKeyboardButton != null) {
                    boolean hasMarker = UpdateTool.hasMarker(inlineKeyboardButton.text());

                    if (hasMarker) {
                        specializationService.removeUserSpecialisation(user, Specialization.valueOf(data));
                        inlineKeyboardButton = UpdateTool.removeMarkerFromButton(inlineKeyboardButton);
                        outgoingMessage = "Ты отменил выбор специализации: " + inlineKeyboardButton.text() + ".";
                    } else {
                        specializationService.addUserSpecialisation(user, Specialization.valueOf(data));
                        outgoingMessage = "Круто, ты выбрал специализацию: " + inlineKeyboardButton.text() +
                                ". Можешь выбрать еще несколько или завершить этот этап.";
                        inlineKeyboardButton = UpdateTool.addMarkerToButton(inlineKeyboardButton);
                    }

                    UpdateTool.changeButtonByCallback(inlineKeyboardMarkup.inlineKeyboard(), data, inlineKeyboardButton);

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
        return Step.SPECIALISATIONS;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult updateProcessorResult) {

        List<String> specialisations = new ArrayList<>();
        Stream.of(Specialization.values()).forEach(specialization -> specialisations.add(specialization.getDescription()));

        List<String> specialisationsCallbacks = new ArrayList<>();
        Stream.of(Specialization.values()).forEach(specialization -> specialisationsCallbacks.add(specialization.name()));

        InlineKeyboardButton[][] inlineKeyboardButtons = UpdateTool.getButtonArray(specialisations, specialisationsCallbacks, 2, true);
        return ((SendMessage) updateProcessorResult.getRequest()).replyMarkup(new InlineKeyboardMarkup(inlineKeyboardButtons));
    }
}
