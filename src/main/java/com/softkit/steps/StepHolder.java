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

    private final StartStep startStep;                    // 0
    private final CandidateStep candidateStep;            // 1
    private final SpecialisationStep specialisationStep;  // 2
    private final TechnologiesStep technologiesStep;      // 3
    private final ExperienceStep experienceStep;          // 4
    private final EnglishLevelStep englishLevelStep;      // 5
    private final LocationStep locationStep;              // 6
    private final EmploymentStep employmentStep;          // 7
    private final MinSalaryStep minSalaryStep;            // 8
    private final MaxSalaryStep maxSalaryStep;            // 9
    private final DoneBasicRegistrationStep doneBasicRegistrationStep; // 10

    public static final String FINISH_SELECTION = "com.softkit.FINISH_SELECTION";
    private static final String START_COMMAND = "/start";
    private static final Map<Step, AbstractStep> ALL_STEPS = new HashMap<>();

    @PostConstruct
    public void init() {
        ALL_STEPS.put(START, startStep);
        ALL_STEPS.put(CANDIDATE, candidateStep);
        ALL_STEPS.put(SPECIALISATIONS, specialisationStep);
        ALL_STEPS.put(TECHNOLOGIES, technologiesStep);
        ALL_STEPS.put(EXPERIENCE, experienceStep);
        ALL_STEPS.put(ENGLISH_LEVEL, englishLevelStep);
        ALL_STEPS.put(CITY_OR_LOCATION, locationStep);
        ALL_STEPS.put(EMPLOYMENT, employmentStep);
        ALL_STEPS.put(MIN_SALARY, minSalaryStep);
        ALL_STEPS.put(MAX_SALARY, maxSalaryStep);
        ALL_STEPS.put(DONE_BASIC_REGISTRATION, doneBasicRegistrationStep);
    }

    public AbstractStep getStep(Step step) {
        return ALL_STEPS.get(step);
    }

    public AbstractStep getStartStep() {
        return startStep;
    }

    public static String getStartCommand() {
        return START_COMMAND;
    }
}
