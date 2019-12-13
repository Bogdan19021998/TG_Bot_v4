import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import database.User;
import lombok.RequiredArgsConstructor;
import repository.UserRepository;
import steps.AbstractStep;
import steps.StepHolder;
import vo.UpdateProcessorResult;

import java.util.Optional;

@RequiredArgsConstructor
public class DefaultUpdateProcessor implements UpdateProcessor {

    private final UserRepository userRepository;
    private final StepHolder stepHolder;
    private final IMessageSender messageSender;

    @Override
    public void process(Update update) {
        Optional<User> user = userRepository.findUserById(getUserId(update));

        AbstractStep step = user.map(u -> stepHolder.getStep(u.getCurrentStep())).orElseGet(stepHolder::getDefaultStatus);

        UpdateProcessorResult result = step.process(update, user.orElse(null));

        if(step.getStepId() == result.getNextStep()) {
            messageSender.send(result.getRequest());
        } else {
            BaseRequest request = stepHolder.getStep(result.getNextStep()).buildDefaultResponse(result.getUpdatedUser());
            messageSender.send(request);
            userRepository.setNewStep(result.getUpdatedUser().getUserId(), result.getNextStep());
        }


    }

    public Long getUserId(Update u) {
        return 1l;
    }
}
