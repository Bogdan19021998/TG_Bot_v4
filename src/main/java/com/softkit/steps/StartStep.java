package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.softkit.database.User;
import com.softkit.repository.UserRepository;
import com.softkit.service.ReferralService;
import com.softkit.utils.TextParser;
import com.softkit.vo.Step;
import com.softkit.vo.UpdateProcessorResult;
import com.softkit.utils.UpdateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartStep extends AbstractStep {

    private final UserRepository userRepository;
    private final ReferralService referralService;

    @Override
    public UpdateProcessorResult process(Update update, User user) {
        // ---- Second version with referral links

        Long chatId = UpdateUtils.getChatId(update);
        Step nextStep = getCurrentStepId();
        String outgoingMessage = null;

        if (UpdateUtils.hasMassageText(update) ) {
            String incomingText = UpdateUtils.getMessage(update).text();
            if( incomingText.indexOf( Commands.START_COMMAND ) == 0 )
            {
                nextStep = getDefaultNextStep();
                outgoingMessage = nextStep.getBotMessage();

                if( TextParser.wordCount( incomingText ) == 2 ) {
                    String strEncryptingReferralId = incomingText.trim().split(" ")[1];

                    String strUserId = TextParser.decryptingText(strEncryptingReferralId);
                    if ( TextParser.isIntegerText( strUserId ) )
                    {
                        userRepository.findUserById(Integer.valueOf(strUserId)).
                                ifPresent(userOwner -> referralService.addUserReferral(userOwner, user));
                    }
                }
            }
        }
        if( outgoingMessage == null )
            outgoingMessage = nextStep.getUserMistakeResponse();

        return new UpdateProcessorResult(chatId, new SendMessage(chatId, outgoingMessage), nextStep, user);
    }

    @Override
    public Step getCurrentStepId() {
        return Step.START;
    }

    @Override
    public Step getDefaultNextStep() {
        return Step.CANDIDATE;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult updateProcessorResult) {
        return updateProcessorResult.getRequest();
    }


}
