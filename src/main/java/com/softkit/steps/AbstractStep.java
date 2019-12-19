package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.softkit.database.UserProfile;
import com.softkit.repository.UserStatusRepository;
import com.softkit.vo.Step;
import com.softkit.vo.UpdateProcessorResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractStep {

    @Autowired
    protected UserStatusRepository userStatusRepository;

    protected Step nextStep = getStepId();
    protected String outgoingMessage;

    public abstract UpdateProcessorResult process(Update update, UserProfile userProfile);

    public abstract Step getStepId();

    public abstract BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult updateProcessorResult);

}
