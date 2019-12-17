package com.softkit;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.softkit.database.User;
import com.softkit.repository.UserRepository;
import com.softkit.steps.AbstractStep;
import com.softkit.steps.StepHolder;
import com.softkit.vo.Step;
import com.softkit.vo.UpdateProcessorResult;
import com.softkit.vo.UpdateTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

//@RequiredArgsConstructor
@Component
public class DefaultUpdateProcessor implements UpdateProcessor {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StepHolder stepHolder;

    @Autowired
    @Qualifier("botMessageSender")
    private IMessageSender messageSender;

    @Override
    public void process(Update update) {

        Integer userId = UpdateTool.getUserId(update);

        if (userId != null)
            if (UpdateTool.getUpdateMessage(update).text() != null || UpdateTool.isCallback(update)) {
                Optional<User> user = userRepository.findUserByUserId(userId);

                AbstractStep step = user.map(u -> stepHolder.getStep(Step.getStepById(u.getCurrentStep()))).orElseGet(stepHolder::getDefaultStatus);

                System.out.println("update from user " + UpdateTool.getUserId(update) + " with status " + step.getStepId());

                UpdateProcessorResult result = step.process(update, user.orElse(new User(userId)));
                // add
                userRepository.save(result.getUpdatedUser());

                if (step.getStepId() == result.getNextStep()) {
                    messageSender.send(result.getRequest());
                } else {
                    BaseRequest<?, ?> request = stepHolder.getStep(result.getNextStep()).buildDefaultResponse(result);
                    messageSender.send(request);
                    userRepository.setNewStep(userId, result.getNextStep());
                }
            } else {
                // some text about wrong user action
            }
    }

}
