package com.softkit;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.softkit.database.User;
import com.softkit.repository.ReferralRepository;
import com.softkit.repository.UserRepository;
import com.softkit.service.UserService;
import com.softkit.steps.Commands;
import com.softkit.steps.StepHolder;
import com.softkit.vo.Step;
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

    private UserService userService;
    private Commands commands;

    public DefaultUpdateProcessor(UserRepository userRepository, StepHolder stepHolder, @Qualifier("botMessageSender") IMessageSender messageSender, Commands commands, UserService userService) {
        this.userRepository = userRepository;
        this.stepHolder = stepHolder;
        this.messageSender = messageSender;
        this.commands = commands;
        this.userService = userService;
    }

    @Override
    public void process(Update update) {

        Integer userId = UpdateUtils.getUserId(update);

        if (userId != null)
            if (UpdateUtils.getMessageOrCallbackMessage(update) != null || UpdateUtils.isCallback(update)) {

                Optional<User> optionalUser = userRepository.findUserById(userId);

                User user = optionalUser.orElse(userService.addUserAndSetFirstStep(UpdateUtils.getUserId(update)));

                UpdateProcessorResult result;

                Step step = commands.getStepForUserCommand(update, user);
                if (step == null)
                    step = user.getStep();

                result = stepHolder.getStep(step).process(update, user);

                // optional request
                if (result.getOptionalRequest() != null) {
                    messageSender.send(result.getOptionalRequest());
                }

                if (result.getRequest() != null) {
                    // main request
                    BaseRequest<?, ?> baseRequest;
                    boolean isSent;
                    if (result.getUpdatedUser().getStep() == result.getNextStep()) {
                        baseRequest = result.getRequest();
                    } else {
                        result.getUpdatedUser().setStep(result.getNextStep());
                        baseRequest = stepHolder.getStep(result.getNextStep()).buildDefaultResponse(result);
                    }

                    if (baseRequest != null) {
                        isSent = messageSender.send(baseRequest).isOk();
                        if (isSent)
                            userRepository.save(result.getUpdatedUser());
                    }
                }
            }
//        else {
//                // some text about wrong user action
//            }
    }

}
