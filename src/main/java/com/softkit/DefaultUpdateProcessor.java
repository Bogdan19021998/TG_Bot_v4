package com.softkit;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.softkit.database.User;
import com.softkit.repository.UserRepository;
import com.softkit.steps.AbstractStep;
import com.softkit.steps.StepHolder;
import com.softkit.vo.UpdateProcessorResult;
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

//    public DefaultUpdateProcessor(UserRepository userRepository, StepHolder stepHolder, IMessageSender messageSender) {
//        this.userRepository = userRepository;
//        this.stepHolder = stepHolder;
//        this.messageSender = messageSender;
//    }

    @Override
    public void process(Update update) {


        Optional<User> user = userRepository.findUserByUserId(getUserId(update));

        AbstractStep step = user.map(u -> stepHolder.getStep(u.getCurrentStep())).orElseGet(stepHolder::getDefaultStatus);

        UpdateProcessorResult result = step.process(update, user.orElse( new User() ));

        if(step.getStepId() == result.getNextStep()) {
            messageSender.send(result.getRequest());
        } else {
            BaseRequest request = stepHolder.getStep(result.getNextStep()).buildDefaultResponse(result.getUpdatedUser());
            messageSender.send(request);
            userRepository.setNewStep(result.getUpdatedUser().getUserId(), result.getNextStep());
        }

    }

    public Integer getUserId(Update u) {
        return 1;
    }

}
