package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.softkit.database.User;
import com.softkit.vo.Step;
import com.softkit.vo.UpdateProcessorResult;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractStep {

    public abstract UpdateProcessorResult process(Update update, User user);

    public abstract Step getCurrentStepId();

    public abstract BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult updateProcessorResult);

    /**
     * getDefaultNextStep()
     * При "пропустить" проверять в DefaultUpdateProcessor, вызывать этот метод с проверкой и переходить в следующий дефолтный
     *
     */

}
