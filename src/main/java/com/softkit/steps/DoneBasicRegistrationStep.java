package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.softkit.database.Status;
import com.softkit.database.User;
import com.softkit.repository.UserStatusRepository;
import com.softkit.vo.Step;
import com.softkit.vo.UpdateProcessorResult;
import com.softkit.vo.UpdateTool;
import org.springframework.stereotype.Component;

@Component
public class DoneBasicRegistrationStep extends AbstractStep {

    public DoneBasicRegistrationStep(UserStatusRepository userStatusRepository) {
        super(userStatusRepository);
    }

    public UpdateProcessorResult process(Update update, User user) {
        Long chatId = UpdateTool.getChatId(update);
        outgoingMessage = this.userStatusRepository.findUserStatusByStep(nextStep).map(Status::getBotMessage).get();
        return new UpdateProcessorResult(chatId, new SendMessage(chatId, outgoingMessage), nextStep, user);
    }

    public Step getStepId() {
        return Step.DONE_BASIC_REGISTRATION;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult result) {
        return result.getRequest();
    }

}
