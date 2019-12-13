package steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import database.User;
import vo.Step;
import vo.UpdateProcessorResult;

public class FirstNameStep extends AbstractStep {

    public UpdateProcessorResult process(Update update, User user) {
        return new UpdateProcessorResult(null, null, Step.SECOND_NAME, null);
    }

    public Step getStepId() {
        return Step.FIRST_NAME;
    }

    @Override
    public BaseRequest<?, ?> buildDefaultResponse(User user) {
        return new SendMessage(user.getUserId(), "Please enter you first name");
    }

}
