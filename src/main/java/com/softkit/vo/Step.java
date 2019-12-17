package com.softkit.vo;

public enum Step {

    START(1),
    CANDIDATE(2),
    SPECIALISATIONS(3);

    private final int step;

    Step(int step) {
        this.step = step;
    }

    public int getStepIntId() {
        return step;
    }

    public static Step getStepById(int id) {
        for (Step value : values()) {
            if (value.step == id) {
                return value;
            }
        }
        return null;
    }
}
