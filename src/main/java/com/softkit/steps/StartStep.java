package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.softkit.database.User;
import com.softkit.repository.ReferralRepository;
import com.softkit.repository.UserRepository;
import com.softkit.service.ReferralService;
import com.softkit.utils.TextParser;
import com.softkit.vo.Step;
import com.softkit.vo.UpdateProcessorResult;
import com.softkit.utils.UpdateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Base64;

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
        User userOwner = null;

        if (UpdateUtils.hasMassageText(update) ) {
            String incomingText = UpdateUtils.getMessage(update).text();
            if( incomingText.indexOf( StepHolder.getStartCommand() ) == 0 )
            {
                nextStep = Step.CANDIDATE;
                outgoingMessage = nextStep.getBotMessage();

                if( TextParser.wordCount( incomingText ) == 2 ) {
                    String strEncryptingReferralId = incomingText.trim().split(" ")[1];

                    String strUserId = TextParser.decryptingText(strEncryptingReferralId);
                    if ( TextParser.isIntegerText( strUserId ) )
                    {
                        userOwner = userRepository.findUserById( Integer.valueOf( strUserId ) ).orElse(null);
                        if( userOwner != null )
                            referralService.addUserReferral( userOwner, user );
                    }
                }
            }
        }
        if( outgoingMessage == null )
            outgoingMessage = nextStep.getUserMistakeResponse();

        UpdateProcessorResult updateProcessorResult = new UpdateProcessorResult(chatId, new SendMessage(chatId, outgoingMessage), nextStep, user);


        return updateProcessorResult;


        // ---- First version

        /*
        Long chatId = UpdateUtils.getChatId(update);
        Step nextStep = getCurrentStepId();
        String outgoingMessage ;
        if (UpdateUtils.hasMassageText(update) && UpdateUtils.getMessage(update).text().contentEquals(StepHolder.getStartCommand())) {
            nextStep = Step.SUMMARY;
//            nextStep = Step.CANDIDATE;

            outgoingMessage = nextStep.getBotMessage();
        } else
            outgoingMessage = nextStep.getUserMistakeResponse();


        return new UpdateProcessorResult(chatId, new SendMessage(chatId, outgoingMessage), nextStep, user);
        */

    }

    @Override
    public Step getCurrentStepId() {
        return Step.START;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult updateProcessorResult) {
        return updateProcessorResult.getRequest();
    }

}
