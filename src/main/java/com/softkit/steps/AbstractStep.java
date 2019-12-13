package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.softkit.database.User;
import com.softkit.vo.Step;
import com.softkit.vo.UpdateProcessorResult;

public abstract class AbstractStep {

    public abstract UpdateProcessorResult process(Update update, User user);
    public abstract Step getStepId();

    public abstract BaseRequest<?, ?> buildDefaultResponse(User user);

}
