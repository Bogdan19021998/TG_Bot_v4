package steps;

import vo.Step;

import java.util.HashMap;
import java.util.Map;

public class StepHolder {

    private static final Map<Step, AbstractStep> ALL_STEPS = new HashMap<>();

    private static final AbstractStep DEFAULT_STATUS;

    static {
//        ALL_STEPS.put();
//        todo init

        DEFAULT_STATUS = null;

    }

    public AbstractStep getStep(Step step) {
        return ALL_STEPS.get(step);
    }

    public AbstractStep getDefaultStatus() {
        return DEFAULT_STATUS;
    }
}
