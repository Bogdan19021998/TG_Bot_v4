package com.softkit.steps;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.*;
import com.softkit.database.User;
import com.softkit.database.Status;
import com.softkit.repository.SpecialisationRepository;
import com.softkit.repository.UserSpecialisationsRepository;
import com.softkit.repository.UserStatusRepository;
import com.softkit.vo.Step;
import com.softkit.vo.UpdateProcessorResult;
import com.softkit.vo.UpdateTool;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SpecialisationStatus extends AbstractStep {

    private SpecialisationRepository specialisationRepository;
    private final UserSpecialisationsRepository userSpecialisationsRepository;

    public SpecialisationStatus(UserStatusRepository userStatusRepository, SpecialisationRepository specialisationRepository, UserSpecialisationsRepository userSpecialisationsRepository) {
        super(userStatusRepository);
        this.specialisationRepository = specialisationRepository;
        this.userSpecialisationsRepository = userSpecialisationsRepository;
    }

    @Override
    public UpdateProcessorResult process(Update update, User user) {

        Long chatId = UpdateTool.getChatId(update);

        outgoingMessage = this.userStatusRepository.findUserStatusByStep(nextStep).map(Status::getUserMistakeResponse).get();
        BaseRequest<?,?> botAnswer = new SendMessage(chatId, outgoingMessage);

        if (UpdateTool.isCallback(update)) {

            String data = update.callbackQuery().data();
            boolean hasMarker = UpdateTool.hasMarker(data);

            if (data.contentEquals(StepHolder.FINISH_SELECTION) && user.getSpecializations().size() >= 1 && user.getSpecializations().size() <= 5) {
//            if ( true ) {

                nextStep = Step.TECHNOLOGIES;
                outgoingMessage = userStatusRepository.findUserStatusByStep(nextStep).map(Status::getBotMessage).get();
                botAnswer = new SendMessage(chatId, outgoingMessage);

            } else {

                InlineKeyboardMarkup inlineKeyboardMarkup = update.callbackQuery().message().replyMarkup();
                InlineKeyboardButton inlineKeyboardButton = UpdateTool.findButtonByText(inlineKeyboardMarkup.inlineKeyboard(), data);

                if (inlineKeyboardButton != null) {
//                if (userSpecialisationsRepository.hasUserSpecialisation(user.getUserId(), data)) {
                    if (hasMarker) {
                        userSpecialisationsRepository.removeUserSpecialisation(user.getUserId(), data);
                        inlineKeyboardButton = UpdateTool.removeMarkerFromButton(inlineKeyboardButton);
                        outgoingMessage = "Ты отменил выбор специализации: " + inlineKeyboardButton.text() + ".";
                    } else {
                        userSpecialisationsRepository.addUserSpecialisation(user.getUserId(), data);
                        inlineKeyboardButton = UpdateTool.addMarkerToButton(inlineKeyboardButton);
                        outgoingMessage = "Круто, ты выбрал специализацию: " + data +
                                ". Можешь выбрать еще несколько или завершить этот этап.";
                    }

                    UpdateTool.changeButtonByText(inlineKeyboardMarkup.inlineKeyboard(), data, inlineKeyboardButton);

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
        return Step.SPECIALISATIONS;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult updateProcessorResult) {

        List<String> specialisations = new ArrayList<>();
        specialisationRepository.findAll().forEach(specialization -> specialisations.add(specialization.getSpecializationDescription()));

        InlineKeyboardButton[][] inlineKeyboardButtons = UpdateTool.getButtonArray(specialisations, 2);
        return ((SendMessage)updateProcessorResult.getRequest()).replyMarkup(new InlineKeyboardMarkup(inlineKeyboardButtons));
    }
}
