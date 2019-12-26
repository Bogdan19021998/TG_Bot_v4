package com.softkit;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.softkit.database.User;
import com.softkit.repository.UserRepository;
import com.softkit.steps.AbstractStep;
import com.softkit.steps.StepHolder;
import com.softkit.vo.UpdateProcessorResult;
import com.softkit.utils.UpdateUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DefaultUpdateProcessor implements UpdateProcessor {

    private UserRepository userRepository;
    private StepHolder stepHolder;
    private IMessageSender messageSender;

    public DefaultUpdateProcessor(UserRepository userRepository, StepHolder stepHolder, @Qualifier("botMessageSender") IMessageSender messageSender) {
        this.userRepository = userRepository;
        this.stepHolder = stepHolder;
        this.messageSender = messageSender;
    }

    @Override
    public void process(Update update) {

        Integer userId = UpdateUtils.getUserId(update);

        if (userId != null)
            if (UpdateUtils.getMessageOrCallbackMessage(update).text() != null || UpdateUtils.isCallback(update)) {
                Optional<User> user = userRepository.findUserById(userId);

                AbstractStep step = user.map(u -> stepHolder.getStep(u.getStep())).orElseGet(stepHolder::getStartStep);

                System.out.println("update from user " + UpdateUtils.getUserId(update) + " with status " + step.getCurrentStepId());

                UpdateProcessorResult result = step.process(update, user.orElse(new User(userId)));

                // optional request
                if (result.getOptionalRequest() != null) {
                    messageSender.send(result.getOptionalRequest());
                }

                // main request
                BaseRequest<?,?> baseRequest;
                boolean isSent;
                if (step.getCurrentStepId() == result.getNextStep()) {
                    baseRequest = result.getRequest();
                } else {
                    result.getUpdatedUser().setStep(result.getNextStep());
                    baseRequest = stepHolder.getStep(result.getNextStep()).buildDefaultResponse(result);
                }

                if (baseRequest != null) {
                    isSent = messageSender.send(result.getRequest());
                    if (isSent)
                        userRepository.save(result.getUpdatedUser());
                }

            }
//        else {
//                // some text about wrong user action
//            }
    }

}
