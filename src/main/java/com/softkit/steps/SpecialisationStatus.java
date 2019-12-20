package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SpecialisationStatus extends AbstractStep {

    private SpecialisationRepository specialisationRepository;
    private UserSpecialisationsRepository userSpecialisationsRepository;

    public SpecialisationStatus(UserStatusRepository userStatusRepository, SpecialisationRepository specialisationRepository) {
        super(userStatusRepository);
        this.specialisationRepository = specialisationRepository;
    }

    @Override
    public UpdateProcessorResult process(Update update, User user) {

        Long chatId = UpdateTool.getChatId(update);

        if (UpdateTool.isCallback(update)) {
            String data = update.callbackQuery().data();
            System.out.println(data);

            if (data.contentEquals(StepHolder.FINISH_SELECTION) && user.getSpecializations().size() >= 1 && user.getSpecializations().size() <= 5) {
                nextStep = Step.TECHNOLOGIES;
                outgoingMessage = this.userStatusRepository.findUserStatusByStep(nextStep).map(Status::getBotMessage).get();
            } else {

            }

        } else
            outgoingMessage = this.userStatusRepository.findUserStatusByStep(nextStep).map(Status::getUserMistakeResponse).get();

        return new UpdateProcessorResult(chatId, new SendMessage(chatId, outgoingMessage), nextStep, user);
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
