package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.softkit.database.User;
import com.softkit.repository.UserFieldsSetter;
import com.softkit.vo.Step;
import com.softkit.vo.UpdateProcessorResult;
import com.softkit.utils.UpdateUtils;
import org.springframework.stereotype.Component;

import static com.softkit.utils.UpdateUtils.getNumberForRange;

@Component
public class MinSalaryStep extends AbstractStep {

    private final UserFieldsSetter userFieldsSetter;

    public MinSalaryStep(UserFieldsSetter userFieldsSetter) {
        this.userFieldsSetter = userFieldsSetter;
    }

    public UpdateProcessorResult process(Update update, User user) {

        // Version two
        Long chatId = UpdateUtils.getChatId(update);
        Step nextStep = getCurrentStepId();
        String outgoingMessage = nextStep.getUserMistakeResponse();

        Integer number = getNumberForRange( update, 10, 9999 );
        if( number!= null) {
            userFieldsSetter.setSalaryFrom(user, number);
            nextStep = Step.MAX_SALARY;
            outgoingMessage = nextStep.getBotMessage();
        }

        return new UpdateProcessorResult(chatId, new SendMessage(chatId, outgoingMessage), nextStep, user);


        // Version one
        /*
        Long chatId = UpdateUtils.getChatId(update);
        Step nextStep = getCurrentStepId();
        String outgoingMessage = nextStep.getUserMistakeResponse();

        if (UpdateUtils.hasMassageText(update)) {
            String price = UpdateUtils.getMessage(update).text();
            if (TextParser.isIntegerText(price) && Integer.parseInt(price) >= 10 && Integer.parseInt(price) <= 99999) {
                userFieldsSetter.setSalaryFrom(user, Integer.parseInt(price));
                nextStep = Step.MAX_SALARY;
                outgoingMessage = nextStep.getBotMessage();
            }
        }

        return new UpdateProcessorResult(chatId, new SendMessage(chatId, outgoingMessage), nextStep, user);
    */
    }


    public Step getCurrentStepId() {
        return Step.MIN_SALARY;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(UpdateProcessorResult result) {
        return result.getRequest();
    }

}
