package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.softkit.database.User;
import com.softkit.repository.UserRepository;
import com.softkit.repository.UserStatusRepository;
import com.softkit.vo.Step;
import com.softkit.vo.UpdateProcessorResult;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractStep {

    @Autowired
    protected UserStatusRepository userStatusRepository;

    public abstract UpdateProcessorResult process(Update update, User user);
    public abstract Step getStepId();

    public abstract BaseRequest<?, ?> buildDefaultResponse(User user);

}
