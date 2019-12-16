package com.softkit.vo;

public enum Step {

    START(1),
    NAME_SURNAME(2),
    SPECIALISATIONS(3);

    private final int step;

    Step(int step) {
        this.step = step;
    }

    public int getStepId() {
        return step;
    }
}
