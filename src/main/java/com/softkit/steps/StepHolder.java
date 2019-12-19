package com.softkit.steps;

import com.softkit.vo.Step;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static com.softkit.vo.Step.*;

@Service
@RequiredArgsConstructor
public class StepHolder {

    private final DefaultStatus defaultStatus;
    private final CandidateStatus candidateStatus;
    private final SpecialisationStatus specialisationStatus;

    private static final String START_COMMAND = "/start";
    private static final Map<Step, AbstractStep> ALL_STEPS = new HashMap<>();

    @PostConstruct
    public void init() {
        ALL_STEPS.put(START, defaultStatus);
        ALL_STEPS.put(CANDIDATE, candidateStatus);
        ALL_STEPS.put(SPECIALISATIONS, specialisationStatus);
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
