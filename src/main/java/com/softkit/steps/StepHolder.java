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

    private final DefaultStatus defaultStatus;                // 0
    private final CandidateStatus candidateStatus;            // 1
    private final SpecialisationStatus specialisationStatus;  // 2
    private final TechnologiesStatus technologiesStatus;      // 3
    private final ExperienceStatus experienceStatus;          // 4
    private final EnglishLevelStatus englishLevelStatus;      // 5
    private final LocationStatus locationStatus;              // 6
    private final EmploymentStatus employmentStatus;          // 7
    private final MinSalaryStatus minSalaryStatus;            // 8
    private final MaxSalaryStatus maxSalaryStatus;            // 9
    private final DoneBasicRegistrationStatus doneBasicRegistrationStatus; // 10

    public static final String FINISH_SELECTION = "com.softkit.FINISH_SELECTION";
    private static final String START_COMMAND = "/start";
    private static final Map<Step, AbstractStep> ALL_STEPS = new HashMap<>();

    @PostConstruct
    public void init() {
        ALL_STEPS.put(START, defaultStatus);
        ALL_STEPS.put(CANDIDATE, candidateStatus);
        ALL_STEPS.put(SPECIALISATIONS, specialisationStatus);
        ALL_STEPS.put(TECHNOLOGIES, technologiesStatus);
        ALL_STEPS.put(EXPERIENCE, experienceStatus);
        ALL_STEPS.put(ENGLISH_LEVEL, englishLevelStatus);
        ALL_STEPS.put(CITY_OR_LOCATION, locationStatus);
        ALL_STEPS.put(EMPLOYMENT, employmentStatus);
        ALL_STEPS.put(MIN_SALARY, minSalaryStatus);
        ALL_STEPS.put(MAX_SALARY, maxSalaryStatus);
        ALL_STEPS.put(DONE_BASIC_REGISTRATION, doneBasicRegistrationStatus);
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
