package com.softkit.vo;

public enum Step {
    START,
    CANDIDATE,
    SPECIALISATIONS,
    TECHNOLOGIES,
    EXPERIENCE,
    ENGLISH_LEVEL,
    CITY_OR_LOCATION,
    EMPLOYMENT,
    MIN_SALARY,
    MAX_SALARY,
    DONE_BASIC_REGISTRATION,
    PHONE,
    AGE,
    SUMMARY,
    DONE_REGISTRATION;

    private int stepId;

    private void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public int getStepId() {
        return stepId;
    }

    static {
        for (int i = 0; i < values().length; i++) {
            values()[i].setStepId(i + 1);
        }
    }

}
