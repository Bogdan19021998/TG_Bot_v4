package steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import database.User;
import vo.Step;
import vo.UpdateProcessorResult;

public abstract class AbstractStep {

    public abstract UpdateProcessorResult process(Update update, User user);
    public abstract Step getStepId();

    public abstract BaseRequest<?, ?> buildDefaultResponse(User user);

}
