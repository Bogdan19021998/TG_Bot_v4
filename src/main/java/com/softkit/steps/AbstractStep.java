package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.softkit.database.User;
import com.softkit.repository.UserStatusRepository;
import com.softkit.vo.Step;
import com.softkit.vo.UpdateProcessorResult;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractStep {

    protected final UserStatusRepository userStatusRepository;

    protected Step nextStep = getStepId();
    protected String outgoingMessage;

    public AbstractStep(UserStatusRepository userStatusRepository) {
        this.userStatusRepository = userStatusRepository;
    }

    public abstract UpdateProcessorResult process(Update update, User user);

    public abstract Step getStepId();

    public abstract BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult updateProcessorResult);

}
