package br.com.codemain.nutrixpertai.enums;

public enum GoalType {
    WEIGHT_LOSS("Perda de peso"),
    WEIGHT_GAIN("Ganho de peso"),
    MUSCLE_GAIN("Ganho de massa"),
    FAT_LOSS("Perda de gordura");

    private final String displayName;

    GoalType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}