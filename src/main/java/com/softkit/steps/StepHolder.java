package com.softkit.steps;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.softkit.database.User;
import com.softkit.vo.Step;
import com.softkit.vo.UpdateProcessorResult;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.softkit.vo.Step.NAME_SURNAME;
import static com.softkit.vo.Step.START;

@Service
public class StepHolder {

    private static final Map<Step, AbstractStep> ALL_STEPS = new HashMap<>();

    private static final AbstractStep DEFAULT_STATUS = new DefaultStatus();

    static {
        ALL_STEPS.put(START, DEFAULT_STATUS);
        ALL_STEPS.put(NAME_SURNAME, new NameStatus());
        // todo realize and add all statuses
    }

    public AbstractStep getStep(Step step) {
        return ALL_STEPS.get(step);
    }

    public AbstractStep getDefaultStatus() {
        return DEFAULT_STATUS;
    }
}
