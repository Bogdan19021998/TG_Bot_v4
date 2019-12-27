package com.softkit;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.softkit.database.User;
import com.softkit.database.UserReferral;
import com.softkit.repository.ReferralRepository;
import com.softkit.repository.UserRepository;
import com.softkit.steps.AbstractStep;
import com.softkit.steps.StepHolder;
import com.softkit.vo.ApplicationContextProvider;
import com.softkit.vo.UpdateProcessorResult;
import com.softkit.utils.UpdateUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

@Component
public class DefaultUpdateProcessor implements UpdateProcessor {

    private UserRepository userRepository;
    private StepHolder stepHolder;
    private IMessageSender messageSender;
    private ReferralRepository referralRepository;

    public DefaultUpdateProcessor(UserRepository userRepository, StepHolder stepHolder, @Qualifier("botMessageSender") IMessageSender messageSender, ReferralRepository referralRepository) {
        this.userRepository = userRepository;
        this.stepHolder = stepHolder;
        this.messageSender = messageSender;
        this.referralRepository = referralRepository;
    }

    @Override
    public void process(Update update) {

        Integer userId = UpdateUtils.getUserId(update);

        if (userId != null)
            if (UpdateUtils.getMessageOrCallbackMessage(update) != null || UpdateUtils.isCallback(update)) {
                Optional<User> user = userRepository.findUserById(userId);

                AbstractStep step = user.map(u -> stepHolder.getStep(u.getStep())).orElseGet(stepHolder::getStartStep);

                System.out.println("update from user " + UpdateUtils.getUserId(update) + " with status " + step.getCurrentStepId());

                UpdateProcessorResult result = step.process(update, user.orElse(new User(userId)));

                // optional request
                if (result.getOptionalRequest() != null) {
                    BaseResponse response = messageSender.send(result.getOptionalRequest());
                    if( response instanceof GetFileResponse )
                    {
                        saveFile( (GetFileResponse)response, user.get() );
                    }
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
                    isSent = messageSender.send(baseRequest).isOk();
                    if (isSent)
                        userRepository.save(result.getUpdatedUser());
                }

                if( result.getUserOwner() != null )
                {
                    referralRepository.save( new UserReferral( result.getUserOwner().getId(), result.getUpdatedUser().getId()));
                }

            }
//        else {
//                // some text about wrong user action
//            }
    }

    private boolean saveFile(GetFileResponse responseFile, User user)
    {
        if (responseFile.isOk()) {
            // todo remove token!
            String token = "1032578818:AAEcsGFQ46oLcenajdG7vUB_jPyuazzdqIk";
            File localFile = new File("/home/bogdan/" + user.getNameSummary());
            try {
                InputStream is = new URL("https://api.telegram.org/file/bot" + token
                        + "/" + responseFile.file().filePath()).openStream();
                FileUtils.copyInputStreamToFile(is, localFile);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
