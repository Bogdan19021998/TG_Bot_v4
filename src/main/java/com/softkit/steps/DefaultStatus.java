package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.softkit.database.User;
import com.softkit.database.Status;
import com.softkit.repository.UserStatusRepository;
import com.softkit.vo.Step;
import com.softkit.vo.UpdateProcessorResult;
import com.softkit.vo.UpdateTool;
import org.springframework.stereotype.Component;

@Component
//todo rename
public class DefaultStatus extends AbstractStep {

    public DefaultStatus(UserStatusRepository userStatusRepository) {
        super(userStatusRepository);
    }

    @Override
    public UpdateProcessorResult process(Update update, User userProfile) {
        Long chatId = UpdateTool.getChatId(update);

        if (UpdateTool.getUpdateMessage(update).text().contentEquals(StepHolder.getStartCommand())) {
            nextStep = Step.CANDIDATE;
            outgoingMessage = this.userStatusRepository.findUserStatusByStep(nextStep).map(Status::getBotMessage).get();
        } else
            outgoingMessage = this.userStatusRepository.findUserStatusByStep(nextStep).map(Status::getUserMistakeResponse).get();

        return new UpdateProcessorResult(chatId, new SendMessage(chatId, outgoingMessage), nextStep, userProfile);
    }

    @Override
    public Step getStepId() {
        return Step.START;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult updateProcessorResult) {
        return updateProcessorResult.getRequest();
    }

}
