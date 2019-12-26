package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.softkit.database.User;
import com.softkit.repository.UserFieldsSetter;
import com.softkit.utils.TextParser;
import com.softkit.utils.UpdateUtils;
import com.softkit.vo.Step;
import com.softkit.vo.UpdateProcessorResult;
import jdk.jshell.execution.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhoneStep extends AbstractStep{

    private final UserFieldsSetter userFieldsSetter;

    @Override
    public UpdateProcessorResult process(Update update, User user) {


        return null;
    }

    @Override
    public Step getCurrentStepId() {

        return null;
//        return Step.PHONE;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult updateProcessorResult) {

        return null;
    }
}
