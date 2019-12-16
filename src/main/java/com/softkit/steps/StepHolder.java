package com.softkit.steps;

import com.softkit.repository.UserStatusRepository;
import com.softkit.vo.Step;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static com.softkit.vo.Step.NAME_SURNAME;
import static com.softkit.vo.Step.START;

@Service
@RequiredArgsConstructor
public class StepHolder {

    private final UserStatusRepository userStatusRepository;
    private final ApplicationContext applicationContext;
    private final DefaultStatus defaultStatus;

    private static final String START_COMMAND = "/start";
    private static final Map<Step, AbstractStep> ALL_STEPS = new HashMap<>();

    @PostConstruct
    public void init() {
        ALL_STEPS.put(START, defaultStatus);
        ALL_STEPS.put(NAME_SURNAME, new NameStatus());
    }

    public AbstractStep getStep(Step step) {
        return ALL_STEPS.get(step);
    }

    public AbstractStep getDefaultStatus() {
        return defaultStatus;
    }

    public static String getStartCommand() {
        return START_COMMAND;
    }
}
