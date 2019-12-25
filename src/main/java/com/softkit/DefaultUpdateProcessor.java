package com.softkit;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.softkit.database.User;
import com.softkit.repository.UserRepository;
import com.softkit.steps.AbstractStep;
import com.softkit.steps.StepHolder;
import com.softkit.vo.UpdateProcessorResult;
import com.softkit.vo.UpdateTool;
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

        Integer userId = UpdateTool.getUserId(update);

        if (userId != null)
            if (UpdateTool.getUpdateMessage(update).text() != null || UpdateTool.isCallback(update)) {
                Optional<User> user = userRepository.findUserById(userId);

                AbstractStep step = user.map(u -> stepHolder.getStep(u.getStep())).orElseGet(stepHolder::getStartStep);

                System.out.println("update from user " + UpdateTool.getUserId(update) + " with status " + step.getStepId());

                UpdateProcessorResult result = step.process(update, user.orElse(new User(userId)));

                boolean isSent;

                if (step.getStepId() == result.getNextStep()) {
                    isSent = messageSender.send(result.getRequest());
                    if (isSent) {
                        userRepository.save(result.getUpdatedUser());
                    }
                } else {
                    BaseRequest<?, ?> request = stepHolder.getStep(result.getNextStep()).buildDefaultResponse(result);
                    isSent = messageSender.send(request);
                    if (isSent) {
                        userRepository.setNewStep(userId, result.getNextStep());
                        System.out.println("user " + UpdateTool.getUserId(update) + " set status " + result.getNextStep());
                    }
                }

            }
//        else {
//                // some text about wrong user action
//            }
    }

}
