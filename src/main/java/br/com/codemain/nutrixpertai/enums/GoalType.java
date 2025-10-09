package br.com.codemain.nutrixpertai.enums;

public enum GoalType {
    WEIGHT_LOSS("Emagrecimento"),
    MUSCLE_GAIN("Ganho de massa muscular"),
    DIABETES_CONTROL("Controle de diabetes"),
    NUTRITIONAL_REEDUCATION("Reeducação alimentar"),
    PHYSICAL_MENTAL_PERFORMANCE("Performance física e mental"),
    WEIGHT_GAIN("Ganho de peso"),
    FAT_LOSS("Perda de gordura"),
    MAINTENANCE("Manutenção do peso");

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
